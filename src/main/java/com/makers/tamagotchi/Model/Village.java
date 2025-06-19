package com.makers.tamagotchi.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity

@Table(name = "village")
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
    // for milk
    private int milk;
    @Column(name = "milk_lastupdated")
    private LocalDateTime milkLastUpdated;
    // for catnip
    private int catnip;
    @Column(name = "catnip_lastupdated")
    private LocalDateTime catnipLastUpdated;
    // for brush
    private int brush;
    @Column(name = "brush_lastupdated")
    private LocalDateTime brushLastUpdated;

    // Empty constructor
    public Village(){
        this.catfood = 0;
        this.catFoodLastUpdated = LocalDateTime.now();

        this.milk = 0;
        this.milkLastUpdated = LocalDateTime.now();

        this.catnip = 0;
        this.catnipLastUpdated = LocalDateTime.now();

        this.brush = 0;
        this.brushLastUpdated = LocalDateTime.now();

        this.isActive = true;

    }

    public Village(Long id){
        this.id = id;
    }







}