package com.epam.engx.selenium.pages.utils;

import org.javamoney.moneta.Money;

import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;
import java.util.function.Function;

public final class USMoneyParser implements Function<CharSequence, Money> {
    private static final MonetaryAmountFormat US_FORMAT = MonetaryFormats.getAmountFormat(Locale.US);

    @Override
    public Money apply(CharSequence charSequence) {
        return Money.parse(charSequence, US_FORMAT);
    }
}
