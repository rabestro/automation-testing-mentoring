package com.epam.engx.selenium.pages.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public abstract class Page extends PageFactory {
    protected final WebDriver driver;

    protected Page(WebDriver driver) {
        this.driver = driver;
        initElements(driver, this);
    }

    public Page to() {
        return this;
    }
}
