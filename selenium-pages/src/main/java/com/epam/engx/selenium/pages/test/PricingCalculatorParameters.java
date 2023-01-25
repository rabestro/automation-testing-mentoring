package com.epam.engx.selenium.pages.test;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.yaml.snakeyaml.Yaml;

import java.util.stream.Stream;

@SuppressWarnings("unused")
final class PricingCalculatorParameters implements ArgumentsProvider {
    private static final String TEST_CASES = "/pricing-calculator-parameters.yaml";

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        var is = PricingCalculatorParameters.class.getResourceAsStream(TEST_CASES);
        var data = new Yaml().loadAs(is, PricingCalculatorTestSuit.class);
        return data.stream();
    }
}
