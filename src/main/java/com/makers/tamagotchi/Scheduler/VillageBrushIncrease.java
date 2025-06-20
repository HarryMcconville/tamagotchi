package com.makers.tamagotchi.Scheduler;

import com.makers.tamagotchi.Model.Village;
import com.makers.tamagotchi.Repository.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VillageBrushIncrease {

    @Autowired
    private VillageRepository villageRepository;

    // can run every 5 mins just as a test but we can change this. the time is set to milliseconds
    @Scheduled(fixedRate = 10000) // 5 minutes = 300,000 ms but for testing i;ve set it to 10secs
    public void increaseBrush() {
        List<Village> villages = villageRepository.findAll();

        for (Village village : villages) {
            int currentBrush = village.getBrush();
            int newBrush = Math.min(100, currentBrush + 1); // will increase by 1% every 10 secs but never go bel 0
            village.setBrush(newBrush);
            villageRepository.save(village);
        }

        System.out.println("Brushes increased for village by 1.");
    }
}

