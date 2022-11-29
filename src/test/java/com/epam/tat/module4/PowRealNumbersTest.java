package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Calculator powers real numbers")
final class PowRealNumbersTest extends CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "pow-real-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("power real numbers")
    void divideDoubleNumbers(double a, double b, double expectedResult) {
        double result = calculator.pow(a, b);
        Assertions.assertEquals(expectedResult, result, DELTA, "the power of real numbers is wrong");
    }
}

