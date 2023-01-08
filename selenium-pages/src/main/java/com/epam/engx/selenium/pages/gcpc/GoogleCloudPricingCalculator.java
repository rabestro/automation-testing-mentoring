package com.epam.engx.selenium.pages.gcpc;

import com.epam.engx.selenium.pages.gcpc.model.*;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

public final class GoogleCloudPricingCalculator implements AngularCalculator {
    @FindBy(css = "button[ng-disabled^='ComputeEngineForm']")
    private  WebElement addEstimateComputeEngine;

    private final NgWebDriver ngDriver;
    private final WebDriver driver;

    public GoogleCloudPricingCalculator(WebDriver driver) {
        this.driver = driver;
        var js = (JavascriptExecutor) driver;
        ngDriver = new NgWebDriver(js);
        PageFactory.initElements(driver, this);
        openPage();
    }

    private void openPage() {
        driver.get("https://cloud.google.com/products/calculator");
        ngDriver.waitForAngularRequestsToFinish();
        var mainFrame = driver.findElement(By.xpath("//iframe"));
        driver.switchTo().frame(mainFrame);
        var myFrame = driver.findElement(By.id("myFrame"));
        driver.switchTo().frame(myFrame);
    }

    @Override
    public Model model(String model) {
        var element = driver.findElement(new ByModel(model));

        return switch (element.getTagName()) {
            case "md-select" -> new Select(this, element, OptionEquals::new);
            case "input" -> new Input(this, element);
            case "md-checkbox" -> new Checkbox(this, element);
            default -> throw new UnsupportedOperationException(
                    "No implementation for " + element.getTagName());
        };
    }

    @Override
    public Map<String, String> parameters() {
        return new Parameters(driver).get();
    }

    @Override
    public Estimate estimate() {
        click(addEstimateComputeEngine);
        return new Estimate(driver);
    }

    @Override
    public void click(WebElement element) {
        ngDriver.evaluateScript(element, "arguments[0].click();");
        ngDriver.waitForAngularRequestsToFinish();
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }
}
