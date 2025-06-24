package com.makers.tamagotchi.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity

@Table(name = "villages")
public class Village {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // for catfood
    private int catfood;
    @Column(name = "catfood_lastupdated")
    private LocalDateTime catFoodLastUpdated;
    @Column(name = "collected_catfood")
    private int collectedCatFood;

    // for milk
    private int milk;
    @Column(name = "milk_lastupdated")
    private LocalDateTime milkLastUpdated;
    @Column(name = "collected_milk")
    private int collectedMilk;
    // for catnip
    private int catnip;
    @Column(name = "catnip_lastupdated")
    private LocalDateTime catnipLastUpdated;
    @Column(name = "collected_catnip")
    private int collectedCatnip;
    // for brush
    private int brush;
    @Column(name = "brush_lastupdated")
    private LocalDateTime brushLastUpdated;
    @Column(name = "collected_brush")
    private int collectedBrush;

    // Empty constructor
    public Village() {
        this.catfood = 0;
        this.catFoodLastUpdated = LocalDateTime.now();
        this.collectedCatFood = 0;

        this.milk = 0;
        this.milkLastUpdated = LocalDateTime.now();
        this.collectedMilk = 0;

        this.catnip = 0;
        this.catnipLastUpdated = LocalDateTime.now();
        this.collectedCatnip = 0;

        this.brush = 0;
        this.brushLastUpdated = LocalDateTime.now();
        this.collectedBrush = 0;

        this.isActive = true;
    }


    public Village(Long id){
        this.id = id;
    }

    public Village(User user) {
        this.user = user;

        this.catfood = 0;
        this.catFoodLastUpdated = LocalDateTime.now();
        this.collectedCatFood = 0;

        this.milk = 0;
        this.milkLastUpdated = LocalDateTime.now();
        this.collectedMilk = 0;

        this.catnip = 0;
        this.catnipLastUpdated = LocalDateTime.now();
        this.collectedCatnip = 0;

        this.brush = 0;
        this.brushLastUpdated = LocalDateTime.now();
        this.collectedBrush = 0;

        this.isActive = true;
    }
}