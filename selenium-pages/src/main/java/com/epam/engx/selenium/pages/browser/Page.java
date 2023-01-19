package com.epam.engx.selenium.pages.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.Objects;


public abstract class Page extends PageFactory {
    protected final WebDriver driver;
    protected SearchContext searchContext;

    protected Page(WebDriver driver) {
        this.driver = driver;
        initElements(driver, this);
    }

    public Page to() {
        return this;
    }

    public WebDriver driver() {
        return driver;
    }

    protected WebElement $(String selector) {
        return Objects
                .requireNonNullElse(searchContext, driver)
                .findElement(By.cssSelector(selector));
    }

    protected void setSearchContext(SearchContext context) {
        searchContext = context;
    }
}
