package com.epam.engx.selenium.task3;

import com.epam.engx.selenium.pages.GoogleCloud;
import com.epam.engx.selenium.pages.SearchResult;
import com.epam.engx.selenium.pages.gcpc.GoogleCloudPricingCalculator;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Search for a Google Cloud Pricing Calculator and Estimate Computer Engine")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class SearchAndEstimateTest {
    private static final String TERM = "Google Cloud Platform Pricing Calculator";
    private static final String FRANKFURT = "Frankfurt";

    private static WebDriver driver;
    private static SearchResult searchResult;

    @BeforeAll
    static void setUp() {
        var prefs = Map.of("profile.default_content_setting_values.cookies", 2);
        driver = new ChromeDriver(new ChromeOptions()
                .addArguments("--incognito", "start-maximized")
                .setExperimentalOption("prefs", prefs)
        );
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("search for " + TERM)
    void searchGoogleCloudPlatformPricingCalculator() {
        searchResult = GoogleCloud.openPage(driver).search(TERM);

        then(searchResult.links())
                .as("Search results for %s", TERM)
                .isNotEmpty()
                .extracting("text")
                .contains(TERM);
    }

    @Test
    @Order(2)
    @DisplayName("estimate the monthly rent for a computer engine")
    void openGoogleCloudPlatformPricingCalculator() {
        var pricingCalculator = searchResult
                .goFirst(GoogleCloudPricingCalculator::new);

        pricingCalculator
                .model("computeServer.quantity").set("4")
                .model("computeServer.os").set("free")
                .model("computeServer.class").set("regular")
                .model("computeServer.series").set("n1")
                .model("computeServer.instance").set("n1-standard-8")
                .model("computeServer.addGPUs").set("true")
                .model("computeServer.gpuType").set("NVIDIA Tesla V100")
                .model("computeServer.gpuCount").set("1")
                .model("computeServer.ssd").set("2x375")
                .model("computeServer.location").set(FRANKFURT)
                .model("computeServer.cud").set("1");

        var estimate = pricingCalculator.estimate();

        then(estimate.getItems())
                .as("parameters in the Estimate result block")
                .containsEntry("Region", FRANKFURT)
                .containsEntry("Provisioning model", "Regular")
                .containsEntry("Commitment term", "1 Year")
                .containsEntry("Instance type", "n1-standard-8")
                .containsEntry("Local SSD", "2x375 GiB");

        then(estimate.totalEstimatedCost())
                .as("the monthly rent for configured computer engine")
                .startsWith("Total Estimated Cost:")
                .endsWith("per 1 month")
                .contains("USD 1,081.20");
    }
}
