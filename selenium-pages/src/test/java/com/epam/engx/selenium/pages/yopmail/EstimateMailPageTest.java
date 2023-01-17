package com.epam.engx.selenium.pages.yopmail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Objects;

import static org.assertj.core.api.BDDAssertions.then;


class EstimateMailPageTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
        var url = this.getClass().getResource("/estimateMail.html");
        driver.get("file://" + Objects.requireNonNull(url).getPath());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void getEstimatedBill() {
        // given
        var mail = new EstimateMailPage(driver);

        // when
        var estimatedBill = mail.getEstimatedBill();

        then(estimatedBill.subject())
                .isEqualTo("Google Cloud Price Estimate");

        then(estimatedBill.monthlyCost().getCurrency())
                .asString().isEqualTo("USD");

        then(estimatedBill.monthlyCost().getNumberStripped())
                .isEqualByComparingTo("1081.20");
    }
}