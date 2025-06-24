package com.makers.tamagotchi.Scheduler;

import com.makers.tamagotchi.Model.Village;
import com.makers.tamagotchi.Repository.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VillageCatFoodIncrease {

    @Autowired
    private VillageRepository villageRepository;

    // can run every 5 mins just as a test but we can change this. the time is set to milliseconds
    @Scheduled(fixedRate = 40000) // 5 minutes = 300,000 ms but for testing i;ve set it to 10secs
    public void increaseCatFood() {
        List<Village> villages = villageRepository.findAll();

        for (Village village : villages) {
            int currentCatFood = village.getCatfood();
            int newCatFood = Math.min(5, currentCatFood + 1); // will increase catfood by 1% every 10 secs but never go bel 0
            village.setCatfood(newCatFood);
            villageRepository.save(village);
        }

        System.out.println("Cat Food increased for village by 1.");
    }
}

