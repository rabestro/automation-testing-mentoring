package com.epam.engx.selenium.pages.gcpc.component;

import org.openqa.selenium.WebElement;

import java.util.function.Consumer;

public record CheckboxComponent(WebElement element, Consumer<WebElement> jsActionClick) implements Component {

    @Override
    public void set(String value) {
        if (isClickRequired(value)) {
            jsActionClick.accept(element);
        }
    }

    private boolean isClickRequired(String value) {
        return !Boolean.valueOf(value).equals(Boolean.valueOf(get()));
    }

    @Override
    public String get() {
        return element.getDomAttribute("aria-checked");
    }
}
