package com.epam.engx.selenium.pages.utils;

import lombok.Data;
import org.javamoney.moneta.Money;
import org.junit.jupiter.params.provider.Arguments;

import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;
import java.util.Map;

@Data
public class PricingCalculatorTestCase {
    private static final MonetaryAmountFormat US_FORMAT =
            MonetaryFormats.getAmountFormat(Locale.US);

    private String description;
    private Map<String, String> computeServer;
    private String monthlyCost;

    public Arguments arguments() {
        return Arguments.of(description, computeServer, Money.parse(monthlyCost, US_FORMAT));
    }
}