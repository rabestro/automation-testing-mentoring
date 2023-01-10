package com.epam.engx.selenium.pages;

import org.openqa.selenium.WebElement;

public record Link(String text, String url) {

    public Link(WebElement element) {
        this(element.getText(), element.getAttribute("href"));
    }

    public boolean hasText() {
        return !text.isBlank();
    }
}
