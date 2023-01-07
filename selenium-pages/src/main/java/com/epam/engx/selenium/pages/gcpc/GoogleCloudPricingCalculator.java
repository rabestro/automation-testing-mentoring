package com.epam.engx.selenium.pages.gcpc;

import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static com.paulhammant.ngwebdriver.ByAngularModel.FindBy;

public final class GoogleCloudPricingCalculator extends LoadableComponent<GoogleCloudPricingCalculator> {
    private final WebDriver driver;
    private final NgWebDriver ngDriver;

    @FindBy(model = "listingCtrl.computeServer.quantity")
    private WebElement numberOfInstances;

    public GoogleCloudPricingCalculator(WebDriver driver) {
        this.driver = driver;
        var js = (JavascriptExecutor) driver;
        ngDriver = new NgWebDriver(js);
        PageFactory.initElements(driver, this);
    }

    public GoogleCloudPricingCalculator setInstances(int instances) {
        numberOfInstances.click();
        numberOfInstances.sendKeys(String.valueOf(instances));
        return this;
    }

    public String getInstances() {
        return numberOfInstances.getAttribute("value");
    }

    @Override
    protected void load() {
        driver.get("https://cloud.google.com/products/calculator");
        ngDriver.waitForAngularRequestsToFinish();
        var mainFrame = driver.findElement(By.xpath("//iframe"));
        driver.switchTo().frame(mainFrame);
        var myFrame = driver.findElement(By.id("myFrame"));
        driver.switchTo().frame(myFrame);
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            var isLoaded = driver.getTitle()
                    .equals("Google Cloud Pricing Calculator");

            if (isLoaded) {
                return;
            }
        } catch (Exception ignored) {
            // ignored
        }
        throw new Error("Page has not loaded");
    }
}
