package com.epam.engx.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

class AbstractPage {
    static final String VALUE = "value";
    final WebDriver driver;

    AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
