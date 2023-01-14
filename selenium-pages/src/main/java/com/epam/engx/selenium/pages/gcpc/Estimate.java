package com.epam.engx.selenium.pages.gcpc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Estimate extends PageFactory {
    private final GoogleCloudPricingCalculator calculator;

    @FindBy(css = "div.cpc-cart-total > h2.md-title > b.ng-binding")
    private WebElement totalEstimatedCost;

    @FindBy(css = "button.md-button.md-mini[title='Email Estimate']")
    private WebElement emailEstimateButton;

    @FindBy(css = "button.md-raised.md-primary[aria-label='Send Email']")
    private WebElement sendEmailButton;

    public Estimate(GoogleCloudPricingCalculator calculator) {
        this.calculator = calculator;
        initElements(calculator.driver(), this);
    }

    public String totalEstimatedCost() {
        return totalEstimatedCost.getText();
    }

    public String getItem(String label) {
        var path = "//*[@id='compute']/md-list/md-list-item/div[contains(.,'%s')]".formatted(label);
        return calculator.findElement(By.xpath(path)).getText();
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

    public void sendTo(String email) {
        emailEstimateButton.click();
        var emailInput = calculator.findElement(new ByModel("emailQuote.user.email"));
        calculator.click(emailInput);
        emailInput.sendKeys(email);
        calculator.click(sendEmailButton);
    }

}
