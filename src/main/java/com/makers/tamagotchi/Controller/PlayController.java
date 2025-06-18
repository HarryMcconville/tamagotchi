package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.User;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String feedCat(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("flashMessage", "You have fed your Cat!");
        return "redirect:/play";
    }

    @GetMapping("/play/water")
    public String waterCat(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("flashMessage", "You have watered your Cat!");
        return "redirect:/play";
    }

    @GetMapping("/play/pet")
    public String petCat(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("flashMessage", "You have petted your Cat!");
        return "redirect:/play";
    }

    @GetMapping("/play/game")
    public String playGameWithCat(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("flashMessage", "You have played with your Cat!");
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
}


