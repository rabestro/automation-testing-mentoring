package com.epam.engx.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

// page_url = https://cloud.google.com/products/calculator
public class PricingCalculator extends AbstractPage {

    @FindBy(name = "quantity")
    private WebElement numberOfInstances;

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

    public PricingCalculator select(Menu menu, String item) {
        menu.click(driver);
        var itemXpath = "//md-option/div[contains(text(),'%s')]".formatted(item);
        var element = driver.findElement(By.xpath(itemXpath));
        var action = new Actions(driver);
        action.moveToElement(element);
        element.click();
        return this;
    }

    public String valueOf(Menu menu) {
        var element = By.xpath(menu.baseXpath + "/md-select-value");
        return driver.findElement(element).getText();
    }

    public enum Menu {
        SERIES("Series"),
        SSD("Local SSD"),
        DATACENTER("Datacenter location"),
        INSTANCE("Instance type");
        final String placeholder;
        final String baseXpath;

        Menu(String placeholder) {
            this.placeholder = placeholder;
            this.baseXpath = "//md-select[@placeholder='%s']".formatted(placeholder);
        }

        void click(WebDriver driver) {
            var element = driver.findElement(By.xpath(baseXpath));
            var action = new Actions(driver);
            action.moveToElement(element);
            element.click();
        }
    }

}
