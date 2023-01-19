package com.epam.engx.selenium.pages.utils;

import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Extracting an item as label/value pair from a multiline block of text.
 * <p>
 * This function searches for the first line in a multiline
 * block of text containing the pattern: {@code label: value}.
 * After finding this line the label and value are extracted.
 */
public final class ItemExtractionFunction implements Function<WebElement, Map.Entry<String, String>> {
    private static final Pattern ITEM_PATTERN = Pattern.compile(
            "(?<label>.+): (?<value>.*)"
    );

    /**
     * Extract a label and a value of an item from a text block
     *
     * @param webElement containing the text in which to find and extract item
     * @return map entry containing extracted label and value
     * or a map entry with empty strings if item not found
     */
    @Override
    public Map.Entry<String, String> apply(WebElement webElement) {
        var text = Optional.ofNullable(webElement)
                .map(WebElement::getText)
                .orElse("");

        var m = ITEM_PATTERN.matcher(text);
        return m.find()
                ? Map.entry(m.group("label").strip(), m.group("value").strip())
                : Map.entry("", "");
    }
}
