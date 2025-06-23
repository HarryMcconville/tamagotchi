package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.Trait;
import com.makers.tamagotchi.Model.Trait.StatType;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class PlayController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PetRepository petRepository;

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

    // this handles "no active pet" exception by redirecting to /welcome
    @ExceptionHandler(NoActivePetException.class)
    public String handleNoActivePet() {
        return "redirect:/welcome";
    }

    // this adds the active pet to the model before every controller method
    @ModelAttribute("pet")
    public Pet populateActivePet(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        return getActivePet(email);
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

    @GetMapping("/play/feed")
    public String feedCat(@ModelAttribute("pet") Pet pet,
                          RedirectAttributes redirectAttributes) {

        int restoredAmount = calculateRestoredAmount(pet, StatType.HUNGER, 20);
        int newHunger = Math.min(100, pet.getHunger() + restoredAmount);
        pet.setHunger(newHunger);

        pet.setLastUpdated(LocalDateTime.now());
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("flashMessage", "You have fed " + pet.getName() + "!");
        return "redirect:/play";
    }

    @GetMapping("/play/water")
    public String waterCat(@ModelAttribute("pet") Pet pet,
                           RedirectAttributes redirectAttributes) {

        int restoredAmount = calculateRestoredAmount(pet, StatType.THIRST, 20);
        int newThirst = Math.min(100, pet.getThirst() + restoredAmount);
        pet.setThirst(newThirst);

        pet.setLastUpdated(LocalDateTime.now());
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("flashMessage", "You have refilled " + pet.getName() + "'s water bowl!");
        return "redirect:/play";
    }

    @GetMapping("/play/pet")
    public String petCat(@ModelAttribute("pet") Pet pet,
                         RedirectAttributes redirectAttributes) {

        int restoredAmount = calculateRestoredAmount(pet, StatType.SOCIAL, 20);
        int newSocial = Math.min(100, pet.getSocial() + restoredAmount);
        pet.setSocial(newSocial);

        pet.setLastUpdated(LocalDateTime.now());
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("flashMessage", "You have pet " + pet.getName() + "!");
        return "redirect:/play";
    }

    @GetMapping("/play/game")
    public String playGameWithCat(@ModelAttribute("pet") Pet pet,
                                  RedirectAttributes redirectAttributes) {

        int restoredAmount = calculateRestoredAmount(pet, StatType.FUN, 20);
        int newFun = Math.min(100, pet.getFun() + restoredAmount);
        pet.setFun(newFun);

        pet.setLastUpdated(LocalDateTime.now());
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("flashMessage", "You have played with " + pet.getName() + "!");
        return "redirect:/play";
    }

    @GetMapping("/play/shoo")
    public String shooCat(@ModelAttribute("pet") Pet pet) {
        if (pet != null) {
            pet.setIsActive(false);
            petRepository.save(pet);
        }
        return "redirect:/welcome";
    }

    @GetMapping("/play/confirm_shoo")
    public String confirmShoo(){
        return "shoo_cat";
    }
}