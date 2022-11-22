package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MultTwoLongNumbersTest extends CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "mult-long-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("multiply two long numbers")
    void multLongNumbers(long a, long b, long expectedResult) {
        long result = calculator.mult(a, b);
        Assertions.assertEquals(expectedResult, result, "the multiplication of two long numbers is wrong");
    }
}
