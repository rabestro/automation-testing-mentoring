package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Page;
import com.epam.engx.selenium.pages.model.EstimatedBill;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class EstimateMailPage extends Page {

    @FindBy(css = "header div.ellipsis")
    private WebElement subject;

    @FindBy(css = "#mail h2")
    private WebElement monthlyCost;

    public EstimateMailPage(WebDriver mail) {
        super(mail);
    }

    public EstimatedBill getEstimatedBill() {
        return new EstimatedBill(subject, monthlyCost);
    }
}
