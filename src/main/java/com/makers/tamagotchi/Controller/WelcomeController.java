package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.User;
import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Repository.UserRepository;
import com.makers.tamagotchi.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @RequestMapping(value = "/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("welcome");
    }

    @PostMapping("/start")
    public String handleWelcome(
            @RequestParam("displayName") String displayName,
            @RequestParam("catName") String catName,
            Authentication authentication) {

        // if user not authenticated, throws an error
        if (authentication == null) {
            throw new IllegalStateException("User not authenticated");
        }

        // gets email from authenticated user
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String email = (String) principal.getAttributes().get("email");

        // uses email to look up user in DB
        User user = userRepository.findUserByEmail(email)
                // otherwise, saves new user to DB
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setDisplayName(displayName);
                    newUser.setEnabled(true);
                    return userRepository.save(newUser);
                });

        // saves new cat in the DB
        Pet cat = new Pet();
        cat.setName(catName);
        cat.setUser(user);
        petRepository.save(cat);

        return "redirect:/play";
    }
}