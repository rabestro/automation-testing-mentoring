package com.epam.engx.selenium.task3;

import com.epam.engx.selenium.pages.browser.Browser;
import com.epam.engx.selenium.pages.gcpc.GoogleCloudPricingCalculatorPage;
import com.epam.engx.selenium.pages.google.GoogleCloudPage;
import com.epam.engx.selenium.pages.google.SearchResultPage;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.and;
import static org.assertj.core.api.BDDAssertions.then;

@SuppressWarnings("AccessStaticViaInstance")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Search for a Google Cloud Pricing Calculator and Estimate Computer Engine")
final class SearchAndEstimateTest {
    private static final String TERM = "Google Cloud Pricing Calculator";
    private static final String FRANKFURT = "Frankfurt";
    private static Browser browser;
    private static SearchResultPage searchResultPage;

    @BeforeAll
    static void setUp() {
        browser = Browser.firefox();
    }

    @AfterAll
    static void tearDown() {
        browser.quit();
    }

    @Test
    @Order(1)
    @DisplayName("search for " + TERM)
    void searchGoogleCloudPlatformPricingCalculator() {
        // given
        var googleCloud = browser.go(GoogleCloudPage::new);

        // when
        searchResultPage = browser.go(googleCloud.search(TERM));

        then(searchResultPage.links())
                .as("Search results for %s", TERM)
                .isNotEmpty()
                .extracting("text")
                .contains(TERM);
    }

    @Test
    @Order(2)
    @DisplayName("estimate the monthly rent for a computer engine")
    void openGoogleCloudPlatformPricingCalculator() {
        // given
        searchResultPage.clickFirstLink();
        var pricingCalculator = browser.go(GoogleCloudPricingCalculatorPage::new);

        // when
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

        and.then(estimate.totalEstimatedCost())
                .as("the monthly rent for configured computer engine")
                .startsWith("Total Estimated Cost:")
                .endsWith("per 1 month")
                .contains("USD 1,081.20");
    }
}
