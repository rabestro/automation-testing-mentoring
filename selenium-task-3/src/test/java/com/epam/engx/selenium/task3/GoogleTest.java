package com.epam.engx.selenium.task3;

import com.epam.engx.selenium.pages.GoogleCloud;
import com.epam.engx.selenium.pages.WebDriverFabric;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.BDDAssertions.then;

final class GoogleTest {
    private static final String TERM = "Google Cloud Platform Pricing Calculator";

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new WebDriverFabric().incognito();
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
