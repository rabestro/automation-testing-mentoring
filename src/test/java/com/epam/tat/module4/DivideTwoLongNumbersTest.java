package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Calculator divide two long numbers")
final class DivideTwoLongNumbersTest extends CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "divide-long-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("divide two long numbers")
    void divideLongNumbers(long a, long b, long expectedResult) {
        long result = calculator.div(a, b);
        Assertions.assertEquals(expectedResult, result, "the division of two long numbers is wrong");
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, -1, Long.MIN_VALUE, Long.MAX_VALUE})
    @DisplayName("divide long number by zero")
    void divideLongNumberByZero(long number) {
        var exception = Assertions.assertThrows(ArithmeticException.class, () -> {
            long result = calculator.div(number, 0);
        }, "the division of long number by zero throws wrong exception");

        var expectedMessage = "divide by zero";
        assertTrue(exception.getMessage().contains(expectedMessage));

        assertThat(exception.getMessage(), containsString(expectedMessage));
    }
}
