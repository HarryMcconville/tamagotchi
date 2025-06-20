//package com.makers.tamagotchi.Model;
//
//
//
//import com.makers.tamagotchi.Model.User;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class UserModelTest {
//
//    @Test
//    public void testUserConstructorAndFields() {
//        // When given a new user with the below info:
//        User user = new User(1L, "Noodle", true, "noodle@noods.com");
//        // The userID should match:
//        assertEquals(1L, user.getId());
//        // The displayName should match:
//        assertEquals("Noodle", user.getDisplayName());
//        // They should be enabled in the db:
//        assertTrue(user.isEnabled());
//        // And their email should match:
//        assertEquals("noodle@noods.com", user.getEmail());
//
//        System.out.println("Noodle's User constructor and Fields tested successfully!");
//    }
//
//    @Test
//    public void testUserSettersAndGetters() {
//        User user = new User();
//        user.setId(2L);
//        user.setDisplayName("Sipsi");
//        user.setEnabled(false);
//        user.setEmail("sipsi@potatochip.com");
//
//        assertEquals(2L, user.getId());
//        assertEquals("Sipsi", user.getDisplayName());
//        assertFalse(user.isEnabled());
//        assertEquals("sipsi@potatochip.com", user.getEmail());
//        System.out.println("Sipsi's Setters and Getters tested successfully!");
//    }
//}
//
