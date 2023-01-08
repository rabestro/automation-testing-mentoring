package com.epam.engx.selenium.task3;

import com.epam.engx.selenium.pages.PricingCalculator;
import com.epam.engx.selenium.pages.WebDriverFabric;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

final class MoveToElementTest {
    private WebDriver driver;

    // Subject under test
    private PricingCalculator pricingCalculator;

    private static Stream<Arguments> elements() {
        return Stream.of(
                arguments("Add GPUs", By.cssSelector("[ng-model$='computeServer.addGPUs']")),
                arguments("Number of instances", By.cssSelector("[ng-model$='computeServer.quantity']")),
                arguments("Local SSD", By.cssSelector("[ng-model$='computeServer.ssd']"))
        );
    }

    @BeforeEach
    void setUp() {
        driver = new WebDriverFabric().incognito();

        pricingCalculator = PricingCalculator
                .openPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @MethodSource("elements")
    @ParameterizedTest(name = "click on «{0}»")
    @DisplayName("click on the item")
    void clickOnElementOnly(String name, By selector) {
        // given
        var element = driver.findElement(selector);

        // when
        element.click();
    }

//    @Disabled
    @MethodSource("elements")
    @ParameterizedTest(name = "move to «{0}» {1}")
    @DisplayName("move to the item")
    void testTwo(String name, By selector) {
        var element = driver.findElement(selector);

        new Actions(driver)
                .moveToElement(element)
                .perform();
    }

}
