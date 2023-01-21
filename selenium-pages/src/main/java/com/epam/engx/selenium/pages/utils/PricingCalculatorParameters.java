package com.epam.engx.selenium.pages.utils;

import org.junit.jupiter.params.provider.Arguments;
import org.yaml.snakeyaml.Yaml;

import java.util.stream.Stream;

@SuppressWarnings("unused")
final class PricingCalculatorParameters {
    private static final String TEST_CASES = "/pricing-calculator-parameters.yaml";

    private PricingCalculatorParameters() {
    }

    public static Stream<Arguments> provideComputerEngineParameters() {
        var is = PricingCalculatorParameters.class.getResourceAsStream(TEST_CASES);
        var data = new Yaml().loadAs(is, PricingCalculatorTestSuit.class);
        return data.stream();
    }
}
