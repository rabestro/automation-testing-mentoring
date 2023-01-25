package com.epam.engx.selenium.pages.gcpc.component;

import org.openqa.selenium.WebElement;

public record InputComponent(WebElement element) implements Component {

    @Override
    public void set(String value) {
        element.click();
        element.sendKeys(value);
    }

    @Override
    public String get() {
        return element.getAttribute("value");
    }
}
