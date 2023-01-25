package com.epam.engx.selenium.pages.utils;

import com.epam.engx.selenium.pages.UnitTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.openqa.selenium.WebElement;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@UnitTest
class ItemExtractionFunctionTest {
    private final ItemExtractionFunction extractionFunction = new ItemExtractionFunction();

    @Mock
    private WebElement webElement;

    @ParameterizedTest(name = "{arguments}")
    @CsvSource(delimiterString = "->", useHeadersInDisplayName = true, textBlock = """
            text                        -> label                -> value
            Region: Frankfurt           -> Region               -> Frankfurt
            Provisioning model: Regular -> Provisioning model   -> Regular
                                        -> ''                   -> ''
            ''                          -> ''                   -> ''
            'Local SSD: 2x375 GiB
            USD 288
            '                           -> Local SSD            -> 2x375 GiB
            'Discount applied
            Instance type: n1-standard-8' -> Instance type      -> n1-standard-8
            2,920 total hours per month -> ''                   -> ''
            """)
    void extract_an_item_from_the_web_element(String text, String label, String value) {
        given(webElement.getText())
                .willReturn(text);

        // when
        var entry = extractionFunction.apply(webElement);

        then(entry)
                .isNotNull()
                .isEqualTo(Map.entry(label, value));
    }
}