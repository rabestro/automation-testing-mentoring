package calculator

import com.epam.tat.module4.Calculator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource

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
        def actualSum = calculator.sum a, b

        Assertions.assertEquals expectedSum, actualSum
    }

    @ParameterizedTest
    @CsvFileSource(resources = "sum-long-incorrect.csv")
    void 'returns the wrong result on overflow'(long a, long b) {
        def expectedSum = a + b as BigInteger

        def actualSum = calculator.sum a, b

        Assertions.assertNotEquals expectedSum, actualSum
    }

}
