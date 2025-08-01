package com.makers.tamagotchi.Scheduler;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.Trait.StatType;
import com.makers.tamagotchi.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetThirstDecay {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetDecayService petDecayService;

    // can run every 5 mins just as a test but we can change this. the time is set to milliseconds
    @Scheduled(fixedRate = 30000) // 5 minutes = 300,000 ms but for testing i;ve set it to 20secs
    public void decayThirst() {
        List<Pet> pets = petRepository.findAll();

        for (Pet pet : pets) {

            // Skip inactive pets
            if (!Boolean.TRUE.equals(pet.getIsActive())) {
                continue;
            }

            int baseDecay = 4;
            double modifier = petDecayService.getDecayModifier(pet, StatType.THIRST);

            int decayAmount = (int) Math.round(baseDecay * modifier);
            int currentThirst = pet.getThirst();
            int newThirst = Math.max(0, currentThirst - decayAmount);

            pet.setThirst(newThirst);
            pet.setHappiness(pet.calculateHappiness());
            petRepository.save(pet);
        }
    }
}