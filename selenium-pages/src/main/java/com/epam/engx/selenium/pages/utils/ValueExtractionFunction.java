package com.epam.engx.selenium.pages.utils;

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNullElse;

/**
 * Extracting a value of an item from a multiline block of text.
 * <p>
 * This function searches for the first line in a multiline
 * block of text containing the pattern: {@code item: value}.
 * After finding this line the value is extracted.
 */
public final class ValueExtractionFunction implements Function<CharSequence, String> {
    private static final Pattern VALUE_PATTERN = Pattern.compile("(?<=: )(.*)");

    /**
     * Extract a value of an item from a text block
     *
     * @param text in which to find and extract value
     * @return extracted value or an empty string if not found
     */
    @Override
    public String apply(CharSequence text) {
        return VALUE_PATTERN.matcher(requireNonNullElse(text, ""))
                .results()
                .findFirst()
                .map(MatchResult::group)
                .orElse("");
    }
}
