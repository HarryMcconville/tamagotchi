package com.makers.tamagotchi.Model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;  // e.g. "auth0|abc12345678"
    @Column(name = "display_name")
    private String displayName;
    private boolean enabled;
    private String email;
    // empty constructor
    public User() {}
    // constructor for creating the user
    public User(String id, String displayName, boolean enabled, String email) {
        this.id = id;
        this.displayName = displayName;
        this.enabled = enabled;
        this.email = email;
    }
}