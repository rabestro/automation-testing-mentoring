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
@DisplayName("Calculator calculates sine")
final class SineTest extends CalculatorTest {

    private static Stream<Arguments> sineSampleData() {
        return Stream.of(
                arguments(0, 0),
                arguments(PI / 6, 1./2),
                arguments(PI / 4, sqrt(2) / 2),
                arguments(PI / 3, sqrt(3) / 2),
                arguments(PI / 2, 1),
                arguments(PI, 0)
        );
    }

    @ParameterizedTest(name = "sine {0, number, 0.00} is {1, number, 0.00}")
    @MethodSource("sineSampleData")
    @DisplayName("calculates sine for a real number")
    void calculatesSine(double radian, double expected) {
        var actual = calculator.sin(radian);
        Assertions.assertEquals(expected, actual, DELTA);
    }
}
