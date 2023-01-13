package com.epam.engx.selenium.task4;

import com.epam.engx.selenium.pages.browser.Browser;
import com.epam.engx.selenium.pages.gcpc.GoogleCloudPricingCalculator;
import com.epam.engx.selenium.pages.google.GoogleCloud;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.and;
import static org.assertj.core.api.BDDAssertions.then;

@SuppressWarnings("AccessStaticViaInstance")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Search for a Pricing Calculator, Estimate computer engine and Send email")
class HardcoreTest {
    private static final String FRANKFURT = "Frankfurt";
    private static Browser browser;

    private static GoogleCloudPricingCalculator pricingCalculator;

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
    void search_for_google_cloud_platform_pricing_calculator() {
        // given
        var googleCloud = browser.go(GoogleCloud::new);
        var searchResults = googleCloud
                .search("Google Cloud Platform Pricing Calculator");

        browser.go(searchResults).clickFirstLink();

        // when
        pricingCalculator = browser.go(GoogleCloudPricingCalculator::new);

        then(pricingCalculator.parameters())
                .as("initial state of the parameters")
                .isNotEmpty()
                .hasSizeGreaterThan(10)
                .containsEntry("computeServer.class", "Regular")
                .containsEntry("computeServer.family", "General purpose")
                .containsEntry("computeServer.series", "E2")
                .containsEntry("computeServer.addGPUs", "false");
    }

    @Test
    @Order(2)
    void estimate_the_monthly_rent_for_a_computer_engine() {
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
