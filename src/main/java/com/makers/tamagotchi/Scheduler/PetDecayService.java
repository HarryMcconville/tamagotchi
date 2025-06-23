package com.makers.tamagotchi.Scheduler;

import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.Trait;
import com.makers.tamagotchi.Model.Trait.EffectType;
import com.makers.tamagotchi.Model.Trait.StatType;
import org.springframework.stereotype.Service;

@Service
public class PetDecayService {

    // Return the decay modifier based on flaw or perk that affects decay of the stat
    public double getDecayModifier(Pet pet, StatType statType) {
        Trait perk = pet.getPerk();
        Trait flaw = pet.getFlaw();

        // Flaws affect decay (depletion) by design
        if (flaw != null && flaw.getStatType() == statType && flaw.getEffectType() == EffectType.DEPLETION) {
            return flaw.getModifier();
        }

        // Perks may also affect decay but typically they affect replenishment; adjust if your design differs
        if (perk != null && perk.getStatType() == statType && perk.getEffectType() == EffectType.DEPLETION) {
            return perk.getModifier();
        }

        return 1.0; // no modifier
    }

    // Helper to get the trait name that applies to decay for logging
    public String getActiveDecayTraitName(Pet pet, StatType statType) {
        Trait flaw = pet.getFlaw();
        Trait perk = pet.getPerk();

        if (flaw != null && flaw.getStatType() == statType && flaw.getEffectType() == EffectType.DEPLETION) {
            return "flaw '" + flaw.toString() + "'";
        }

        if (perk != null && perk.getStatType() == statType && perk.getEffectType() == EffectType.DEPLETION) {
            return "perk '" + perk.toString() + "'";
        }

        return "no modifier";
    }
}