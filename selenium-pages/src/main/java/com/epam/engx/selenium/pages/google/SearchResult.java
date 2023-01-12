package com.epam.engx.selenium.pages.google;

import com.epam.engx.selenium.pages.browser.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

// page_url = https://cloud.google.com/s/results?q=Google%20Cloud%20Platform%20Pricing%20Calculator
public class SearchResult extends Page {
    @FindBy(css = "a > b")
    private List<WebElement> results;

    @FindBy(css = "a:first-child > b")
    private WebElement firstResultLink;

    public SearchResult(WebDriver driver) {
        super(driver);
    }

    public List<Link> links() {
        return results.stream().map(Link::new).filter(Link::hasText).toList();
    }

    public <T> T goFirst(Function<? super WebDriver, T> pageFabric) {
        firstResultLink.click();
        return pageFabric.apply(driver);
    }

    @Override
    public SearchResult to() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return this;
    }
}