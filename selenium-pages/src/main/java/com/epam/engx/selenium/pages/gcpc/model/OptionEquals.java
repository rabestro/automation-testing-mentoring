package com.epam.engx.selenium.pages.gcpc.model;

import org.openqa.selenium.WebElement;

import java.util.function.Predicate;

/**
 * This predicate is used to find a menu option by text
 * <p>
 * The algorithm checks whether menu option starts with
 * the specified text, case-insensitive.
 *
 * @param text we use to find the desired menu option
 */
public record OptionEquals(String text) implements Predicate<WebElement> {

    @Override
    public boolean test(WebElement element) {
        return element
                .getAttribute("textContent")
                .strip()
                .toLowerCase()
                .startsWith(text().toLowerCase());
    }
}
