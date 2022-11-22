package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@DisplayName("Calculator adds two double numbers")
public class SumTwoDoubleNumbersTest extends CalculatorTest {
    private static final double DELTA = 0.000_000_000_1;

    @ParameterizedTest
    @CsvFileSource(resources = "sum-long-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("sum two whole numbers")
    void sumLongNumbers(double a, double b, double expectedSum) {
        double result = calculator.sum(a, b);
        Assertions.assertEquals(expectedSum, result, "the sum of two whole numbers is wrong");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sum-double-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("sum two real numbers")
    void sumDoubleNumbers(double a, double b, double expectedSum) {
        double result = calculator.sum(a, b);
        Assertions.assertEquals(expectedSum, result, DELTA, "the sum of two real numbers is wrong");
    }
}
