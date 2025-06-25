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


}


