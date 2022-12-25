package com.epam.engx.task1;

import com.epam.engx.task1.page.PastebinPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("I Can Win")
final class ICanWinTest {
    private static final String CODE = "Hello from WebDriver";
    private static final String TITLE = "helloweb";

    private WebDriver driver;
    private PastebinPage pastebinPage;

    @BeforeEach
    public void setUp() {
        var prefs = Map.of("profile.default_content_setting_values.cookies", 2);
        driver = new ChromeDriver(new ChromeOptions()
                .addArguments("--incognito", "start-maximized")
                .setExperimentalOption("prefs", prefs)
        );
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(PastebinPage.HOMEPAGE_URL);
        pastebinPage = new PastebinPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Publish code snippets on PasteBin site")
    void postCode() {
        pastebinPage.postCode(TITLE, CODE);

        assertEquals(CODE, pastebinPage.code());
        assertEquals(TITLE, pastebinPage.title());
        assertEquals("10 Minutes", pastebinPage.expiration());
    }
}
