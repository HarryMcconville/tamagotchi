package com.makers.tamagotchi.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter

@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "user_id")
    private Long userId;

    // Empty constructor
    public Pet(){

    }

    // Referring to Pet Id on db
    public Pet(Long id){
        this.id = id;
    }

    public Pet(String name, Long userId){
        this.name = name;
        this.userId = userId;
    }

}


