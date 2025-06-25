package com.makers.tamagotchi.feature;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class AdoptACatTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private Faker faker;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        faker = new Faker();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void userCanSignUpAndAdoptCat() {
        String email = faker.name().username().replace(".", "") + "@example.com";
        String password = "Password123!";

        // when we visit the site
        driver.get("http://localhost:8080/");

        // and we click on sign up
        WebElement signUpLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign up")));
        signUpLink.click();

        // we should be able to fill out email and password
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);
        passwordField.submit();

        // Add waits for selenium for the accept button
        try {
            wait.until(ExpectedConditions.urlContains("/consent"));

            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            if (!iframes.isEmpty()) {
                driver.switchTo().frame(iframes.get(0));
            }

            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button[name='accept'], button[data-action='accept'], button[type='submit']")));
            acceptButton.click();
            driver.switchTo().defaultContent();
        } catch (TimeoutException e) {
            System.out.println("Consent screen not shown, likely already accepted.");
        }

        // Wait for redirect to /welcome
        wait.until(ExpectedConditions.urlContains("/welcome"));

        // Adopt a cat
        List<WebElement> catImages = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("img.cat-option"))
        );
        WebElement firstCat = catImages.get(0);
        firstCat.click();

        WebElement catImageInput = driver.findElement(By.id("catImageInput"));
        assertFalse(catImageInput.getAttribute("value").isEmpty(), "Expected a cat to be selected");

        String catName = faker.name().firstName();
        WebElement catNameField = driver.findElement(By.id("catName"));
        catNameField.sendKeys(catName);

        List<WebElement> displayNameFields = driver.findElements(By.id("displayName"));
        if (!displayNameFields.isEmpty()) {
            displayNameFields.get(0).sendKeys(faker.name().firstName());
        }

        WebElement startButton = driver.findElement(By.cssSelector("button[type='submit']"));
        startButton.click();

        // Step 7: We should then be redirected to /play
        wait.until(ExpectedConditions.urlContains("/play"));
        assertThat(driver.getTitle(), containsString("Home"));
    }
}
