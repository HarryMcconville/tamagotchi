package com.makers.tamagotchi.Model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PetModelTest {

    @Test
    @DisplayName("Should create pet with name and user, and initialize defaults correctly")
    void testPetConstructorWithNameAndUser() {
        // Given
        User user = new User(null, "TestUser", true, "test@user.com");

        // When
        Pet pet = new Pet("Noodle", user);

        // Then
        assertThat(pet.getName()).isEqualTo("Noodle");
        assertThat(pet.getUser()).isEqualTo(user);
        assertThat(pet.getIsActive()).isTrue();
        assertThat(pet.getHunger()).isEqualTo(100);
        assertThat(pet.getThirst()).isEqualTo(100);
        assertThat(pet.getSocial()).isEqualTo(100);
        assertThat(pet.getFun()).isEqualTo(100);
        assertThat(pet.getHappiness()).isEqualTo(100);
        assertThat(pet.getCreatedAt()).isNull(); // Will be set only on persist
        assertThat(pet.getLastUpdated()).isNotNull();
        assertThat(pet.getThirstLastUpdated()).isNotNull();
        assertThat(pet.getSocialLastUpdated()).isNotNull();
        assertThat(pet.getFunLastUpdated()).isNotNull();
    }

    @Test
    @DisplayName("Should allow setting and getting all fields manually")
    void testSettersAndGetters() {
        User user = new User();
        user.setId(1L);
        user.setDisplayName("Owner");
        user.setEmail("owner@pets.com");
        user.setEnabled(true);

        Pet pet = new Pet();
        pet.setId(10L);
        pet.setName("Noodle");
        pet.setUser(user);
        pet.setHunger(80);
        pet.setThirst(70);
        pet.setSocial(90);
        pet.setFun(60);
        pet.setIsActive(false);
        pet.setImage("cat.png");
        pet.setPerk(Trait.Love_Bug);
        pet.setFlaw(Trait.Picky_Eater);


        assertThat(pet.getId()).isEqualTo(10L);
        assertThat(pet.getName()).isEqualTo("Noodle");
        assertThat(pet.getUser()).isEqualTo(user);
        assertThat(pet.getHunger()).isEqualTo(80);
        assertThat(pet.getThirst()).isEqualTo(70);
        assertThat(pet.getSocial()).isEqualTo(90);
        assertThat(pet.getFun()).isEqualTo(60);
        assertThat(pet.getIsActive()).isFalse();
        assertThat(pet.getImage()).isEqualTo("cat.png");
        pet.setPerk(Trait.Love_Bug);
        pet.setFlaw(Trait.Picky_Eater);

    }

    @Test
    @DisplayName("Should calculate average happiness correctly")
    void testCalculateHappiness() {
        Pet pet = new Pet();
        pet.setHunger(80);
        pet.setThirst(60);
        pet.setSocial(40);
        pet.setFun(20);

        int expected = (80 + 60 + 40 + 20) / 4;
        assertThat(pet.calculateHappiness()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should set createdAt on persist")
    void testCreatedAtSetting() {
        Pet pet = new Pet();
        assertThat(pet.getCreatedAt()).isNull(); // Before persist

        pet.onCreate(); // Simulate @PrePersist
        assertThat(pet.getCreatedAt()).isNotNull();
        assertThat(pet.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should allow constructing Pet by ID")
    void testConstructorWithIdOnly() {
        Pet pet = new Pet(99L);
        assertThat(pet.getId()).isEqualTo(99L);
    }
}
