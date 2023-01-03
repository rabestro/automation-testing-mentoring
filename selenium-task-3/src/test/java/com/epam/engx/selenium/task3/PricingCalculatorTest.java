package com.epam.engx.selenium.task3;

import com.epam.engx.selenium.pages.PricingCalculator;
import com.epam.engx.selenium.pages.WebDriverFabric;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static com.epam.engx.selenium.pages.PricingCalculator.Menu.*;
import static org.assertj.core.api.BDDAssertions.then;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class PricingCalculatorTest {
    private static final String N1_STANDARD_8 = "n1-standard-8";
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
                .select(SERIES, "N1")
                .select(INSTANCE, N1_STANDARD_8);
    }

    @AfterAll
    static void tearDown() throws InterruptedException {
//        Thread.sleep(5000);
        driver.quit();
    }

    @Test
    @Order(5)
    void preconfiguredMachine() {
        then(pricingCalculator.getInstances())
                .isEqualTo("4");

        then(pricingCalculator.valueOf(SERIES))
                .isEqualTo("N1");

        then(pricingCalculator.valueOf(INSTANCE))
                .contains(N1_STANDARD_8)
                .contains("vCPUs: 8")
                .contains("RAM: 30");
    }

    @Test
    @Order(10)
    @DisplayName("set Local SSD to 2x375 Gb")
    void setLocalSSD() {
        // when
        pricingCalculator.select(SSD, SSD_TYPE);

        then(pricingCalculator.valueOf(SSD))
                .contains(SSD_TYPE);
    }

    @Test
    @Order(20)
    @DisplayName("set datacenter location: Frankfurt (europe-west3)")
    void setDatacenter() {
        // when
        pricingCalculator.select(DATACENTER, FRANKFURT);

        then(pricingCalculator.valueOf(DATACENTER))
                .contains(FRANKFURT)
                .contains("europe-west3");
    }
}
