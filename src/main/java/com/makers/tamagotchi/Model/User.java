package com.makers.tamagotchi.Model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_name")
    private String displayName;
    private boolean enabled;
    private String email;

    // empty constructor
    public User() {}

    // constructor for creating the user
    public User(Long id, String displayName, boolean enabled, String email) {
        this.id = id;
        this.displayName = displayName;
        this.enabled = enabled;
        this.email = email;
    }
}