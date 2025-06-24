package com.makers.tamagotchi.Model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class VillageModelTest {

    @Test
    @DisplayName("Should create village with default constructor")
    void testDefaultConstructor() {
        Village village = new Village();

        assertThat(village.getCatfood()).isEqualTo(0);
        assertThat(village.getMilk()).isEqualTo(0);
        assertThat(village.getCatnip()).isEqualTo(0);
        assertThat(village.getBrush()).isEqualTo(0);
        assertThat(village.getCollectedCatFood()).isEqualTo(0);
        assertThat(village.getCollectedMilk()).isEqualTo(0);
        assertThat(village.getCollectedCatnip()).isEqualTo(0);
        assertThat(village.getCollectedBrush()).isEqualTo(0);
        assertThat(village.getIsActive()).isTrue();
        assertThat(village.getCatFoodLastUpdated()).isNotNull();
        assertThat(village.getMilkLastUpdated()).isNotNull();
        assertThat(village.getCatnipLastUpdated()).isNotNull();
        assertThat(village.getBrushLastUpdated()).isNotNull();
    }

    @Test
    @DisplayName("Should create village with user constructor")
    void testUserConstructor() {
        User user = new User();
        user.setId(1L);
        user.setDisplayName("Whiskers");

        Village village = new Village(user);

        assertThat(village.getUser()).isEqualTo(user);
        assertThat(village.getIsActive()).isTrue();
        assertThat(village.getCatfood()).isZero();
        assertThat(village.getCollectedMilk()).isZero();
        assertThat(village.getBrushLastUpdated()).isNotNull();
    }

    @Test
    @DisplayName("Should allow setting and getting values")
    void testSettersAndGetters() {
        Village village = new Village();
        village.setId(99L);
        village.setCatfood(5);
        village.setMilk(10);
        village.setCollectedMilk(3);
        village.setIsActive(false);

        assertThat(village.getId()).isEqualTo(99L);
        assertThat(village.getCatfood()).isEqualTo(5);
        assertThat(village.getMilk()).isEqualTo(10);
        assertThat(village.getCollectedMilk()).isEqualTo(3);
        assertThat(village.getIsActive()).isFalse();
    }

    @Test
    @DisplayName("Should create village by ID only")
    void testConstructorWithIdOnly() {
        Village village = new Village(101L);
        assertThat(village.getId()).isEqualTo(101L);
    }

    @Test
    @DisplayName("Should update resource timestamps")
    void testTimestampUpdate() {
        Village village = new Village();

        LocalDateTime past = village.getCatFoodLastUpdated();
        village.setCatFoodLastUpdated(LocalDateTime.now().plusHours(1));

        assertThat(village.getCatFoodLastUpdated()).isAfter(past);
    }
}
