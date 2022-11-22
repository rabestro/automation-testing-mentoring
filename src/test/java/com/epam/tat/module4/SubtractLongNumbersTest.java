package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@DisplayName("Calculator subtract two long numbers")
final class SubtractLongNumbersTest extends CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "sub-long-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("subtract two long numbers")
    void subtractLongNumbers(long a, long b, long expectedSum) {
        long result = calculator.sub(a, b);
        Assertions.assertEquals(expectedSum, result, "the subtraction of two long numbers is wrong");
    }
}
