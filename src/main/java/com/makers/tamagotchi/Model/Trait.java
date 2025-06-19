package com.makers.tamagotchi.Model;

public enum Trait {

    // Hunger Depletion
    Efficient_Metabolism("HUNGER", EffectType.DEPLETION, 0.75,
            "Stays feeling full. Hunger drains 25% more slowly."),
    Always_Peckish("HUNGER", EffectType.DEPLETION, 1.25,
            "This cat is the snacky type. Hunger drains 25% faster."),

    // Hunger Replenishment
    Easy_Feeder("HUNGER", EffectType.REPLENISHMENT, 1.25,
            "This cat just loves to eat. Feeding restores 25% more hunger."),
    Picky_Eater("HUNGER", EffectType.REPLENISHMENT, 0.75,
            "Very choosy eater. Feeding restores 25% less hunger."),

    // Thirst Depletion
    Camel_Cat("THIRST", EffectType.DEPLETION, 0.75,
            "Ideal desert adventure companion. Thirst drains 25% more slowly."),
    Parched_Paws("THIRST", EffectType.DEPLETION, 1.25,
            "Quickly gets thirsty. Thirst drains 25% faster."),

    // Thirst Replenishment
    Gulper("THIRST", EffectType.REPLENISHMENT, 1.25,
            "Efficient lapping technique. Drinking restores 25% more thirst."),
    Sloppy_Drinker("THIRST", EffectType.REPLENISHMENT, 0.75,
            "This cat spills more than it drinks. Drinking restores 25% less thirst."),

    // Fun Depletion
    Low_Maintenance("FUN", EffectType.DEPLETION, 0.75,
            "Not easily bored. Fun drains 25% more slowly."),
    Restless_Spirit("FUN", EffectType.DEPLETION, 1.25,
            "Very playful. Fun drains 25% faster."),

    // Fun Replenishment
    Easily_Amused("FUN", EffectType.REPLENISHMENT, 1.25,
            "Finds joy easily. Fun restores 25% more."),
    Hard_To_Impress("FUN", EffectType.REPLENISHMENT, 0.75,
            "Hard to please. Fun restores 25% less."),

    // Social Depletion
    Loner("SOCIAL", EffectType.DEPLETION, 0.75,
            "Prefers solitude. Social drains 25% more slowly."),
    Clingy_Whiskers("SOCIAL", EffectType.DEPLETION, 1.25,
            "Craves attention. Social drains 25% faster."),

    // Social Replenishment
    Love_Bug("SOCIAL", EffectType.REPLENISHMENT, 1.25,
            "Loves affection. Social restores 25% more."),
    Stubborn_Stranger("SOCIAL", EffectType.REPLENISHMENT, 0.75,
            "Difficult to bond with. Social restores 25% less.");

    private final String stat;
    private final EffectType effectType;
    private final double modifier;
    private final String description;

    Trait(String stat, EffectType effectType, double modifier, String description) {
        this.stat = stat;
        this.effectType = effectType;
        this.modifier = modifier;
        this.description = description;
    }

    public String getStat() {
        return stat;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public double getModifier() {
        return modifier;
    }

    public String getDescription() {
        return description;
    }

    public enum EffectType {
        DEPLETION,
        REPLENISHMENT
    }
}