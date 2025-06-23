package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/play")
    public String livingRoom(@ModelAttribute("pet") Pet pet, Model model) {
        int happiness = pet.getHappiness();
        String statusMessage;

        if (happiness < 5) {
            statusMessage = "I think it’s time to pack my whiskers and tail it outta here...";
        } else if (happiness < 15) {
            statusMessage = "I packed my favorite toy mouse… just in case I need to move my paws.";
        } else if (happiness < 30) {
            statusMessage = "Just wondering if anyone’s posted an ad for a lonely but loveable kitty...";
        } else if (happiness < 45) {
            statusMessage = "I'm feline a little unloved... maybe it's time to prowl elsewhere.";
        } else if (happiness < 60) {
            statusMessage = "Why do my fur-iends get more treats and chin scritches than me?";
        } else if (happiness < 75) {
            statusMessage = "Can I get a little more purr-sonal attention, please?";
        } else if (happiness < 90) {
            statusMessage = "I’m a pretty pawsitive kitty right meow!";
        } else {
            statusMessage = "This is pawsitively the purrrfect life!";
        }

        model.addAttribute("statusMessage", statusMessage);
        return "living_room";
    }

    @GetMapping("/play/feed")
    public String feedCat(@ModelAttribute("pet") Pet pet,
                          RedirectAttributes redirectAttributes) {

        pet.setHunger(100);
        pet.setLastUpdated(LocalDateTime.now());
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("flashMessage", "You have fed " + pet.getName() + "!");
        return "redirect:/play";
    }

    @GetMapping("/play/water")
    public String waterCat(@ModelAttribute("pet") Pet pet,
                           RedirectAttributes redirectAttributes) {

        pet.setThirst(100);
        pet.setThirstLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("flashMessage", "You have refilled " + pet.getName() + "'s water bowl!");
        return "redirect:/play";
    }

    @GetMapping("/play/pet")
    public String petCat(@ModelAttribute("pet") Pet pet,
                         RedirectAttributes redirectAttributes) {

        pet.setSocial(100);
        pet.setSocialLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
        petRepository.save(pet);

        redirectAttributes.addFlashAttribute("flashMessage", "You have pet " + pet.getName() + "!");
        return "redirect:/play";
    }

    @GetMapping("/play/game")
    public String playGameWithCat(@ModelAttribute("pet") Pet pet,
                                  RedirectAttributes redirectAttributes) {

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

    @GetMapping("/play/relocation")
    public String relocateCat(@ModelAttribute("pet") Pet pet) {
        int happinessStatus = pet.getHappiness();
        if (happinessStatus == 0) {
            return "cat_relocated"; }
        else {
            return "redirect:/play";
        }
    }
}