package com.epam.engx.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.function.Function;

// page_url = https://cloud.google.com/s/results?q=Google%20Cloud%20Platform%20Pricing%20Calculator
public class SearchResult extends AbstractPage {

    @FindBy(xpath = "//a[b]")
    private List<WebElement> results;

    @FindBy(xpath = "//a[b][1]")
    private WebElement firstResult;

    public SearchResult(WebDriver driver) {
        super(driver);
    }

    public List<Link> links() {
        return results.stream().map(Link::new).filter(Link::hasText).toList();
    }

    public <T extends AbstractPage> T goFirst(Function<? super WebDriver, T> pageFabric) {
        firstResult.click();
        return pageFabric.apply(driver);
    }
}