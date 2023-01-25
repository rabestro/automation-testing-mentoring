package com.epam.engx.selenium.pages.gcpc;

import com.epam.engx.selenium.pages.browser.Browser;
import com.epam.engx.selenium.pages.test.PricingCalculatorParameterizedTest;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Google Cloud Pricing Calculator")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GoogleCloudPricingCalculatorPageUITest {
    private static final String FRANKFURT = "Frankfurt";
    private Browser browser;
    private GoogleCloudPricingCalculatorPage calculator;

    @BeforeEach
    void open_pricing_calculator_page() {
        browser = Browser.create();
        calculator = browser.go(GoogleCloudPricingCalculatorPage::new);
    }

    @AfterEach
    void close_tabs_and_quit_browser() {
        browser.quit();
    }

    @Test
    void the_number_of_instances_is_empty_on_page_load() {
        var instancesNumber = calculator.model("computeServer.quantity").get();

        then(instancesNumber).isEmpty();
    }


    @ParameterizedTest(name = "number of instances is {0}")
    @ValueSource(strings = {"1", "3", "5", "15"})
    void setting_the_number_of_instances(String instances) {
        var model = calculator.model("computeServer.quantity");
        model.set(instances);

        then(model.get()).isEqualTo(instances);
    }

    @Test
    void default_operating_system_and_software() {
        var os = calculator.model("computeServer.os");

        then(os.get())
                .startsWith("Free")
                .contains("Debian", "CentOS", "CoreOS", "Ubuntu");
    }

    @Test
    void default_datacenter_location() {
        var datacenter = calculator.model("computeServer.location");

        then(datacenter.get())
                .startsWith("Iowa")
                .contains("us-central1");
    }

    @RepeatedTest(5)
    void select_frankfurt_datacenter() {
        // given
        var datacenter = calculator.model("location");

        // when
        datacenter.set(FRANKFURT);

        then(datacenter.get())
                .as("Datacenter location is %s", FRANKFURT)
                .startsWith(FRANKFURT)
                .contains("europe-west");
    }

    @Test
    void set_series_to_n1() {
        var series = calculator.model("series");

        then(series.get())
                .as("default series after page load")
                .isEqualTo("E2");

        series.set("N1");

        then(series.get())
                .as("set series to N1")
                .isEqualTo("N1");

        var instance = calculator.model("instance").get();

        then(instance)
                .as("default machine type for N1 series")
                .startsWith("n1-standard-1")
                .contains("vCPUs: 1")
                .contains("RAM: 3.75GB");
    }

    @Test
    void the_initial_calculator_parameters_on_the_page_load() {
        then(calculator.getParameters())
                .isNotEmpty()
                .hasSizeGreaterThan(10)
                .containsEntry("computeServer.class", "Regular")
                .containsEntry("computeServer.family", "General purpose")
                .containsEntry("computeServer.series", "E2")
                .containsEntry("computeServer.addGPUs", "false");
    }

    @PricingCalculatorParameterizedTest
    void calculate_estimate_monthly_cost(@SuppressWarnings("unused") String description,
                                         Map<String, String> params, Money expectedCost
    ) {
        // given
        calculator.setParameters(params);

        // when
        var estimate = calculator.estimate();

        then(estimate.getItems())
                .as("parameters for estimate")
                .containsEntry("Region", params.get("location"))
                .containsEntry("Provisioning model", "Regular")
                .containsEntry("Commitment term", "1 Year")
                .containsEntry("Instance type", params.get("instance"))
                .containsEntry("Local SSD", "2x375 GiB");

        then(estimate.estimatedMonthlyCost())
                .as("the estimated monthly cost")
                .isEqualByComparingTo(expectedCost);
    }
}
