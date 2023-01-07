package com.epam.engx.selenium.pages.gcpc;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Google Cloud Pricing Calculator")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GoogleCloudPricingCalculatorTest {
    private WebDriver driver;
    private GoogleCloudPricingCalculator calculator;

    @BeforeEach
    void setUp() {
        var prefs = Map.of("profile.default_content_setting_values.cookies", 2);
        driver = new ChromeDriver(new ChromeOptions()
                .addArguments("--incognito", "start-maximized")
                .setExperimentalOption("prefs", prefs)
        );
        driver.manage().window().maximize();
        calculator = new GoogleCloudPricingCalculator(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void the_number_of_instances_is_empty_on_page_load() {
        // when
        calculator.get();

        then(calculator.getInstances())
                .isEmpty();
    }

    @ParameterizedTest(name = "number of instances is {0, number}")
    @ValueSource(ints = {1, 3, 5, 15})
    void setting_the_number_of_instances(int instances) {
        // when
        calculator.get();
        calculator.setInstances(instances);

        then(calculator.getInstances())
                .isEqualTo(Integer.toString(instances));
    }

    @Test
    void default_operating_system_and_software() {
        // when
        var operatingSystem = calculator.get()
                .new Menu("listingCtrl.computeServer.os");

        then(operatingSystem.text())
                .startsWith("Free")
                .contains("Debian", "CentOS", "CoreOS", "Ubuntu");
    }

    @Test
    void default_provisioning_model() {
        // when
        var provisioningModel = calculator.get()
                .new Menu("listingCtrl.computeServer.class");

        then(provisioningModel.text())
                .isEqualTo("Regular");
    }

    @Test
    void default_machine_family() {
        // when
        calculator.get();
        var machineFamily = calculator.new Menu("listingCtrl.computeServer.family");

        then(machineFamily.text())
                .isEqualTo("General purpose");
    }


    @Test
    void default_datacenter_location() {
        // when
        var datacenterLocation = calculator.get()
                .new Menu("listingCtrl.computeServer.location");

        then(datacenterLocation.text())
                .isEqualTo("Iowa (us-central1)");
    }

    @Test
    void available_datacenter_locations() {
        // when
        var locations = calculator.get()
                .new Menu("listingCtrl.computeServer.location")
                .options();

        then(locations)
                .as("Google has at least ten data centers")
                .isNotEmpty()
                .hasSizeGreaterThan(10)
                .extracting("value")
                .contains("us-central1", "us-east1", "us-west1", "europe-west1");

        then(locations)
                .as("Google has data centers in London and Frankfurt")
                .extracting("text")
                .contains("London (europe-west2)", "Frankfurt (europe-west3)");
    }

    @RepeatedTest(5)
    void select_frankfurt_datacenter() {
        // when
        var datacenterLocation = calculator.get()
                .new Menu("listingCtrl.computeServer.location");

        var datacenter = datacenterLocation.options()
                .stream()
                .filter(loc -> loc.getText().startsWith("Frankfurt"))
                .findFirst();

        then(datacenter)
                .isPresent();

        var frankfurt = datacenter.get();
        frankfurt.select();

        then(datacenterLocation.text())
                .as("set datacenter location as %s", frankfurt.getText())
                .isEqualTo("Frankfurt (europe-west3)");
    }
}