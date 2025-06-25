package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.Village;
import com.makers.tamagotchi.Model.User;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.VillageRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class VillageController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VillageRepository villageRepository;
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
                .orElseThrow(VillageController.NoActivePetException::new);  // throws instead of returning null
    }

    // this handles "no active pet" exception by redirecting to /welcome
    @ExceptionHandler(VillageController.NoActivePetException.class)
    public String handleNoActivePet() {
        return "redirect:/welcome";
    }

    @ModelAttribute("activeVillage")
    public Village getActiveVillage(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            List<Village> villages = villageRepository.findAllByUser(userOptional.get());
            for (Village village : villages) {
                if (village.getIsActive()) {
                    return village;
                }
            }
        }
        return null;
    }

    @GetMapping("/village")
    public ModelAndView villageSquare(@ModelAttribute("activeVillage") Village village,
                                      @AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Pet activePet = getActivePet(email);
        if (activePet == null) {
            return new ModelAndView("redirect:/welcome");
        }
        ModelAndView modelAndView = new ModelAndView("village_square");
        modelAndView.addObject("village", village);
        return modelAndView;
    }

    @GetMapping("/api/village_resources")
    @ResponseBody
    public Map<String, Integer> getResourceCounts(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Optional<User> userOpt = userRepository.findUserByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Village> villages = villageRepository.findAllByUser(user);
            Village activeVillage = villages.stream().filter(Village::getIsActive).findFirst().orElse(null);

            if (activeVillage != null) {
                return Map.of(
                        "catFood", activeVillage.getCollectedCatFood(),
                        "milk", activeVillage.getCollectedMilk(),
                        "catnip", activeVillage.getCollectedCatnip(),
                        "brush", activeVillage.getCollectedBrush()
                );
            }
        }

        return Map.of(); // empty map if user or village not found
    }

    @GetMapping("/api/village_quote")
    @ResponseBody
    public Map<String, String> getVillageQuote() {
        List<Map<String, String>> quotes = List.of(
                // "Store" keepers (2 each)
                Map.of("quote", "Ever try brushing a cat who thinks the brush is a fierce rival? Welcome to my daily adventure.", "author", "Brush Shop Owner"),
                Map.of("quote", "These brushes aren’t just tools—they’re peacekeepers in a land of fur and claws.", "author", "Brush Shop Owner"),
                Map.of("quote", "Sometimes the catnip whispers secrets I’m not supposed to hear. Or maybe I’m just imagining things.", "author", "Greenhouse Keeper"),
                Map.of("quote", "The greenhouse is full of life—and mischief. One sniff and the cats forget all their worries.", "author", "Greenhouse Keeper"),
                Map.of("quote", "Milk here isn’t just for drinking—it’s a potion for courage and the occasional nap.", "author", "Milk Well Keeper"),
                Map.of("quote", "I swear the well hums softly at night, like it’s telling tales from the village’s past.", "author", "Milk Well Keeper"),
                Map.of("quote", "The food tree is a gift. Every bite feels like a story unfolding in the sunshine.", "author", "Cat Food Orchard Keeper"),
                Map.of("quote", "Cats gather ‘round like it’s a festival. The tree’s bounty never fails to inspire joy.", "author", "Cat Food Orchard Keeper"),

                // Cats (1 each)
                Map.of("quote", "Caught a leaf. Let it go. Caught it again. Life is beautiful.", "author", "Pip"),
                Map.of("quote", "Got into a staring contest with a bird. It blinked first.", "author", "Captain Claws"),
                Map.of("quote", "Brushed my tail 47 times today. New record.", "author", "Whiskella"),
                Map.of("quote", "Is it just me or does the milk well smell like tuna?", "author", "Bramble"),
                Map.of("quote", "I'm not lazy. I'm conserving energy for a dramatic pounce.", "author", "Ziggy"),
                Map.of("quote", "Someone planted catnip in the mayor’s flower bed. Again.", "author", "Deputy Whisp"),
                Map.of("quote", "Why does no one take the brush merchant seriously? Brushes are life.", "author", "Biscuit"),
                Map.of("quote", "The food tree whispered to me. I think it likes me.", "author", "Socks"),

                // Villagers (1 each)
                Map.of("quote", "I heard that old Mr. Finch, the greenhouse owner, has been sneaking in at night with a curious box. What could he be hiding?", "author", "Mrs. Baker"),
                Map.of("quote", "Sometimes, in the stillness of Pawston, I hear whispers not meant for mortal ears. There’s more to this town than meets the eye.", "author", "Reverend Blackwood"),
                Map.of("quote", "I saw something strange down at the orchard last week—shadows moving between the trees, but no one was there. Spooky, right?", "author", "Timmy"),
                Map.of("quote", "Pawston’s spirit is unmatched—where neighbors become family and every corner hides a story waiting to be told. It’s truly home.", "author", "Mayor Goodwill"),
                Map.of("quote", "Word is the catnip greenhouse owner talks to his plants. Maybe that’s why the cats never stray too far—something magical in the air.", "author", "Mrs. Dalloway"),
                Map.of("quote", "Strange noises at the milk well have been reported lately. Could just be the wind—or something else watching over Pawston.", "author", "Constable Harris"),
                Map.of("quote", "I remember when the food tree first bloomed—it was a sign. They say the tree chooses its caretakers carefully. I still watch it closely.", "author", "Mrs. Carmichael"),
                Map.of("quote", "Pawston might look peaceful, but I swear there’s a secret beneath those streets. Old tunnels, they say. I wouldn’t go poking around after dark.", "author", "Mr. Grayson")
        );

        int randomIndex = (int) (Math.random() * quotes.size());
        return quotes.get(randomIndex);
    }
}