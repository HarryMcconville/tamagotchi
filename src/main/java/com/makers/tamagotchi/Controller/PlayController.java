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
import java.util.List;
import java.util.Random;

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
        List<String> messages;

        if (happiness < 5) {
            messages = List.of(
                    "I think it’s time to pack my whiskers and tail it outta here...",
                    "I folded my blanket and wrote my goodbye meow.",
                    "I left you a hairball with a note inside.",
                    "Don’t mind me, I’m just on my way to a furrier future.",
                    "Goodbye isn't fur-ever, but it might be!"
            );
        } else if (happiness < 15) {
            messages = List.of(
                    "I packed my favorite toy mouse… just in case I need to move my paws.",
                    "One paw in, three out-I’m nearly gone!",
                    "Time to pounce to a new beginning."
            );
        } else if (happiness < 30) {
            messages = List.of(
                    "Just wondering if anyone’s posted an ad for a lonely but loveable kitty...",
                    "Somewhere out there is a cat with a personal snack chef.",
                    "Paw-sibly wondering if someone else gets brushed more than once a week.",
                    "I heard some kitties get brushed just for fun. Living the dream!"
            );
        } else if (happiness < 45) {
            messages = List.of(
                    "I'm feline a little unloved... maybe it's time to prowl elsewhere.",
                    "I’m not kitten-I might go find a fluffier blanket elsewhere.",
                    "I’m paw-ndering a change in scenery.",
                    "Leaving? Meow-be. I’m on the fence...",
                    "Fur now, I’m still here. But my dreams are far away."
            );
        } else if (happiness < 60) {
            messages = List.of(
                    "Why do my fur-iends get more treats and chin scritches than me?",
                    "Other cats are living the life-I’m living on wishes!",
                    "I’m not saying I need a hug… but I might purr if you offer.",
                    "Even a small cuddle can spark a mighty purr.",
                    "Maybe if I curl up extra cute, you’ll notice me?",
                    "I believe in a better tomorrow—with treats!"
            );
        } else if (happiness < 75) {
            messages = List.of(
                    "Can I get a little more purr-sonal attention, please?",
                    "Hello? A meow-ment of your time please!",
                    "Feeling a smidge under-loved today...",
                    "Just one treat might fix everything.",
                    "Could I paw-sibly get a cuddle?",
                    "Excuse me, hooman? I’m ready for scritches!",
                    "I’m cute, cuddly, and a little under-hugged!",
                    "Need attention before I turn into a furball of feelings!"
            );
        } else if (happiness < 90) {
            messages = List.of(
                    "I’m a pretty pawsitive kitty right meow!",
                    "Life's looking purrty good!",
                    "I'm feline great today.",
                    "Life's pretty paw-some around here",
                    "I give today nine out of nine lives",
                    "Keep the snuggles coming—I’m purring for more!",
                    "I’m a whisker away from full-on joy!"
            );
        } else {
            messages = List.of(
                    "This is pawsitively the purrrfect life!",
                    "Feeling pawfect, don't change a thing!",
                    "I'm living my best nine lives.",
                    "Purrrfection achieved. No notes.",
                    "I'm so happy, my whiskers are dancing!",
                    "I’ve got love, naps, and a full food bowl. What a paw-ty!",
                    "Home is where the treats are… and I’m home!",
                    "Each cuddle fills up my heart and my whiskers."
            );
        }

        String statusMessage = messages.get(new Random().nextInt(messages.size()));
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