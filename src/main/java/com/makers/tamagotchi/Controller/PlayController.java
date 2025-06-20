package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.User;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.User;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PlayController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PetRepository petRepository;

    @ModelAttribute("activePet")
    public Pet getActivePet(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            List<Pet> pets = petRepository.findAllByUser(userOptional.get());
            for (Pet pet : pets) {
                if (pet.getIsActive()) {
                    return pet;
                }
            }
        }
        return null;
    }

    @GetMapping("/play")
    public ModelAndView livingRoom(@ModelAttribute("activePet") Pet pet) {
        ModelAndView modelAndView = new ModelAndView("living_room");
        modelAndView.addObject("pet", pet);
        return modelAndView;
    }

    @GetMapping("/play/feed")
    public String feedCat(@AuthenticationPrincipal(expression = "attributes['email']") String email,
                          RedirectAttributes redirectAttributes) {
        try {
            Optional<User> userOpt = userRepository.findUserByEmail(email);

            if (userOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("flashMessage", "User not found.");
                return "redirect:/play";
            }

            User user = userOpt.get();
            List<Pet> pets = petRepository.findAllByUser(user);

            Pet activePet = pets.stream()
                    .filter(Pet::getIsActive)
                    .findFirst()
                    .orElse(null);

            if (activePet == null) {
                redirectAttributes.addFlashAttribute("flashMessage", "No active pet found.");
                return "redirect:/play";
            }
            // HUNGER STUFF
            // Update hunger
            activePet.setHunger(100);
            activePet.setLastUpdated(LocalDateTime.now());
            petRepository.save(activePet);

            redirectAttributes.addFlashAttribute("flashMessage", "You have fed " + activePet.getName() + "!");
        } catch (Exception e) {
            e.printStackTrace(); // For debug cus why aint it working?
            redirectAttributes.addFlashAttribute("flashMessage", "Something went wrong while feeding the cat.");
        }

        return "redirect:/play";
    }
    @GetMapping("/play/water")

    public String waterCat(@AuthenticationPrincipal(expression = "attributes['email']") String email,
                           RedirectAttributes redirectAttributes) {
        try {
            Optional<User> userOpt = userRepository.findUserByEmail(email);

            if (userOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("flashMessage", "User not found.");
                return "redirect:/play";
            }

            User user = userOpt.get();
            List<Pet> pets = petRepository.findAllByUser(user);

            Pet activePet = pets.stream()
                    .filter(Pet::getIsActive)
                    .findFirst()
                    .orElse(null);

            if (activePet == null) {
                redirectAttributes.addFlashAttribute("flashMessage", "No active pet found.");
                return "redirect:/play";
            }
            // THIRST STUFF
            // Update thirst
            activePet.setThirst(100);
            activePet.setThirstLastUpdated(LocalDateTime.now());
            petRepository.save(activePet);

            redirectAttributes.addFlashAttribute("flashMessage", "You have refilled " + activePet.getName() + "'s water bowl!");
        } catch (Exception e) {
            e.printStackTrace(); // For debug cus why aint it working?
            redirectAttributes.addFlashAttribute("flashMessage", "Something went wrong while filling up the water bowl.");
        }
        return "redirect:/play";
    }
    @GetMapping("/play/pet")
    public String petCat(@AuthenticationPrincipal(expression = "attributes['email']") String email,
                         RedirectAttributes redirectAttributes) {
        try {
            Optional<User> userOpt = userRepository.findUserByEmail(email);

            if (userOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("flashMessage", "User not found.");
                return "redirect:/play";
            }

            User user = userOpt.get();
            List<Pet> pets = petRepository.findAllByUser(user);

            Pet activePet = pets.stream()
                    .filter(Pet::getIsActive)
                    .findFirst()
                    .orElse(null);

            if (activePet == null) {
                redirectAttributes.addFlashAttribute("flashMessage", "No active pet found.");
                return "redirect:/play";
            }
            // SOCIAL STUFF
            // Update social
            activePet.setSocial(100);
            activePet.setSocialLastUpdated(LocalDateTime.now());
            petRepository.save(activePet);

            redirectAttributes.addFlashAttribute("flashMessage", "You have petted " + activePet.getName() + "!");
        } catch (Exception e) {
            e.printStackTrace(); // For debug cus why aint it working?
            redirectAttributes.addFlashAttribute("flashMessage", "Something went wrong while petting your cat.");
        }
        return "redirect:/play";
    }
    @GetMapping("/play/game")
    public String playGameWithCat(@AuthenticationPrincipal(expression = "attributes['email']") String email,
                                  RedirectAttributes redirectAttributes) {
        try {
            Optional<User> userOpt = userRepository.findUserByEmail(email);

            if (userOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("flashMessage", "User not found.");
                return "redirect:/play";
            }

            User user = userOpt.get();
            List<Pet> pets = petRepository.findAllByUser(user);

            Pet activePet = pets.stream()
                    .filter(Pet::getIsActive)
                    .findFirst()
                    .orElse(null);

            if (activePet == null) {
                redirectAttributes.addFlashAttribute("flashMessage", "No active pet found.");
                return "redirect:/play";
            }
            // fun STUFF
            // Update fun
            activePet.setFun(100);
            activePet.setFunLastUpdated(LocalDateTime.now());
            petRepository.save(activePet);

            redirectAttributes.addFlashAttribute("flashMessage", "You have played with " + activePet.getName() + "!");
        } catch (Exception e) {
            e.printStackTrace(); // For debug cus why aint it working?
            redirectAttributes.addFlashAttribute("flashMessage", "Something went wrong while playing with your cat.");
        }
        return "redirect:/play";
    }

    @GetMapping("/play/shoo")
    public String shooCat(@ModelAttribute("activePet") Pet activePet) {
        if (activePet != null) {
            activePet.setIsActive(false);
            petRepository.save(activePet);
        }
        return "redirect:/welcome";
    }

    @GetMapping("/play/confirm_shoo")
    public ModelAndView confirmShoo(@ModelAttribute("activePet") Pet activePet){
        ModelAndView modelAndView = new ModelAndView("shoo_cat");
        modelAndView.addObject("pet", activePet);
        return modelAndView;
    }
}


