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
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/village/milk")
    public String harvestMilk(@AuthenticationPrincipal(expression = "attributes['email']") String email,
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

            int available = activeVillage.getMilk();

            if (available > 0) {
                activeVillage.setMilk(available - 1);
                activeVillage.setCollectedMilk(activeVillage.getCollectedMilk() + 1);
                activeVillage.setMilkLastUpdated(LocalDateTime.now());
                villageRepository.save(activeVillage);
                redirectAttributes.addFlashAttribute("flashMessage", "You collected 1 bucket of milk!");
            } else {
                redirectAttributes.addFlashAttribute("flashMessage", "No milk available yet.");
            }

            return "redirect:/village";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flashMessage", "Error collecting milk");
            return "redirect:/village";
        }
    }

    @GetMapping("/village/catnip")
    public String harvestCatnip(@AuthenticationPrincipal(expression = "attributes['email']") String email,
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

            int available = activeVillage.getCatnip();

            if (available > 0) {
                activeVillage.setCatnip(available - 1);
                activeVillage.setCollectedCatnip(activeVillage.getCollectedCatnip() + 1);
                activeVillage.setCatnipLastUpdated(LocalDateTime.now());
                villageRepository.save(activeVillage);
                redirectAttributes.addFlashAttribute("flashMessage", "You collected 1 pouch of catnip!");
            } else {
                redirectAttributes.addFlashAttribute("flashMessage", "No catnip available yet.");
            }

            return "redirect:/village";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flashMessage", "Error collecting catnip.");
            return "redirect:/village";
        }
    }

    @GetMapping("/village/brush")
    public String harvestBrush(@AuthenticationPrincipal(expression = "attributes['email']") String email,
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

            int available = activeVillage.getBrush();

            if (available > 0) {
                activeVillage.setBrush(available - 1);
                activeVillage.setCollectedBrush(activeVillage.getCollectedBrush() + 1);
                activeVillage.setBrushLastUpdated(LocalDateTime.now());
                villageRepository.save(activeVillage);
                redirectAttributes.addFlashAttribute("flashMessage", "You collected 1 brush!");
            } else {
                redirectAttributes.addFlashAttribute("flashMessage", "No brushes available yet.");
            }

            return "redirect:/village";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flashMessage", "Error collecting brushes");
            return "redirect:/village";
        }
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


