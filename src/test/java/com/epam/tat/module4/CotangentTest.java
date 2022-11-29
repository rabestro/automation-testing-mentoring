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
@DisplayName("Calculator calculates cotangent")
final class CotangentTest extends CalculatorTest {
    private static Stream<Arguments> cotangentSampleData() {
        return Stream.of(
                arguments(PI / 6, sqrt(3)),
                arguments(PI / 4, 1),
                arguments(PI / 3, sqrt(3) / 3),
                arguments(PI / 2, 0)
        );
    }

    @ParameterizedTest(name = "cotangent {0, number, 0.00} is {1, number, 0.00}")
    @MethodSource("cotangentSampleData")
    @DisplayName("calculates cotangent for a radian")
    void calculatesCotangent(double radian, double expected) {
        var actual = calculator.ctg(radian);
        Assertions.assertEquals(expected, actual, DELTA);
    }
}
