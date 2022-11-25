package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Numbers can be positive, negative or zero.
 * Zero is neither positive nor negative.
 */
@DisplayName("Calculator checks if a number is positive")
final class IsPositiveNumberTest extends CalculatorTest {

    @ParameterizedTest(name = "{0} is a positive number")
    @ValueSource(longs = {1, 8, 4_209, Short.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE})
    @DisplayName("check for a number that is positive")
    void checkLongPositiveNumber(long number) {
        var isPositive = calculator.isPositive(number);
        Assertions.assertTrue(isPositive, () -> "the number %d is positive".formatted(number));
    }

    @ParameterizedTest(name = "{0} is not a positive number")
    @ValueSource(longs = {0, -5, -327, Short.MIN_VALUE, Integer.MIN_VALUE, Long.MIN_VALUE})
    @DisplayName("check for a number that is not positive")
    void checkLongNotPositiveNumber(long number) {
        var isPositive = calculator.isPositive(number);
        Assertions.assertFalse(isPositive, () -> "the number %d is not a positive number".formatted(number));
    }
}
