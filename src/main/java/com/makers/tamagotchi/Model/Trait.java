package com.makers.tamagotchi.Model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum Trait {

    // Hunger Depletion (Perks and Flaws)
    Efficient_Metabolism(StatType.HUNGER, EffectType.DEPLETION, 0.75, TraitType.PERK,
            "Stays feeling full. Hunger drains 25% more slowly."),
    Always_Peckish(StatType.HUNGER, EffectType.DEPLETION, 1.25, TraitType.FLAW,
            "This cat is the snacky type. Hunger drains 25% faster."),

    // Hunger Replenishment
    Easy_Feeder(StatType.HUNGER, EffectType.REPLENISHMENT, 1.25, TraitType.PERK,
            "This cat just loves to eat. Feeding restores 25% more hunger."),
    Picky_Eater(StatType.HUNGER, EffectType.REPLENISHMENT, 0.75, TraitType.FLAW,
            "Very choosy eater. Feeding restores 25% less hunger."),

    // Thirst Depletion
    Camel_Cat(StatType.THIRST, EffectType.DEPLETION, 0.75, TraitType.PERK,
            "Ideal desert adventure companion. Thirst drains 25% more slowly."),
    Parched_Paws(StatType.THIRST, EffectType.DEPLETION, 1.25, TraitType.FLAW,
            "Quickly gets thirsty. Thirst drains 25% faster."),

    // Thirst Replenishment
    Gulper(StatType.THIRST, EffectType.REPLENISHMENT, 1.25, TraitType.PERK,
            "Efficient lapping technique. Drinking restores 25% more thirst."),
    Sloppy_Drinker(StatType.THIRST, EffectType.REPLENISHMENT, 0.75, TraitType.FLAW,
            "This cat spills more than it drinks. Drinking restores 25% less thirst."),

    // Fun Depletion
    Low_Maintenance(StatType.FUN, EffectType.DEPLETION, 0.75, TraitType.PERK,
            "Not easily bored. Fun drains 25% more slowly."),
    Restless_Spirit(StatType.FUN, EffectType.DEPLETION, 1.25, TraitType.FLAW,
            "Very playful. Fun drains 25% faster."),

    // Fun Replenishment
    Easily_Amused(StatType.FUN, EffectType.REPLENISHMENT, 1.25, TraitType.PERK,
            "Finds joy easily. Fun restores 25% more."),
    Hard_To_Impress(StatType.FUN, EffectType.REPLENISHMENT, 0.75, TraitType.FLAW,
            "Hard to please. Fun restores 25% less."),

    // Social Depletion
    Loner(StatType.SOCIAL, EffectType.DEPLETION, 0.75, TraitType.PERK,
            "Prefers solitude. Social drains 25% more slowly."),
    Clingy_Whiskers(StatType.SOCIAL, EffectType.DEPLETION, 1.25, TraitType.FLAW,
            "Craves attention. Social drains 25% faster."),

    // Social Replenishment
    Love_Bug(StatType.SOCIAL, EffectType.REPLENISHMENT, 1.25, TraitType.PERK,
            "Loves affection. Social restores 25% more."),
    Stubborn_Stranger(StatType.SOCIAL, EffectType.REPLENISHMENT, 0.75, TraitType.FLAW,
            "Difficult to bond with. Social restores 25% less.");

    private final StatType statType;
    private final EffectType effectType;
    private final double modifier;
    private final TraitType traitType;
    private final String description;

    Trait(StatType statType, EffectType effectType, double modifier, TraitType traitType, String description) {
        this.statType = statType;
        this.effectType = effectType;
        this.modifier = modifier;
        this.traitType = traitType;
        this.description = description;
    }

    public enum EffectType {
        DEPLETION,
        REPLENISHMENT
    }

    public enum TraitType {
        PERK,
        FLAW
    }

    public enum StatType {
        HUNGER,
        THIRST,
        FUN,
        SOCIAL
    }

    // Helper methods (optional)
    public static List<Trait> getPerks() {
        return Arrays.stream(values())
                .filter(t -> t.getTraitType() == TraitType.PERK)
                .collect(Collectors.toList());
    }

    public static List<Trait> getFlaws() {
        return Arrays.stream(values())
                .filter(t -> t.getTraitType() == TraitType.FLAW)
                .collect(Collectors.toList());
    }
}