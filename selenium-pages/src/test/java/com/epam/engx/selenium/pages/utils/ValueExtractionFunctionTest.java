package com.epam.engx.selenium.pages.utils;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.function.Function;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ValueExtractionFunctionTest {
    private final Function<CharSequence, String> extractionFunction = new ValueExtractionFunction();

    @ParameterizedTest()
    @CsvSource(delimiter = '|', textBlock = """
            Region: Frankfurt                                   | Frankfurt
            Provisioning model: Regular                         | Regular
                                                                | ''
            ''                                                  | ''
            'Local SSD: 2x375 GiB
            288'                                                | 2x375 GiB
            'Discount applied
            Instance type: n1-standard-8'                       | n1-standard-8
            2,920 total hours per month                         | ''
            """)
    void extract_a_value_from_the_item_description(String text, String expectedValue) {
        // when
        var value = extractionFunction.apply(text);

        then(value)
                .isEqualTo(expectedValue);
    }
}
