package com.epam.engx.selenium.pages.gcpc.model;

import com.epam.engx.selenium.pages.gcpc.Calculator;
import org.openqa.selenium.WebElement;

public record Input(Calculator calc, WebElement element) implements Model {

    @Override
    public Calculator set(String value) {
        element.click();
        element.sendKeys(value);
        return calc;
    }

    @Override
    public String get() {
        return element.getAttribute("value");
    }
}
