package com.makers.tamagotchi.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PlayController {

    @GetMapping("/play")
    public String livingRoom() {
        return "living_room";
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
}


