package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@DisplayName("Calculator multiply two real numbers")
public class MultiplyTwoRealNumbersTest extends CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "multiply-long-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("multiply two whole numbers")
    void multiplyWholeNumbers(double a, double b, double expectedResult) {
        double result = calculator.mult(a, b);
        Assertions.assertEquals(expectedResult, result, DELTA, "the multiplication of two whole numbers is wrong");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "multiply-real-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("multiply two real numbers")
    void multiplyDoubleNumbers(double a, double b, double expectedResult) {
        double result = calculator.mult(a, b);
        Assertions.assertEquals(expectedResult, result, DELTA, "the multiplication of two real numbers is wrong");
    }
}
