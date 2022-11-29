package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Calculator adds two double numbers")
final class TangentTest extends CalculatorTest {

    private static Stream<Arguments> sampleData() {
        return Stream.of(
                arguments(0, 0),
                arguments(PI / 6, sqrt(3) / 3),
                arguments(PI / 4, 1),
                arguments(PI / 3, sqrt(3)),
                arguments(PI, 0)
        );
    }

    @ParameterizedTest(name = "{0} is a positive number")
    @MethodSource("sampleData")
    @DisplayName("check for a number that is positive")
    void checkLongPositiveNumber(double radian, double expected) {
        var actual = calculator.tg(radian);
        Assertions.assertEquals(expected, actual, DELTA);
    }
}
