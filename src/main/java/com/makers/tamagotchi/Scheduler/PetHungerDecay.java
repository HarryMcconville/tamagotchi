package com.makers.tamagotchi.Scheduler;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.Trait.StatType;
import com.makers.tamagotchi.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetHungerDecay {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetDecayService petDecayService;

    // can run every 5 mins just as a test but we can change this. the time is set to milliseconds
    @Scheduled(fixedRate = 30000) // 5 minutes = 300,000 ms but for testing i;ve set it to 10secs
    public void decayHunger() {
        List<Pet> pets = petRepository.findAll();

        for (Pet pet : pets) {

            if (!Boolean.TRUE.equals(pet.getIsActive())) {// only apply decay if pet is active.
                continue;
            }

            int baseDecay = 4;
            double modifier = petDecayService.getDecayModifier(pet, StatType.HUNGER);

            int decayAmount = (int) Math.round(baseDecay * modifier);

            int currentHunger = pet.getHunger();
            int newHunger = Math.max(0, currentHunger - decayAmount);

            pet.setHunger(newHunger);
            pet.setHappiness(pet.calculateHappiness());
            petRepository.save(pet);
        }
    }
}