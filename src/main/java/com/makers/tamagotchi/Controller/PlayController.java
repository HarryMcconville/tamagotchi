package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.Trait;
import com.makers.tamagotchi.Model.Trait.StatType;
import com.makers.tamagotchi.Model.User;
import com.makers.tamagotchi.Model.Village;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import com.makers.tamagotchi.Repository.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.Optional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Controller
public class PlayController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    VillageRepository villageRepository;

    // this creates a custom exception that runs once, rather than in every controller method
    public static class NoActivePetException extends RuntimeException {}

    // helper method to get active pet by user email
    private Pet getActivePet(String email) {
        return userRepository.findUserByEmail(email)
                .flatMap(user -> petRepository.findAllByUser(user).stream()
                        .filter(Pet::getIsActive)
                        .findFirst())
                .orElseThrow(NoActivePetException::new);  // throws instead of returning null
    }

    // this adds the active pet to the model for html use
    @ModelAttribute("pet")
    public Pet populateActivePet(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        return getActivePet(email);
    }

    // this handles "no active pet" exception by redirecting to /welcome
    @ExceptionHandler(NoActivePetException.class)
    public String handleNoActivePet() {
        return "redirect:/welcome";
    }

    // helper method to get active village by user email
    private Village getActiveVillage(String email) {
        return userRepository.findUserByEmail(email)
                .map(user -> villageRepository.findAllByUser(user).stream()
                        .filter(Village::getIsActive)
                        .findFirst()
                        .orElse(null))
                .orElse(null);
    }

    // this adds the active village to the model for html use
    @ModelAttribute("village")
    public Village populateActiveVillage(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        return getActiveVillage(email);
    }

    // helper method to check for perk or flaw affecting restoration rate for stat
    private int calculateRestoredAmount(Pet pet, StatType statType, int baseAmount) {
        Trait perk = pet.getPerk();
        Trait flaw = pet.getFlaw();

        double modifier = 1.0;

        if (perk != null && perk.getStatType() == statType) {
            modifier = perk.getModifier();  // e.g. 1.25
        } else if (flaw != null && flaw.getStatType() == statType) {
            modifier = flaw.getModifier();  // e.g. 0.75
        }

        return (int) (baseAmount * modifier);
    }

    @GetMapping("/play")
    public String livingRoom() {
        return "living_room";
    }

    // Helper method to reset village stats
    private void resetVillageResources(String email) {
        Optional<User> userOpt = userRepository.findUserByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Village> villages = villageRepository.findAllByUser(user);

            Village activeVillage = villages.stream()
                    .filter(Village::getIsActive)
                    .findFirst()
                    .orElse(null);

            if (activeVillage != null) {
                // Reset all collected resources to 0
                activeVillage.setCatfood(0);
                activeVillage.setCollectedCatFood(0);
                activeVillage.setMilk(0);
                activeVillage.setCollectedMilk(0);
                activeVillage.setCatnip(0);
                activeVillage.setCollectedCatnip(0);
                activeVillage.setBrush(0);
                activeVillage.setCollectedBrush(0);
                villageRepository.save(activeVillage);
            }
        }
    }

    @GetMapping("/play/shoo")
    public String shooCat(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        try {
            Pet activePet = getActivePet(email); //had to change this for the memories page
            activePet.setIsActive(false);
            petRepository.save(activePet);

            // Reset village resources when shooing the cat
            resetVillageResources(email);

        } catch (NoActivePetException e) {
            // fallback if no active pet exists
        }
        return "redirect:/welcome";
    }

    @GetMapping("/play/confirm_shoo")
    public String confirmShoo(){
        return "shoo_cat";
    }

    @GetMapping("/play/relocation")
    public String relocateCat(@ModelAttribute("pet") Pet pet,
                              @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        int happinessStatus = pet.getHappiness();
        if (happinessStatus == 0) {
            pet.setIsActive(false);
            petRepository.save(pet);

            // reset the village when shooing the cat
            resetVillageResources(email);
            return "cat_relocated";
        } else {
            return "redirect:/play";
        }
    }
}