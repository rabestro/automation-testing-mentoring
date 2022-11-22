package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@DisplayName("Calculator divide two real numbers")
final class DivideTwoRealNumbersTest extends CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "divide-real-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("divide two real numbers")
    void divideDoubleNumbers(double a, double b, double expectedResult) {
        double result = calculator.div(a, b);
        Assertions.assertEquals(expectedResult, result, DELTA, "the division of two real numbers is wrong");
    }
}
