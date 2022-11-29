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
@DisplayName("Calculator calculates cosine")
final class CosineTest extends CalculatorTest {

    private static Stream<Arguments> cosineSampleData() {
        return Stream.of(
                arguments(0, 1),
                arguments(PI / 6, sqrt(3) / 2),
                arguments(PI / 4, sqrt(2) / 2),
                arguments(PI / 3, 1. / 2),
                arguments(PI / 2, 0),
                arguments(PI, 1)
        );
    }

    @ParameterizedTest(name = "cosine {0, number, 0.00} is {1, number, 0.00}")
    @MethodSource("cosineSampleData")
    @DisplayName("calculates cosine for a real number")
    void checkLongPositiveNumber(double radian, double expected) {
        var actual = calculator.cos(radian);
        Assertions.assertEquals(expected, actual, DELTA);
    }
}
