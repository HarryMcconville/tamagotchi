//package com.makers.tamagotchi.Model;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class PetModelTest {
//
//    @Test
//    @DisplayName("Should create pet with name and user") // This is the test name
//    void testPetConstructorWithNameAndUser() {
//        // Given a user
//        User user = new User(null, "TestUser", true, "test@user.com");
//
//        // When creating a pet
//        Pet pet = new Pet("Noodle", user);
//
//        // Then properties should match
//        assertThat(pet.getName()).isEqualTo("Noodle");
//        assertThat(pet.getUser()).isEqualTo(user);
//        System.out.println("Test name Should create pet with name and user passed successfully!");
//    }
//
//    @Test
//    @DisplayName("Should allow setting and getting pet fields")
//    void testSettersAndGetters() {
//        // Given a user:
//        User user = new User();
//        user.setId(1L);
//        user.setDisplayName("Owner");
//        user.setEmail("owner@pets.com");
//        user.setEnabled(true);
//        // With a pet:
//        Pet pet = new Pet();
//        pet.setId(10L);
//        pet.setName("Sipsi");
//        pet.setUser(user);
//
//        // Then the pet should be matched to the user
//        assertThat(pet.getId()).isEqualTo(10L);
//        assertThat(pet.getName()).isEqualTo("Sipsi");
//        assertThat(pet.getUser().getDisplayName()).isEqualTo("Owner");
//        System.out.println("Test name Should allow getting and setting pet fields passed successfully!");
//    }
//
//    @Test
//    @DisplayName("Should allow constructing Pet by ID")
//    void testConstructorWithIdOnly() {
//        Pet pet = new Pet(99L);
//        //This tests that the pet ID will match when using the ID only constructor
//        assertThat(pet.getId()).isEqualTo(99L);
//        System.out.println("Test name Should allow constructing Pet by ID passed successfully!");
//    }
//}
