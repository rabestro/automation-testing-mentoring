package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.BaseLocalTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;


class EstimateMailPageTest extends BaseLocalTest {

    @BeforeEach
    void openMail() {
        openHtmlFile("/estimate-mail.html");
    }

    @Test
    void get_estimated_bill() {
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