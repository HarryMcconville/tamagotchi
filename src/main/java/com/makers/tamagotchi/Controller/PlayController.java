package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.Village;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import com.makers.tamagotchi.Repository.VillageRepository;
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

    // helper method to get active village by user email
    private Village getActiveVillage(String email) {
        return userRepository.findUserByEmail(email)
                .map(user -> villageRepository.findAllByUser(user).stream()
                        .filter(Village::getIsActive)
                        .findFirst()
                        .orElse(null))
                .orElse(null);
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

    @GetMapping("/play")
    public String livingRoom() {
        return "living_room";
    }

    @GetMapping("/play/feed")
    public String feedCat(@ModelAttribute("pet") Pet pet,
                          @AuthenticationPrincipal(expression = "attributes['email']") String email,
                          RedirectAttributes redirectAttributes) {

        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "No active village found!");
            return "redirect:/play";
        }

        // Check if user has collected cat food
        if (activeVillage.getCollectedCatFood() <= 0) {
            redirectAttributes.addFlashAttribute("flashMessage", "You don't have any cat food! Visit the village to collect some.");
            return "redirect:/play";
        }

        // Consume 1 cat food and feed the pet
        activeVillage.setCollectedCatFood(activeVillage.getCollectedCatFood() - 1);
        villageRepository.save(activeVillage);

        pet.setHunger(100);
        pet.setLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("flashMessage", "You have fed " + pet.getName() + "!");
        return "redirect:/play";
    }

    @GetMapping("/play/water")
    public String waterCat(@ModelAttribute("pet") Pet pet,
                           @AuthenticationPrincipal(expression = "attributes['email']") String email,
                           RedirectAttributes redirectAttributes) {

        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "No active village found!");
            return "redirect:/play";
        }

        // Check if user has collected milk
        if (activeVillage.getCollectedMilk() <= 0) {
            redirectAttributes.addFlashAttribute("flashMessage", "You don't have any milk! Visit the village to collect some.");
            return "redirect:/play";
        }

        // Consume 1 milk and give water to the pet
        activeVillage.setCollectedMilk(activeVillage.getCollectedMilk() - 1);
        villageRepository.save(activeVillage);

        pet.setThirst(100);
        pet.setThirstLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("flashMessage", "You have refilled " + pet.getName() + "'s bowl with milk!");
        return "redirect:/play";
    }

    @GetMapping("/play/pet")
    public String petCat(@ModelAttribute("pet") Pet pet,
                         @AuthenticationPrincipal(expression = "attributes['email']") String email,
                         RedirectAttributes redirectAttributes) {

        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "No active village found!");
            return "redirect:/play";
        }

        // Check if user has collected brushes
        if (activeVillage.getCollectedBrush() <= 0) {
            redirectAttributes.addFlashAttribute("flashMessage", "You don't have any brushes! Visit the village to get some.");
            return "redirect:/play";
        }

        // Consume 1 brush and pet the cat
        activeVillage.setCollectedBrush(activeVillage.getCollectedBrush() - 1);
        villageRepository.save(activeVillage);

        pet.setSocial(100);
        pet.setSocialLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("flashMessage", "You have pet " + pet.getName() + "!");
        return "redirect:/play";
    }

    @GetMapping("/play/game")
    public String playGameWithCat(@ModelAttribute("pet") Pet pet,
                                  @AuthenticationPrincipal(expression = "attributes['email']") String email,
                                  RedirectAttributes redirectAttributes) {

        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "No active village found!");
            return "redirect:/play";
        }

        // Check if user has collected catnip
        if (activeVillage.getCollectedCatnip() <= 0) {
            redirectAttributes.addFlashAttribute("flashMessage", "You don't have any catnip! Visit the village to get some.");
            return "redirect:/play";
        }

        // Consume 1 catnip and play with the cat
        activeVillage.setCollectedCatnip(activeVillage.getCollectedCatnip() - 1);
        villageRepository.save(activeVillage);

        pet.setFun(100);
        pet.setFunLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
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