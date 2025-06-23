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
    public void decayThirst() {
        List<Pet> pets = petRepository.findAll();

        for (Pet pet : pets) {
            int currentThirst = pet.getThirst();
            int newThirst = Math.max(0, currentThirst - 1); // will reduce thirst by 1% every 20 secs but never go bel 0
            pet.setThirst(newThirst);
            pet.setHappiness(pet.calculateHappiness());
            petRepository.save(pet);
        }

        System.out.println("Thirst decayed for pet by 1%.");
    }
}

