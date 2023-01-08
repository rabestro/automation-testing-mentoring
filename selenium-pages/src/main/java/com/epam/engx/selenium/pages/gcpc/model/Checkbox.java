package com.epam.engx.selenium.pages.gcpc.model;

import com.epam.engx.selenium.pages.gcpc.Calculator;
import org.openqa.selenium.WebElement;

public record Checkbox(AngularCalculator calculator, WebElement element) implements Model {

    @Override
    public Calculator set(String value) {
        if (isClickRequired(value)) {
            calculator.click(element);
        }
        return calculator;
    }

    private boolean isClickRequired(String value) {
        return !Boolean.valueOf(value).equals(Boolean.valueOf(get()));
    }

    @Override
    public String get() {
        return  element.getDomAttribute("aria-checked");
    }
}
