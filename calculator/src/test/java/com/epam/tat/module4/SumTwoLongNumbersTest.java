package com.epam.tat.module4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Calculator adds two long numbers")
final class SumTwoLongNumbersTest extends CalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "sum-long-numbers.csv", useHeadersInDisplayName = true)
    @DisplayName("sum two long numbers")
    void sumLongNumbers(long a, long b, long expectedSum) {
        long result = calculator.sum(a, b);
        Assertions.assertEquals(expectedSum, result, "the sum of two long numbers is wrong");
    }
}
