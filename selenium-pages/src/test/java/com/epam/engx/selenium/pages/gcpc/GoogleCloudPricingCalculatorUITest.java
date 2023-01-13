package com.epam.engx.selenium.pages.gcpc;

import com.epam.engx.selenium.pages.browser.Browser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Google Cloud Pricing Calculator")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GoogleCloudPricingCalculatorUITest {
    private static final String FRANKFURT = "Frankfurt";
    private Browser browser;
    private GoogleCloudPricingCalculator calculator;

    @BeforeEach
    void open_pricing_calculator_page() {
        browser = Browser.firefox();
        calculator = browser.go(GoogleCloudPricingCalculator::new);
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

    @RepeatedTest(5)
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

    @RepeatedTest(5)
    void set_machine_type_n1_standard_8() {
        calculator
                .model("computeServer.quantity").set("4")
                .model("computeServer.os").set("free")
                .model("computeServer.class").set("regular")
                .model("computeServer.series").set("n1");

        var machineType = calculator.model("instance");

        then(machineType.get())
                .as("default machine type for N1 series")
                .startsWith("n1-standard-1")
                .contains("vCPUs: 1")
                .contains("RAM: 3.75GB");

        machineType.set("n1-standard-8");

        then(machineType.get())
                .as("set machine type n1-standard-8")
                .startsWith("n1-standard-8")
                .contains("vCPUs: 8")
                .contains("RAM: 30GB");
    }

    @Test
    void the_initial_calculator_parameters_on_the_page_load() {
        then(calculator.parameters())
                .isNotEmpty()
                .hasSizeGreaterThan(10)
                .containsEntry("computeServer.class", "Regular")
                .containsEntry("computeServer.family", "General purpose")
                .containsEntry("computeServer.series", "E2")
                .containsEntry("computeServer.addGPUs", "false");
    }

    @Test
    void setting_the_parameters_required_by_the_task_3() {
        calculator
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

        then(calculator.parameters())
                .as("checking the set parameters")
                .isNotEmpty()
                .hasSizeGreaterThan(10)
                .containsEntry("computeServer.quantity", "4")
                .containsEntry("computeServer.class", "Regular")
                .containsEntry("computeServer.series", "N1")
                .containsEntry("computeServer.family", "General purpose")
                .containsEntry("computeServer.ssd", "2x375 GB")
                .containsEntry("computeServer.addGPUs", "true");

        var estimate = calculator.estimate();

        then(estimate.getItems())
                .as("parameters for estimate")
                .containsEntry("Region", FRANKFURT)
                .containsEntry("Provisioning model", "Regular")
                .containsEntry("Commitment term", "1 Year")
                .containsEntry("Instance type", "n1-standard-8")
                .containsEntry("Local SSD", "2x375 GiB");

        then(estimate.totalEstimatedCost())
                .as("the monthly rent")
                .startsWith("Total Estimated Cost:")
                .endsWith("per 1 month")
                .contains("USD 1,081.20");
    }
}
