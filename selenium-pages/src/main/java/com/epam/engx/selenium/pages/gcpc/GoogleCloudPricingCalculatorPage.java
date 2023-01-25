package com.epam.engx.selenium.pages.gcpc;

import com.epam.engx.selenium.pages.browser.Page;
import com.epam.engx.selenium.pages.gcpc.component.Component;
import com.epam.engx.selenium.pages.gcpc.component.ComponentFactory;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public final class GoogleCloudPricingCalculatorPage extends Page implements Calculator {
    private static final String URL = "https://cloud.google.com/products/calculator";
    private final ComponentFactory componentFactory;
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
        componentFactory = new ComponentFactory(driver, this::click);
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

    public Component model(String model) {
        return componentFactory.apply(model);
    }

    public Map<String, String> getParameters() {
        return new Parameters(driver).get();
    }

    public GoogleCloudPricingCalculatorPage setParameters(Map<String, String> parameters) {
        parameters.forEach((key, value) -> model(key).set(value));
        return this;
    }

    public GoogleCloudPricingCalculatorPage setParameters(String yamlString) {
        return setParameters(new Yaml().<Map<String, String>>load(yamlString));
    }

    public Estimate estimate() {
        click(addEstimateComputeEngineButton);
        return new Estimate(this);
    }

    public void click(WebElement element) {
        ngDriver.evaluateScript(element, "arguments[0].click();");
        ngDriver.waitForAngularRequestsToFinish();
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }
}
