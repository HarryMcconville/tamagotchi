package com.makers.tamagotchi.Scheduler;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetThirstDecay {

    @Autowired
    private PetRepository petRepository;

    // can run every 5 mins just as a test but we can change this. the time is set to milliseconds
    @Scheduled(fixedRate = 20000) // 5 minutes = 300,000 ms but for testing i;ve set it to 20secs
    @Scheduled(fixedRate = 20000) // 20 seconds for testing
    public void decayThirst() {
        List<Pet> pets = petRepository.findAll();

        for (Pet pet : pets) {
            // Skip inactive pets
            if (!Boolean.TRUE.equals(pet.getIsActive())) {
                continue;
            }

            int currentThirst = pet.getThirst();
            int newThirst = Math.max(0, currentThirst - 1); // reduce thirst by 1%
            pet.setThirst(newThirst);
            pet.setHappiness(pet.calculateHappiness());
            petRepository.save(pet);
        }

        System.out.println("Thirst decayed for active pets by 1%.");
    }

}
