package com.epam.engx.selenium.pages.google;

import com.epam.engx.selenium.pages.browser.Page;
import com.epam.engx.selenium.pages.browser.PageConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class GoogleCloud extends Page {
    private static final String URL = "https://cloud.google.com/";

    @FindBy(name = "q")
    private WebElement searchInput;

    public GoogleCloud(WebDriver driver) {
        super(driver);
    }

    public PageConstructor<SearchResult> search(String term) {
        searchInput.click();
        searchInput.sendKeys(term);
        searchInput.submit();
        return SearchResult::new;
    }

    @Override
    public GoogleCloud to() {
        driver.get(URL);
        return this;
    }
}