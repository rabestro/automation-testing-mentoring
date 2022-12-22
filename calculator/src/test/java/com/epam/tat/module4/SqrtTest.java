package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Calculator calculates sqrt")
final class SqrtTest extends CalculatorTest {

    private static Stream<Arguments> sqrtSampleData() {
        return Stream.of(
                arguments(NaN, NaN),
                arguments(Math.nextDown(0), NaN),
                arguments(-4, NaN),
                arguments(+0.0, +0.0),
                arguments(-0.0, -0.0),
                arguments(1, 1),
                arguments(4, 2),
                arguments(POSITIVE_INFINITY, POSITIVE_INFINITY),
                arguments(3, sqrt(3))
        );
    }

    @ParameterizedTest(name = "sqrt {0, number, 0.00} is {1, number, 0.00}")
    @MethodSource("sqrtSampleData")
    @DisplayName("calculates sqrt for a number")
    void calculatesSqrt(double number, double expected) {
        var actual = calculator.sqrt(number);
        Assertions.assertEquals(expected, actual, DELTA);
    }
}
