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
}