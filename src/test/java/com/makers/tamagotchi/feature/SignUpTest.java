package com.makers.tamagotchi.feature;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class SignUpTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private Faker faker;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        faker = new Faker();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void userCanSignUpViaAuth0() {
        String email = faker.name().username().replace(".", "") + "@example.com";
        String password = "Password123!";

        // when we visit the site
        driver.get("http://localhost:8080/");

        // and we click on signup
        WebElement signUpLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign up")));
        signUpLink.click();

        // we should be able to fill out email and password
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys(password);


        // we should then be able to click submit
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

// Add waits for selenium
        try {

            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            if (!iframes.isEmpty()) {
                driver.switchTo().frame(iframes.get(0));
            }

            // Accept the authorization
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button[name='accept'], button[data-action='accept'], button[type='submit']")
            ));
            acceptButton.click();

            // Further waits
            driver.switchTo().defaultContent();

        } catch (TimeoutException e) {
            System.out.println("Consent screen not shown – likely already accepted.");
        }

// we should then be redirected to the /welcome page
        wait.until(ExpectedConditions.urlContains("/welcome"));
        String currentUrl = driver.getCurrentUrl();
        assertThat("Should be redirected to welcome page", currentUrl, containsString("/welcome"));
    }
}