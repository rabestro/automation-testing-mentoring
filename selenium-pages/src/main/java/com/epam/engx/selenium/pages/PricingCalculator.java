package com.epam.engx.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

// page_url = https://cloud.google.com/products/calculator
public class PricingCalculator extends AbstractPage {

    @FindBy(xpath = "//iframe")
    private WebElement mainFrame;

    @FindBy(id = "myFrame")
    private WebElement myFrame;

    @FindBy(name = "quantity")
    private WebElement numberOfInstances;

    public PricingCalculator(WebDriver driver) {
        super(driver);
        driver.switchTo().frame(mainFrame);
        driver.switchTo().frame(myFrame);
    }

    public static PricingCalculator openPage(WebDriver driver) {
        driver.get("https://cloud.google.com/products/calculator");
        return new PricingCalculator(driver);
    }

    public PricingCalculator setInstances(int instances) {
        numberOfInstances.sendKeys(String.valueOf(instances));
        return this;
    }

    public String getInstances() {
        return numberOfInstances.getAttribute(VALUE);
    }
}
