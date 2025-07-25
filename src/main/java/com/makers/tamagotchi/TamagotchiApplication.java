package com.makers.tamagotchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // allows for time decay
public class TamagotchiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TamagotchiApplication.class, args);
	}

}
