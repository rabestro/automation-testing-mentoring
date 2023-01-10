package com.epam.engx.selenium.pages.gcpc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Estimate extends PageFactory {
    private final WebDriver driver;

    @FindBy(css = "div.cpc-cart-total > h2.md-title > b.ng-binding")
    private WebElement totalEstimatedCost;

    public Estimate(WebDriver driver) {
        this.driver = driver;
        initElements(driver, this);
    }

    public String totalEstimatedCost() {
        return totalEstimatedCost.getText();
    }

    public String getItem(String label) {
        var path = "//*[@id='compute']/md-list/md-list-item/div[contains(.,'%s')]".formatted(label);
        return driver.findElement(By.xpath(path)).getText();
    }

    public Map<String, String> getItems() {
        return Stream.of("Region", "Commitment term", "Provisioning model",
                        "Instance type", "Operating System", "Local SSD")
                .collect(Collectors.toUnmodifiableMap(Function.identity(), this::getValue));
    }

    private String getValue(String text) {
        return getItem(text).lines().findFirst().orElse("")
                .replaceFirst("[^:]+?: ", "");
    }
}
