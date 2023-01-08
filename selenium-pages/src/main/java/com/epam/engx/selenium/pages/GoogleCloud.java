package com.epam.engx.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://cloud.google.com/
public final class GoogleCloud extends PageFactory {
    private final WebDriver driver;

    @FindBy(name = "q")
    private WebElement searchInput;

    private GoogleCloud(WebDriver driver) {
        this.driver = driver;
        initElements(driver, this);
    }

    public static GoogleCloud openPage(WebDriver driver) {
        driver.get("https://cloud.google.com/");
        return new GoogleCloud(driver);
    }

    public SearchResult search(String text) {
        searchInput.click();
        searchInput.sendKeys(text);
        searchInput.submit();
        return new SearchResult(driver);
    }
}
