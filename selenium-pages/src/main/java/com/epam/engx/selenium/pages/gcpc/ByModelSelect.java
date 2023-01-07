package com.epam.engx.selenium.pages.gcpc;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * A custom locator for Google Cloud Pricing Calculator
 * which locates "md-select" elements by the value of the "ng-model" attribute.
 */
public final class ByModelSelect extends By {
    private final By locator;

    public ByModelSelect(String model) {
        var css = "md-select[ng-model$='%s']".formatted(model);
        locator = By.cssSelector(css);
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return context.findElements(locator);
    }

    @Override
    public String toString() {
        return locator.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
