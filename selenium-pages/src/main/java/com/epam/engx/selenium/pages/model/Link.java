package com.epam.engx.selenium.pages.model;

import org.openqa.selenium.WebElement;

public record Link(String text, String url) {

    public Link(WebElement element) {
        this(element.getText(), element.getAttribute("href"));
    }
}
