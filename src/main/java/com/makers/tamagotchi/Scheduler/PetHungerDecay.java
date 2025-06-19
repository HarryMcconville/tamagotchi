package com.makers.tamagotchi.Scheduler;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetHungerDecay {

    @Autowired
    private PetRepository petRepository;

    // can run every 5 mins just as a test but we can change this. the time is set to milliseconds
    @Scheduled(fixedRate = 10000) // 5 minutes = 300,000 ms but for testing i;ve set it to 10secs
    public void decayHunger() {
        List<Pet> pets = petRepository.findAll();

        for (Pet pet : pets) {
            int currentHunger = pet.getHunger();
            int newHunger = Math.max(0, currentHunger - 1); // will reduce hunger by 1% every 10 secs but never go bel 0
            pet.setHunger(newHunger);
            petRepository.save(pet);
        }

        System.out.println("Hunger decayed for pet by 1%.");
    }
}

