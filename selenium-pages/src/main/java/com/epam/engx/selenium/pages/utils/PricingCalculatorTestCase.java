package com.epam.engx.selenium.pages.utils;

import lombok.Data;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Map;

@Data
public final class PricingCalculatorTestCase {
    private final USMoneyParser parser = new USMoneyParser();
    private String description;
    private Map<String, String> computeServer;
    private String monthlyCost;

    public Arguments arguments() {
        return Arguments.of(description, computeServer, parser.apply(monthlyCost));
    }
}