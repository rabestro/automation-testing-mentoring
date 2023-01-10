package com.epam.engx.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

// page_url = https://cloud.google.com/s/results?q=Google%20Cloud%20Platform%20Pricing%20Calculator
public class SearchResult {
    private final WebDriver driver;

    @FindBy(css = "a > b")
    private List<WebElement> results;

    @FindBy(css = "a:first-child > b")
    private WebElement firstResultLink;

    public SearchResult(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public List<Link> links() {
        return results.stream().map(Link::new).filter(Link::hasText).toList();
    }

    public <T> T goFirst(Function<? super WebDriver, T> pageFabric) {
        firstResultLink.click();
        return pageFabric.apply(driver);
    }
}