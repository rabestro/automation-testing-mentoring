package com.epam.engx.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

// page_url = https://cloud.google.com/products/calculator
public class PricingCalculator extends AbstractPage {

    @FindBy(name = "quantity")
    private WebElement numberOfInstances;

    @FindBy(id = "select_115")
    private WebElement series;

    @FindBy(id = "select_option_201")
    private WebElement seriesN1;

    public PricingCalculator(WebDriver driver) {
        super(driver);
        switchToCalculatorArea();
    }

    private void switchToCalculatorArea() {
        var mainFrame = driver.findElement(By.xpath("//iframe"));
        driver.switchTo().frame(mainFrame);
        var myFrame = driver.findElement(By.id("myFrame"));
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

    public PricingCalculator setSeriesN1() {
        series.click();
        seriesN1.click();
        return this;
    }

    public String getSeries() {
        return series.getText();
    }
}
