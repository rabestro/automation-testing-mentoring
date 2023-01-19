package com.epam.engx.selenium.pages.gcpc;

import com.epam.engx.selenium.pages.browser.Page;
import com.epam.engx.selenium.pages.gcpc.model.*;
import com.epam.engx.selenium.pages.utils.ByModel;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public final class GoogleCloudPricingCalculatorPage extends Page implements AngularCalculator {
    private static final String URL = "https://cloud.google.com/products/calculator";
    private final NgWebDriver ngDriver;

    @FindBy(css = "button[ng-disabled^='ComputeEngineForm']")
    private WebElement addEstimateComputeEngineButton;

    @FindBy(xpath = "//iframe")
    private WebElement accountFrame;

    @FindBy(id = "myFrame")
    private WebElement myFrame;

    public GoogleCloudPricingCalculatorPage(WebDriver driver) {
        super(driver);
        var js = (JavascriptExecutor) driver;
        ngDriver = new NgWebDriver(js);
    }

    @Override
    public GoogleCloudPricingCalculatorPage to() {
        if (!driver.getCurrentUrl().startsWith(URL)) {
            driver.get(URL);
        }
        switchToCalculatorFrame();
        return this;
    }

    public void switchToCalculatorFrame() {
        ngDriver.waitForAngularRequestsToFinish();
        driver.switchTo().frame(accountFrame);
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

    public void setParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> model(key).set(value));
    }

    @Override
    public Map<String, String> getParameters() {
        return new Parameters(driver).get();
    }

    @Override
    public Estimate estimate() {
        click(addEstimateComputeEngineButton);
        return new Estimate(this);
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
