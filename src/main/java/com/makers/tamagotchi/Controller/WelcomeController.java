package com.makers.tamagotchi.Controller;
import com.makers.tamagotchi.Model.Village;
import com.makers.tamagotchi.Repository.VillageRepository;
import com.makers.tamagotchi.Model.Trait;
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
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.*;

@Controller
public class WelcomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VillageRepository villageRepository;
    
    private Trait[] getRandomTraits() {
        Random random = new Random();

        Trait perk = Trait.getPerks().get(random.nextInt(Trait.getPerks().size()));

        Trait flaw;
        do {
            flaw = Trait.getFlaws().get(random.nextInt(Trait.getFlaws().size()));
        } while (flaw.getStatType() == perk.getStatType());

        return new Trait[]{perk, flaw};
    }

    @RequestMapping(value = "/welcome")
    public ModelAndView welcome(Authentication authentication, Model model) {

        // if user not authenticated, throws an error
        if (authentication == null) {
            throw new IllegalStateException("User not authenticated");
        }

        // gets email from authenticated user
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String email = (String) principal.getAttributes().get("email");

        // looks up user by email and assigns them to a variable
        // the user has to be Optional because they may not be present at this point
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        // if user exists, gets user data
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // checks user for active pet
            List<Pet> pets = petRepository.findAllByUser(user);
            for (Pet pet : pets) {
                if (Boolean.TRUE.equals(pet.getIsActive())) {
                    return new ModelAndView("redirect:/play");
                }
            }

            // if the user has already logged in before, creates variable displayName
            String displayName = user.getDisplayName();

            // checks if DisplayName present, if not then sets firstTimeLogin to true:
            if (displayName == null || displayName.trim().isEmpty()) {
                model.addAttribute("displayName", null);
                model.addAttribute("firstTimeLogin", true);

            // checks if DisplayName present, if so sets firstTimeLogin to false:
            } else {
                model.addAttribute("displayName", displayName);
                model.addAttribute("firstTimeLogin", false);
            }

        // if user not found, treated as first-time login:
        } else {
            // First-time login
            model.addAttribute("displayName", null);
            model.addAttribute("firstTimeLogin", true);
        }

        // randomly selects 4 cats from the cat images folder
                // listing the cats
        List<String> allCatImages = new ArrayList<>(List.of(
                "black-cat.png",
                "black-white-cat.png",
                "dark-brown-cat.png",
                "grey-cat.png",
                "mixed-black-cat.png",
                "mixed-blue-cat.png",
                "mixed-brown-cat.png",
                "mixed-grey-cat.png",
                "mixed-orange-cat.png",
                "mixed-purple-cat.png",
                "mixed-yellow-cat.png",
                "noodle-oti-cat.png",
                "orange-cat.png",
                "pale-brown-cat.png",
                "pale-grey-cat.png",
                "pale-yellow-cat.png",
                "rainbow-cat.png",
                "tiger-cat.png",
                "white-cat.png",
                "yellow-cat.png"
        ));
                // shuffling and taking 4 random ones
        Collections.shuffle(allCatImages);
        List<String> randomCatImages = allCatImages.subList(0, 4);
        model.addAttribute("randomCatImages", randomCatImages);

        return new ModelAndView("welcome");
    }


    @PostMapping("/start")
    public String handleWelcome(
            @RequestParam(value = "displayName", required = false) String displayName,
            @RequestParam("catName") String catName, @RequestParam("catImage") String catImage,
            Authentication authentication, RedirectAttributes redirectAttributes) {

        // if user not authenticated, throws an error
        if (authentication == null) {
            throw new IllegalStateException("User not authenticated");
        }

        // gets email from authenticated user
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String email = (String) principal.getAttributes().get("email");

        // uses email to look up user in DB
        User user = userRepository.findUserByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setDisplayName(displayName);
                    newUser.setEnabled(true);
                    User savedUser = userRepository.save(newUser);

                    // Create village for new user
                    Village village = new Village(savedUser);
                    villageRepository.save(village);

                    return savedUser;
                });

        // saves new cat in the DB
        Pet cat = new Pet();
        cat.setName(catName);
        cat.setUser(user);
        cat.setImage(catImage);

        Trait[] traits = getRandomTraits();
        cat.setPerk(traits[0]);
        cat.setFlaw(traits[1]);

        petRepository.save(cat);

        redirectAttributes.addFlashAttribute("newPetName", cat.getName());

        redirectAttributes.addFlashAttribute("newPetPerk", cat.getPerk().name().replace("_", " "));
        redirectAttributes.addFlashAttribute("newPetPerkDesc", cat.getPerk().getDescription());

        redirectAttributes.addFlashAttribute("newPetFlaw", cat.getFlaw().name().replace("_", " "));
        redirectAttributes.addFlashAttribute("newPetFlawDesc", cat.getFlaw().getDescription());

        redirectAttributes.addFlashAttribute("newPetImage", cat.getImage());

        return "redirect:/play";
    }

    @RequestMapping("/")
    public String redirectToWelcome() {
        return "redirect:/welcome";
    }

}