package calculator

import com.epam.tat.module4.Calculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("Calculator adds two long numbers")
final class SumTwoLongNumbersSpec {

    private Calculator calculator

    @BeforeEach
    void initTest() {
        calculator = new Calculator()
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sum-long-correct.csv", useHeadersInDisplayName = true)
    void 'returns the correct result if there is no overflow'(long a, long b, long expectedSum) {
        def actualSum = calculator.sum(a, b)

        Assertions.assertEquals expectedSum, actualSum
    }

    @ParameterizedTest
    @ValueSource(longs = [1L, 99L, 1_983_322L, 8_923_021_768L, Long.MAX_VALUE])
    void 'returns an incorrect result if the sum of the numbers exceeds the maximum Long'(long a) {
        def sum = Long.MAX_VALUE + 1
        def b = (sum - a) as long
        def actualSum = calculator.sum(a, b) as BigInteger

        Assertions.assertNotEquals sum, actualSum
    }
}
