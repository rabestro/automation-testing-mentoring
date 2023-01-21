package com.epam.engx.selenium.pages.test;

import lombok.Data;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

@Data
public final class PricingCalculatorTestSuit {
    private List<PricingCalculatorTestCase> tests;

    public Stream<Arguments> stream() {
        return tests.stream().map(PricingCalculatorTestCase::arguments);
    }
}
