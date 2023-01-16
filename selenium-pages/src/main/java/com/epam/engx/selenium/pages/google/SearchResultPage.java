package com.epam.engx.selenium.pages.google;

import com.epam.engx.selenium.pages.browser.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public final class SearchResultPage extends Page {
    @FindBy(css = "a > b")
    private List<WebElement> results;

    @FindBy(css = "a:first-child > b")
    private WebElement firstResultLink;

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public List<Link> links() {
        return results.stream()
                .map(Link::new)
                .filter(Link::hasText)
                .toList();
    }

    public void clickFirstLink() {
        firstResultLink.click();
    }
}
