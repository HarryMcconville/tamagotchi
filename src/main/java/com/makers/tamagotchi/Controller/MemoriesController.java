package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.User;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MemoriesController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/memories/fragment")
    public String getMemoriesFragment(@AuthenticationPrincipal(expression = "attributes['email']") String email,
                                      org.springframework.ui.Model model) {
        Optional<User> userOpt = userRepository.findUserByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Pet> pastPets = petRepository.findAllByUserAndIsActive(user, false);

            List<Map<String, Object>> pastPetsWithDuration = pastPets.stream().map(pet -> {
                Map<String, Object> map = new HashMap<>();
                map.put("pet", pet);
                map.put("daysOwned", Duration.between(pet.getCreatedAt(), pet.getLastUpdated()).toDays());
                return map;
            }).toList();

            model.addAttribute("pastPetsWithDuration", pastPetsWithDuration);
        } else {
            model.addAttribute("pastPetsWithDuration", List.of());
        }

        // This matches your fragment name and selector
        return "fragments/memories-fragment :: memoriesContent";
    }
}