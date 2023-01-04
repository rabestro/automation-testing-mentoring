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
    static void tearDown() throws InterruptedException {
//        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    @Order(1)
    @DisplayName("search for " + TERM)
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
    @DisplayName("open " + TERM)
    void openGoogleCloudPlatformPricingCalculator() {
        // when
        pricingCalculator = searchResult.goFirst(PricingCalculator::new);

        then(pricingCalculator)
                .as("open %s", TERM)
                .isNotNull();
    }

    @Test
    @Order(3)
    @DisplayName("set number of instances as 4")
    void setNumberOfInstances() {
        // when
        pricingCalculator.setInstances(4);

        then(pricingCalculator.getInstances())
                .isNotNull()
                .isNotBlank()
                .isEqualTo("4");
    }

    @Test
    @Order(4)
    @DisplayName("default series is E2")
    void checkSeries() {
        // when
        var selectedSeries = pricingCalculator.valueOf("series");

        then(selectedSeries)
                .isEqualTo("E2");
    }


    @Test
    @Order(5)
    @DisplayName("set series to N1")
    void setSeries() {
        // when
        pricingCalculator.dropdownMenu("series", "n1");

        then(pricingCalculator.valueOf("series"))
                .isEqualTo("N1");
    }

    @Test
    @Order(6)
    @DisplayName("set instance type: n1-standard-8 (vCPUs: 8, RAM: 30 GB)")
    void setInstanceType() {
        // given
        var instanceType = "n1-standard-8";

        // when
        pricingCalculator.dropdownMenu("instance", instanceType.toUpperCase());

        then(pricingCalculator.valueOf("instance"))
                .contains(instanceType)
                .contains("vCPUs: 8")
                .contains("RAM: 30");
    }

    @Test
    @Order(9)
    @DisplayName("set Local SSD to 2x375 Gb")
    void setLocalSSD() {
        // when
        pricingCalculator.dropdownMenu("ssd", "2");

        then(pricingCalculator.valueOf("ssd"))
                .contains("2x375");
    }

    @Test
    @Order(11)
    @DisplayName("set datacenter location: Frankfurt (europe-west3)")
    void setDatacenter() {
        // when
        pricingCalculator.dropdownMenu("location", "europe-west3");

        then(pricingCalculator.valueOf("location"))
                .contains("Frankfurt")
                .contains("europe-west3");
    }
}
