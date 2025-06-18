package com.makers.tamagotchi.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class CatController {

    private static final List<String> CAT_NAMES = List.of(
            "Gondola", "Georgina", "Deirdre", "Mabel", "Beatrice", "Clive", "Formaldehyde", "Victor", "Clarence", "Sackville"
    );

    private final Random random = new Random();

    @GetMapping("/api/random-cat-name")
    public Map<String, String> getRandomCatName() {
        String name = CAT_NAMES.get(random.nextInt(CAT_NAMES.size()));
        return Map.of("name", name);
    }
}
