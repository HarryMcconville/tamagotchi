package com.makers.tamagotchi.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity

@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //New hunger level:
    private int hunger;

    //Hunger time_decay element:
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    //Thirst level:
    private int thirst;

    //Thirst timestamp:
    @Column(name = "thirst_last_updated")
    private LocalDateTime thirstLastUpdated;

    //Social level
    private int social;

    // Social timestamp:
    @Column(name = "social_last_updated")
    private LocalDateTime socialLastUpdated;

    // Fun level
    private int fun;

    //Fun timestamp:
    @Column(name = "fun_last_updated")
    private LocalDateTime funLastUpdated;


    // fetches the user so it can be assigned to the cat as a foreign key
    // ManyToOne establishes that one user can have many cats
    // by default, fetchType for ManyToOne is "EAGER" which means that when the pet
    // is called, the whole of the user info is also fetched. Switching to "LAZY"
    // means we only fetch user data when specifically called upon. It's not
    // necessary but improves performance
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    private String image;

    // Empty constructor with defaults
    public Pet() {
        this.hunger = 100;
        this.lastUpdated = LocalDateTime.now();
        this.isActive = true;
        this.thirst = 100;
        this.thirstLastUpdated = LocalDateTime.now();
        this.social = 100;
        this.socialLastUpdated = LocalDateTime.now();
        this.fun = 100;
        this.funLastUpdated = LocalDateTime.now();
    }

    // Referring to Pet Id on db
    public Pet(Long id){
        this.id = id;
    }

    // Full constructor used in your service/controller
    public Pet(String name, User user, String image) {
        this.name = name;
        this.user = user;
        this.image = image;
        this.isActive = true;
        this.hunger = 100;
        this.lastUpdated = LocalDateTime.now();
        this.thirst = 100;
        this.thirstLastUpdated = LocalDateTime.now();
        this.social = 100;
        this.socialLastUpdated = LocalDateTime.now();
        this.fun = 100;
        this.funLastUpdated = LocalDateTime.now();
    }
}