package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@DisplayName("Calculator subtracts two double numbers")
final class SubtractRealNumbersTest extends CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "sub-long-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("subtract two whole numbers")
    void subtractWholeNumbers(double a, double b, double expectedResult) {
        double result = calculator.sub(a, b);
        Assertions.assertEquals(expectedResult, result, "the subtraction of two whole numbers is wrong");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sub-real-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("subtract two real numbers")
    void subtractDoubleNumbers(double a, double b, double expectedResult) {
        double result = calculator.sub(a, b);
        Assertions.assertEquals(expectedResult, result, DELTA, "the subtraction of two real numbers is wrong");
    }
}
