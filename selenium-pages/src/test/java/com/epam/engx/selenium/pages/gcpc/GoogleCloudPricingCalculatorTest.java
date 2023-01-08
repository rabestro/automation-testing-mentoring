package com.epam.engx.selenium.pages.gcpc;

import com.epam.engx.selenium.pages.GoogleCloudPricingCalculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

import static com.epam.engx.selenium.pages.gcpc.Dropdown.*;
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
                .options()
                .toList();

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

        var frankfurt = datacenterLocation.options()
                .filter(loc -> loc.getText().startsWith("Frankfurt"))
                .findFirst();

        then(frankfurt)
                .isPresent();

        frankfurt.get().select();

        then(datacenterLocation.text())
                .as("set datacenter location as %s", frankfurt.get().getText())
                .isEqualTo("Frankfurt (europe-west3)");
    }

    @RepeatedTest(5)
    void set_series_to_n1() {
        var series = calculator.get()
                .setInstances(4)
                .new Menu("listingCtrl.computeServer.series");

        then(series.text())
                .as("default series after page load")
                .isEqualTo("E2");

        series.select("N1");

        then(series.text())
                .as("set series to N1")
                .isEqualTo("N1");

        var machineType = calculator
                .new Menu("listingCtrl.computeServer.instance");

        then(machineType.text())
                .as("default machine type for N1 series")
                .startsWith("n1-standard-1")
                .contains("vCPUs: 1")
                .contains("RAM: 3.75GB");
    }

    @RepeatedTest(10)
    void set_machine_Type_n1_standard_8() {
        calculator.get()
                .setInstances(4)
                .new Menu("listingCtrl.computeServer.series")
                .select("N1");

        var machineType = calculator
                .new Menu("listingCtrl.computeServer.instance");

        then(machineType.text())
                .as("default machine type for N1 series")
                .startsWith("n1-standard-1")
                .contains("vCPUs: 1")
                .contains("RAM: 3.75GB");

        machineType.select("n1-standard-8");

        then(machineType.text())
                .as("set machine type n1-standard-8")
                .startsWith("n1-standard-8")
                .contains("vCPUs: 8")
                .contains("RAM: 30GB");
    }

    @Test
    void first_setup() {
        calculator.get()
                .setInstances(4)
                .set(OS, "free")
                .set(PROVISIONING_MODEL, "regular")
                .set(MACHINE_FAMILY, "General")
                .set(SERIES, "n1")
                .set(INSTANCE_TYPE, "n1-standard-8")
                .set(DATACENTER_LOCATION, "Frankfurt");

        then(calculator.get(OS))
                .startsWith("Free")
                .contains("Debian", "CentOS", "CoreOS", "Ubuntu");

        then(calculator.get(PROVISIONING_MODEL))
                .isEqualTo("Regular");

        then(calculator.get(INSTANCE_TYPE))
                .as("machine type n1-standard-8")
                .startsWith("n1-standard-8")
                .contains("vCPUs: 8")
                .contains("RAM: 30GB");

        then(calculator.get(DATACENTER_LOCATION))
                .isEqualTo("Frankfurt (europe-west3)");
    }

    @Test
    void second_setup() {
        calculator.get()
                .setInstances(5)
                .set(OS, "free")
                .set(PROVISIONING_MODEL, "regular")
                .set(MACHINE_FAMILY, "General")
                .set(SERIES, "n1")
                .set(INSTANCE_TYPE, "n1-standard-16")
                .set(DATACENTER_LOCATION, "Sydney");

        then(calculator.parameters())
                .isNotEmpty()
                .containsKeys("computeServer.family")
                .containsValues("General purpose", "N1")
                .containsEntry("computeServer.instance", "n1-standard-16 (vCPUs: 16, RAM: 60GB)");
    }
}