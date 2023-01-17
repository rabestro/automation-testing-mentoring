package com.epam.engx.selenium.pages.model;

import org.javamoney.moneta.Money;
import org.openqa.selenium.WebElement;

import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public record EstimatedBill(String subject, Money monthlyCost) {
    private static final String LABEL_PATTERN = ".*:";
    private static final MonetaryAmountFormat US_FORMAT =
            MonetaryFormats.getAmountFormat(Locale.US);

    public EstimatedBill(WebElement subject, WebElement monthlyCost) {
        this(subject.getText(), extractMoney(monthlyCost));
    }

    private static Money extractMoney(WebElement monthlyCost) {
        var amount = monthlyCost.getText().replaceFirst(LABEL_PATTERN, "");
        return Money.parse(amount, US_FORMAT);
    }
}
