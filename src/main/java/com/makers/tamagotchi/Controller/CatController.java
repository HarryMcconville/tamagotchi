package com.makers.tamagotchi.Controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class CatController {

    // ResourceLoader is part of Spring, helps locate and open files (in this case, cat-names.txt)
    private final ResourceLoader resourceLoader;
    private final Random random = new Random();

    public CatController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // this route is called every time a user clicks "Choose for me"
    // it can be made more efficient later for performance if needed, so only loads file once
    @GetMapping("/api/random-cat-name")
    public Map<String, String> getRandomCatName() {

        // this is try-catch structured to throw an error reliably if it fails
        try {

            //resourceLoader navigates to the file and assigns it to "resource"
            Resource resource = resourceLoader.getResource("classpath:/data/cat-names.txt");

            // reads the file, each line converted to an item in a list, stored in "catNames"
            List<String> catNames = Files.readAllLines(resource.getFile().toPath());

            // uses nextInt to randomly get a number between 0 and the length of catNames (200)
            String name = catNames.get(random.nextInt(catNames.size()));

            // returns JSON-style map
            return Map.of("name", name);

        // catches errors and throws an exception in event of failure
        } catch (Exception e) {
            throw new RuntimeException("Could not read cat names", e);
        }
    }
}