package com.epam.engx.selenium.task3;

import com.epam.engx.selenium.pages.GoogleCloud;
import com.epam.engx.selenium.pages.PricingCalculator;
import com.epam.engx.selenium.pages.SearchResult;
import com.epam.engx.selenium.pages.WebDriverFabric;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.BDDAssertions.then;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class GoogleTest {
    private static final String TERM = "Google Cloud Platform Pricing Calculator";
    private static WebDriver driver;
    private static SearchResult searchResult;
    // Subject under test
    private static PricingCalculator pricingCalculator;


    @BeforeAll
    static void setUp() {
        driver = new WebDriverFabric().incognito();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("search for Google Cloud Platform Pricing Calculator")
    void searchGoogleCloudPlatformPricingCalculator() {
        // when
        searchResult = GoogleCloud.openPage(driver).search(TERM);

        then(searchResult.links())
                .as("Search results for %s", TERM)
                .isNotEmpty()
                .extracting("text")
                .contains(TERM);


    }

    @Test
    @Order(2)
    @DisplayName("open Google Cloud Platform Pricing Calculator")
    void openGoogleCloudPlatformPricingCalculator() {
        // when
        pricingCalculator = searchResult.goFirst(PricingCalculator.class);

        then(pricingCalculator)
                .as("open %s", TERM)
                .isNotNull();
    }

    @Test
    @Order(3)
    @DisplayName("estimate")
    void estimate() {
        // when
        pricingCalculator.setQuantity(4);

        then(pricingCalculator.quantity())
                .isNotNull()
                .isNotBlank()
                .isEqualTo("4");
    }
}
