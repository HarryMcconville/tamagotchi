package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.User;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AjaxController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;
// this controller gets all the status figures from the database that the javascript will fetch from.
    @GetMapping("/api/status")
    public Map<String, Object> getPetStatus(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Optional<User> userOpt = userRepository.findUserByEmail(email);
        Map<String, Object> status = new HashMap<>();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Pet activePet = petRepository.findAllByUser(user).stream()
                    .filter(Pet::getIsActive)
                    .findFirst()
                    .orElse(null);

            if (activePet != null) {
                status.put("hunger", activePet.getHunger());
                status.put("thirst", activePet.getThirst());
                status.put("social", activePet.getSocial());
                status.put("fun", activePet.getFun());
//                status.put("happiness", activePet.getHappiness()); --- commented out until Maddy looks at.
            }
        }

        return status;
    }
}
