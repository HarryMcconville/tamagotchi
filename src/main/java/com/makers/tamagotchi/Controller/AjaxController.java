package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.Trait;
import com.makers.tamagotchi.Model.User;
import com.makers.tamagotchi.Model.Village;
import com.makers.tamagotchi.Repository.PetRepository;
import com.makers.tamagotchi.Repository.UserRepository;
import com.makers.tamagotchi.Repository.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class AjaxController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VillageRepository villageRepository;

    // Helper method to get active pet for a user by email
    private Pet getActivePet(String email) {
        return userRepository.findUserByEmail(email)
                .flatMap(user -> petRepository.findAllByUser(user).stream()
                        .filter(Pet::getIsActive)
                        .findFirst())
                .orElse(null);
    }

    // helper method to get active village by user email
    private Village getActiveVillage(String email) {
        return userRepository.findUserByEmail(email)
                .map(user -> villageRepository.findAllByUser(user).stream()
                        .filter(Village::getIsActive)
                        .findFirst()
                        .orElse(null))
                .orElse(null);
    }

    // helper method to check for perk or flaw affecting restoration rate for stat
    private int calculateRestoredAmount(Pet pet, Trait.StatType statType, int baseAmount) {
        Trait perk = pet.getPerk();
        Trait flaw = pet.getFlaw();

        double modifier = 1.0;

        if (perk != null && perk.getStatType() == statType) {
            modifier = perk.getModifier();  // e.g. 1.25
        } else if (flaw != null && flaw.getStatType() == statType) {
            modifier = flaw.getModifier();  // e.g. 0.75
        }

        return (int) (baseAmount * modifier);
    }
    // this controller gets all the status figures from the database that the javascript will fetch from.
    @GetMapping("/api/status")
    public Map<String, Object> getPetStatus(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Pet activePet = getActivePet(email);
        Village activeVillage = getActiveVillage(email);
        Map<String, Object> status = new HashMap<>();

        if (activePet != null) {
            status.put("hunger", activePet.getHunger());
            status.put("thirst", activePet.getThirst());
            status.put("social", activePet.getSocial());
            status.put("fun", activePet.getFun());
            status.put("happiness", activePet.getHappiness());
            status.put("collectedMilk", activeVillage.getCollectedMilk());
            status.put("villageMilk", activeVillage.getMilk());
            status.put("collectedCatFood", activeVillage.getCollectedCatFood());
            status.put("villageCatFood", activeVillage.getCatfood());
            status.put("collectedCatnip", activeVillage.getCollectedCatnip());
            status.put("villageCatnip", activeVillage.getCatnip());
            status.put("collectedBrush", activeVillage.getCollectedBrush());
            status.put("villagebrush", activeVillage.getBrush());
        }
        return status;
    }
    @PostMapping("/village/milk")
    @ResponseBody
    public ResponseEntity<?> collectMilk(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Pet pet = getActivePet(email);
        if (pet == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active pet found."));
        }
        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active village found!"));
        }

        // Check if well has no milk
        if (activeVillage.getMilk() <= 0) {
            return ResponseEntity.ok(Map.of("message", "There is no milk left in the well! Try again later"));
        }

        // Take 1 milk from the well and add 1 to your inventory
        activeVillage.setMilk(activeVillage.getMilk() - 1);
        activeVillage.setCollectedMilk(activeVillage.getCollectedMilk() + 1);
        villageRepository.save(activeVillage);

        return ResponseEntity.ok(Map.of(
                "message", "You have gathered 1 bottle of milk for " + pet.getName() + " !",
                "collectedMilk", activeVillage.getCollectedMilk(),
                "villageMilk", activeVillage.getMilk()

        ));
    }
    @PostMapping("/village/catfood")
    @ResponseBody
    public ResponseEntity<?> collectFood(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Pet pet = getActivePet(email);
        if (pet == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active pet found."));
        }
        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active village found!"));
        }

        // Check if tree has no food
        if (activeVillage.getCatfood() <= 0) {
            return ResponseEntity.ok(Map.of("message", "There is no food left on the tree! Try again later"));
        }

        // Take 1 food from the tree and add 1 to your inventory
        activeVillage.setCatfood(activeVillage.getCatfood() - 1);
        activeVillage.setCollectedCatFood(activeVillage.getCollectedCatFood() + 1);
        villageRepository.save(activeVillage);


        return ResponseEntity.ok(Map.of(
                "message", "You have gathered 1 pack of cat food for " + pet.getName() + " !",
                "collectedCatFood", activeVillage.getCollectedCatFood(),
                "villageCatfood", activeVillage.getCatfood()

        ));
    }
    @PostMapping("/village/catnip")
    @ResponseBody
    public ResponseEntity<?> collectCatnip(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Pet pet = getActivePet(email);
        if (pet == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active pet found."));
        }
        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active village found!"));
        }

        // Check if greenhouse has no catnip
        if (activeVillage.getCatnip() <= 0) {
            return ResponseEntity.ok(Map.of("message", "There is no Catnip left in the greenhouse! Try again later"));
        }

        // Take 1 catnip from the greenhouse and add 1 to your inventory
        activeVillage.setCatnip(activeVillage.getCatnip() - 1);
        activeVillage.setCollectedCatnip(activeVillage.getCollectedCatnip() + 1);
        villageRepository.save(activeVillage);

        pet.setHunger(100);
        pet.setLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
        petRepository.save(pet);

        return ResponseEntity.ok(Map.of(
                "message", "You have gathered 1 Catnip for " + pet.getName() + " !",
                "collectedCatnip", activeVillage.getCollectedCatnip(),
                "villageCatnip", activeVillage.getCatnip()

        ));
    }
    @PostMapping("/village/brush")
    @ResponseBody
    public ResponseEntity<?> collectBrush(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Pet pet = getActivePet(email);
        if (pet == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active pet found."));
        }
        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active village found!"));
        }

        // Check if shop has no brushes
        if (activeVillage.getCatnip() <= 0) {
            return ResponseEntity.ok(Map.of("message", "There is no Catnip left in the greenhouse! Try again later"));
        }

        // Take 1 catnip from the greenhouse and add 1 to your inventory
        activeVillage.setBrush(activeVillage.getBrush() - 1);
        activeVillage.setCollectedBrush(activeVillage.getCollectedBrush() + 1);
        villageRepository.save(activeVillage);

        return ResponseEntity.ok(Map.of(
                "message", "You have gathered 1 Brush for " + pet.getName() + " !",
                "collectedBrush", activeVillage.getCollectedBrush(),
                "villageBrush", activeVillage.getBrush()

        ));
    }

    @PostMapping("/play/feed")
    @ResponseBody
    public ResponseEntity<?> feedCat(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Pet pet = getActivePet(email);
        if (pet == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active pet found."));
        }
        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active village found!"));
        }

        // Check if user has collected cat food
        if (activeVillage.getCollectedCatFood() <= 0) {
            return ResponseEntity.ok(Map.of("message", "You don't have any cat food! Visit the village to collect some."));
        }

        // Consume 1 cat food and feed the pet
        activeVillage.setCollectedCatFood(activeVillage.getCollectedCatFood() - 1);
        villageRepository.save(activeVillage);

        pet.setHunger(100);
        pet.setLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
        petRepository.save(pet);

        return ResponseEntity.ok(Map.of(
                "message", "You have fed " + pet.getName() + "!",
                "hunger", pet.getHunger(),
                "thirst", pet.getThirst(),
                "social", pet.getSocial(),
                "fun", pet.getFun(),
                "happiness", pet.getHappiness()
        ));
    }

    @PostMapping("/play/water")
    @ResponseBody
    public ResponseEntity<?> waterCat(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Pet pet = getActivePet(email);
        if (pet == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active pet found."));
        }
        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active village found!"));
        }
        // Check if user has collected milk
        if (activeVillage.getCollectedMilk() <= 0) {
            return ResponseEntity.ok(Map.of("message", "You don't have any Milk! Visit the village to collect some."));

        }
        // Consume 1 milk and give to the pet
        activeVillage.setCollectedMilk(activeVillage.getCollectedMilk() - 1);
        villageRepository.save(activeVillage);

        pet.setThirst(100);
        pet.setThirstLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
        petRepository.save(pet);

        return ResponseEntity.ok(Map.of(
                "message", "You have refilled " + pet.getName() + "'s bowl with Milk!",
                "hunger", pet.getHunger(),
                "thirst", pet.getThirst(),
                "social", pet.getSocial(),
                "fun", pet.getFun(),
                "happiness", pet.getHappiness()
        ));
    }

    @PostMapping("/play/pet")
    @ResponseBody
    public ResponseEntity<?> petCat(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Pet pet = getActivePet(email);
        if (pet == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active pet found."));
        }
        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active village found!"));
        }
        // Check if user has collected brushes
        if (activeVillage.getCollectedBrush() <= 0) {
            return ResponseEntity.ok(Map.of("message", "You don't have any brushes! Visit the village to collect some!."));
        }

        // Consume 1 brush and pet the cat
        activeVillage.setCollectedBrush(activeVillage.getCollectedBrush() - 1);
        villageRepository.save(activeVillage);

        pet.setSocial(100);
        pet.setSocialLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
        petRepository.save(pet);

        return ResponseEntity.ok(Map.of(
                "message", "You have brushed " + pet.getName() + "!",
                "hunger", pet.getHunger(),
                "thirst", pet.getThirst(),
                "social", pet.getSocial(),
                "fun", pet.getFun(),
                "happiness", pet.getHappiness()
        ));
    }

    @PostMapping("/play/game")
    @ResponseBody
    public ResponseEntity<?> playGameWithCat(@AuthenticationPrincipal(expression = "attributes['email']") String email) {
        Pet pet = getActivePet(email);
        if (pet == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active pet found."));
        }
        Village activeVillage = getActiveVillage(email);

        if (activeVillage == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "No active village found!"));
        }
        // Check if user has collected catnip
        if (activeVillage.getCollectedCatnip() <= 0) {
            return ResponseEntity.ok(Map.of("message", "You don't have any catnip! Visit the village to collect some!."));
        }
        // Consume 1 catnip and play with the cat
        activeVillage.setCollectedCatnip(activeVillage.getCollectedCatnip() - 1);
        villageRepository.save(activeVillage);

        pet.setFun(100);
        pet.setFunLastUpdated(LocalDateTime.now());
        pet.setHappiness(pet.calculateHappiness());
        petRepository.save(pet);

        return ResponseEntity.ok(Map.of(
                "message", "You have played with " + pet.getName() + "!",
                "hunger", pet.getHunger(),
                "thirst", pet.getThirst(),
                "social", pet.getSocial(),
                "fun", pet.getFun(),
                "happiness", pet.getHappiness()
        ));
    }
}