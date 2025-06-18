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

    //new hunger level:
    private int hunger;

    //new time_decay element:
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

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

    // Empty constructor
    public Pet(){

    }

    // Referring to Pet Id on db
    public Pet(Long id){
        this.id = id;
    }

    public Pet(String name, User user){
        this.name = name;
        this.user = user;
        this.isActive = true;
        this.hunger = 100; // means the bar is full when created
        this.lastUpdated = LocalDateTime.now(); // this will track when hunger was last checked

    }
}