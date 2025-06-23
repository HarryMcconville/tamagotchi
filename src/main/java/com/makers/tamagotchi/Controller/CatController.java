package com.makers.tamagotchi.Controller;

import com.makers.tamagotchi.Model.Pet;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/api/status-message")
    public Map<String, String> getRandomStatusMessage(@RequestParam("happiness") int happiness) {
        List<String> messages;

        //        showing status messages in thought bubble
        if (happiness < 5) {
            messages = List.of(
                    "I think it’s time to pack my whiskers and tail it outta here...",
                    "I folded my blanket and wrote my goodbye meow.",
                    "I left you a hairball with a note inside.",
                    "Don’t mind me, I’m just on my way to a furrier future.",
                    "Goodbye isn't fur-ever, but it might be!"
            );
        } else if (happiness < 15) {
            messages = List.of(
                    "I packed my favorite toy mouse… just in case I need to move my paws.",
                    "One paw in, three out-I’m nearly gone!",
                    "Time to pounce to a new beginning."
            );
        } else if (happiness < 30) {
            messages = List.of(
                    "Just wondering if anyone’s posted an ad for a lonely but loveable kitty...",
                    "Somewhere out there is a cat with a personal snack chef.",
                    "Paw-sibly wondering if someone else gets brushed more than once a week.",
                    "I heard some kitties get brushed just for fun. Living the dream!"
            );
        } else if (happiness < 45) {
            messages = List.of(
                    "I'm feline a little unloved... maybe it's time to prowl elsewhere.",
                    "I’m not kitten-I might go find a fluffier blanket elsewhere.",
                    "I’m paw-ndering a change in scenery.",
                    "Leaving? Meow-be. I’m on the fence...",
                    "Fur now, I’m still here. But my dreams are far away."
            );
        } else if (happiness < 60) {
            messages = List.of(
                    "Why do my fur-iends get more treats and chin scritches than me?",
                    "Other cats are living the life-I’m living on wishes!",
                    "I’m not saying I need a hug… but I might purr if you offer.",
                    "Even a small cuddle can spark a mighty purr.",
                    "Maybe if I curl up extra cute, you’ll notice me?",
                    "I believe in a better tomorrow—with treats!"
            );
        } else if (happiness < 75) {
            messages = List.of(
                    "Can I get a little more purr-sonal attention, please?",
                    "Hello? A meow-ment of your time please!",
                    "Feeling a smidge under-loved today...",
                    "Just one treat might fix everything.",
                    "Could I paw-sibly get a cuddle?",
                    "Excuse me, hooman? I’m ready for scritches!",
                    "I’m cute, cuddly, and a little under-hugged!",
                    "Need attention before I turn into a furball of feelings!"
            );
        } else if (happiness < 90) {
            messages = List.of(
                    "I’m a pretty pawsitive kitty right meow!",
                    "Life's looking purrty good!",
                    "I'm feline great today.",
                    "Life's pretty paw-some around here",
                    "I give today nine out of nine lives",
                    "Keep the snuggles coming—I’m purring for more!",
                    "I’m a whisker away from full-on joy!"
            );
        } else {
            messages = List.of(
                    "This is pawsitively the purrrfect life!",
                    "Feeling pawfect, don't change a thing!",
                    "I'm living my best nine lives.",
                    "Purrrfection achieved. No notes.",
                    "I'm so happy, my whiskers are dancing!",
                    "I’ve got love, naps, and a full food bowl. What a paw-ty!",
                    "Home is where the treats are… and I’m home!",
                    "Each cuddle fills up my heart and my whiskers."
            );
        }

        String message = messages.get(new Random().nextInt(messages.size()));
        return Map.of("message", message);
    }
}