package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Village;
import com.makers.tamagotchi.Model.User;
import com.makers.tamagotchi.Repository.VillageRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class VillageController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VillageRepository villageRepository;

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
    public ModelAndView villageSquare(@ModelAttribute("activeVillage") Village village) {
        ModelAndView modelAndView = new ModelAndView("village_square");
        modelAndView.addObject("village", village);
        return modelAndView;
    }

    @GetMapping("/village/catFood")
    public String harvestFood(@AuthenticationPrincipal(expression = "attributes['email']") String email,
                              RedirectAttributes redirectAttributes) {
        try {
            Optional<User> userOpt = userRepository.findUserByEmail(email);

            if (userOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("flashMessage", "User not found.");
                return "redirect:/village";
            }

            User user = userOpt.get();
            List<Village> villages = villageRepository.findAllByUser(user);

            Village activeVillage = villages.stream()
                    .filter(Village::getIsActive)
                    .findFirst()
                    .orElse(null);

            if (activeVillage == null) {
                redirectAttributes.addFlashAttribute("flashMessage", "No active village found.");
                return "redirect:/village";
            }

            int available = activeVillage.getCatfood();

            if (available > 0) {
                activeVillage.setCatfood(available - 1);
                activeVillage.setCollectedCatFood(activeVillage.getCollectedCatFood() + 1);
                activeVillage.setCatFoodLastUpdated(LocalDateTime.now());
                villageRepository.save(activeVillage);
                redirectAttributes.addFlashAttribute("flashMessage", "You collected 1 cat food!");
            } else {
                redirectAttributes.addFlashAttribute("flashMessage", "No cat food available yet.");
            }

            return "redirect:/village";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flashMessage", "Error collecting cat food.");
            return "redirect:/village";
        }
    }

}


