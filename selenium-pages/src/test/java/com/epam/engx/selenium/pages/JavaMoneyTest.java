package com.epam.engx.selenium.pages;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.util.Locale;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("Java Money and Currency API")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JavaMoneyTest {
    private static final String USD_CURRENCY_CODE = "USD";
    private static final int USD_NUMERIC_CODE = 840;
    private static final CurrencyUnit USD = Monetary.getCurrency(USD_CURRENCY_CODE);

    @Test
    void create_usd_currency_by_code() {

        var usd = Monetary
                .getCurrency(USD_CURRENCY_CODE);

        then(usd)
                .isNotNull()
                .extracting("currencyCode")
                .isEqualTo(USD_CURRENCY_CODE);

        then(usd.getDefaultFractionDigits())
                .isEqualTo(2);

        then(usd.getNumericCode())
                .isEqualTo(USD_NUMERIC_CODE);
    }

    @Test
    void get_currency_for_non_existent_code() {

        var throwable = catchThrowable(
                () -> Monetary.getCurrency("AAA")
        );

        then(throwable)
                .isInstanceOf(UnknownCurrencyException.class);
    }

    @SuppressWarnings("MagicNumber")
    @Test
    void create_monetary_amount() {

        var amount = Money.of(12, USD_CURRENCY_CODE);

        then(amount)
                .asString()
                .isEqualTo("USD 12.00");

        then(amount.getNumber().numberValue(BigDecimal.class))
                .isEqualTo(BigDecimal.valueOf(12));

        then(amount.getCurrency())
                .isEqualTo(USD);
    }

    @Test
    void parse_string_to_money() {
        // given
        var formatter = MonetaryFormats.getAmountFormat(Locale.US);

        // when
        var amount = Money.parse("USD 1,081.20", formatter);

        then(amount.getCurrency())
                .asString()
                .isEqualTo("USD");

        then(amount.getNumberStripped())
                .isEqualByComparingTo("1081.20");
    }
}
