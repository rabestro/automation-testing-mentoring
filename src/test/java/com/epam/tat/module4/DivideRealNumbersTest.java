package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Calculator divide two real numbers")
final class DivideRealNumbersTest extends CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "divide-real-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("divide two real numbers")
    void divideDoubleNumbers(double a, double b, double expectedResult) {
        double result = calculator.div(a, b);
        Assertions.assertEquals(expectedResult, result, DELTA, "the division of two real numbers is wrong");
    }
}
