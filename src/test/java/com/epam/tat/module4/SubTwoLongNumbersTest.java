package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@DisplayName("Calculator subtract two long numbers")
public class SubTwoLongNumbersTest extends CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "sum-long-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("sum two long numbers")
    void sumLongNumbers(long a, long b, long expectedSum) {
        long result = calculator.sum(a, b);
        Assertions.assertEquals(expectedSum, result, "the sum of two long numbers is wrong");
    }
}
