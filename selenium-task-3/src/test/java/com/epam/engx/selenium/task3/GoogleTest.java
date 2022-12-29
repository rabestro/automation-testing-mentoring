package com.epam.engx.selenium.task3;

import com.epam.engx.selenium.pages.GoogleCloud;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

final class GoogleTest {
    private static final String TERM = "Google Cloud Platform Pricing Calculator";

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        var prefs = Map.of("profile.default_content_setting_values.cookies", 2);
        driver = new ChromeDriver(new ChromeOptions()
                .addArguments("--incognito", "start-maximized")
                .setExperimentalOption("prefs", prefs)
        );
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("search for Google Cloud Platform Pricing Calculator")
    void searchGoogleCloudPlatformPricingCalculator() {

        // given
        var googlePage = GoogleCloud.openPage(driver);

        // when
        var links = googlePage.search(TERM).links();

        then(links)
                .as("Search results for %s", TERM)
                .isNotEmpty()
                .extracting("text")
                .contains(TERM);
    }
}
