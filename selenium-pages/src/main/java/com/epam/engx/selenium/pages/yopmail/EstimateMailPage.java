package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Page;
import com.epam.engx.selenium.pages.model.EstimatedBill;
import com.epam.engx.selenium.pages.utils.ItemExtractionFunction;
import org.javamoney.moneta.Money;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public final class EstimateMailPage extends Page {
    private static final MonetaryAmountFormat US_FORMAT = MonetaryFormats.getAmountFormat(Locale.US);
    private final ItemExtractionFunction extractionFunction;

    @FindBy(css = "header div.ellipsis")
    private WebElement subject;

    @FindBy(css = "#mail h2")
    private WebElement monthlyCost;

    public EstimateMailPage(WebDriver driver) {
        this(driver, new ItemExtractionFunction());
    }

    public EstimateMailPage(WebDriver mail, ItemExtractionFunction extractionFunction) {
        super(mail);
        this.extractionFunction = extractionFunction;
    }

    public EstimatedBill getEstimatedBill() {
        var item = extractionFunction.apply(monthlyCost);
        var amount = Money.parse(item.getValue(), US_FORMAT);
        return new EstimatedBill(subject.getText(), amount);
    }
}
