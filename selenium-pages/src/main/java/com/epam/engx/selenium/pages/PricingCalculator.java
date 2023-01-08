package com.epam.engx.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

// page_url = https://cloud.google.com/products/calculator
public class PricingCalculator extends AbstractPage {
    private static final String CSS_MENU = "md-select[ng-model$='%s']";
    private static final String CSS_ITEM = "md-option[value*='%s']";
    private static final String CSS_VALUE = "[ng-model$='%s'] > md-select-value div.md-text";
    private static final String CSS_CHECKBOX = "md-checkbox[ng-model$='%s']";

    @FindBy(name = "quantity")
    private WebElement numberOfInstances;

    @FindBy(css = "md-checkbox[aria-label='Add GPUs']")
    private WebElement checkboxAddGpu;

    @FindBy(css = "md-select[ng-model$='gpuType']")
    private WebElement gpuType;

    public PricingCalculator(WebDriver driver) {
        super(driver);
        switchToCalculatorArea();
    }

    public static PricingCalculator openPage(WebDriver driver) {
        driver.get("https://cloud.google.com/products/calculator");
        return new PricingCalculator(driver);
    }

    private void switchToCalculatorArea() {
        var mainFrame = driver.findElement(By.xpath("//iframe"));
        driver.switchTo().frame(mainFrame);
        var myFrame = driver.findElement(By.id("myFrame"));
        driver.switchTo().frame(myFrame);
    }

    public String getInstances() {
        return numberOfInstances.getAttribute(VALUE);
    }

    public PricingCalculator setInstances(int instances) {
        numberOfInstances.sendKeys(String.valueOf(instances));
        return this;
    }

    public PricingCalculator checkbox(String checkbox) {
        click(CSS_CHECKBOX.formatted(checkbox));
        return this;
    }

    public PricingCalculator dropdownMenu(String menuName, String itemName) {
        click(CSS_MENU.formatted(menuName));
        click(CSS_ITEM.formatted(itemName));
        return this;
    }

    private void click(String cssSelector) {
        var element = driver.findElement(By.cssSelector(cssSelector));
//        moveTo(item);

        new Actions(driver).click(element).perform();
//        item.click();
    }

    public void moveTo(WebElement element) {
//        var js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].scrollIntoView();", item);
        new Actions(driver).click(element);
    }

    public String valueOf(String menuName) {
        var cssValue = CSS_VALUE.formatted(menuName);
        return driver.findElement(By.cssSelector(cssValue)).getText();
    }

}
