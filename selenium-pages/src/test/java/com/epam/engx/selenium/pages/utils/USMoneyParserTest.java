package com.epam.engx.selenium.pages.utils;

import com.epam.engx.selenium.pages.UnitTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;

@UnitTest
class USMoneyParserTest {

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, delimiter = '|', textBlock = """
            Representation      | Amount
            USD 1,080.20        | 1080.2
            USD 1000000         | 1000000
            USD 1,000,000       | 1000000
            USD .36             | .36
            USD 0.362           | .362
            USD -10             | -10
            """)
    void parse_usd_money_representation(String representation, BigDecimal amount) {
        // given
        var parser = new USMoneyParser();

        // when
        var money = parser.apply(representation);

        then(money.getNumberStripped())
                .isEqualByComparingTo(amount);

        then(money.getCurrency().getCurrencyCode())
                .isEqualTo("USD");
    }
}