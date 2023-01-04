package com.epam.engx.selenium.task3;

import com.epam.engx.selenium.pages.PricingCalculator;
import com.epam.engx.selenium.pages.WebDriverFabric;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

final class PricingCalculatorTest {
    private static final String N1_STANDARD_8 = "N1-STANDARD-8";
    private static final String SSD_TYPE = "2x375";
    private static final String FRANKFURT = "Frankfurt";

    private static WebDriver driver;

    // Subject under test
    private static PricingCalculator pricingCalculator;

    @BeforeAll
    static void setUp() {
        driver = new WebDriverFabric().incognito();

        pricingCalculator = PricingCalculator
                .openPage(driver)
                .setInstances(4)
                .dropdownMenu("computeServer.series", "n1")
                .dropdownMenu("computeServer.instance", "N1-STANDARD-8")
//                .dropdownMenu("computeServer.ssd", "2")
                .checkbox("computeServer.addGPUs");
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    void checkInstances() {
        assertThat(pricingCalculator.getInstances())
                .isEqualTo("4");
    }

    @Test
    void checkSeries() {
        assertThat(pricingCalculator.valueOf("computeServer.series"))
                .isEqualTo("N1");
    }

    void testMove() {

    }

    @Test
    @Disabled
    @Order(5)
    void preconfiguredMachine() {
        then(pricingCalculator.getInstances())
                .isEqualTo("4");

        then(pricingCalculator.valueOf("computeServer.series"))
                .isEqualTo("N1");

        then(pricingCalculator.valueOf("computeServer.instance"))
                .contains("n1-standard-8")
                .contains("vCPUs: 8")
                .contains("RAM: 30");
    }


}
