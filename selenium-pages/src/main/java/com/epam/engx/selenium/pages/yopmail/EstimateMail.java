package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EstimateMail extends Page {

    @FindBy(css = "header div.ellipsis")
    private WebElement subject;

    @FindBy(css = "#mail h2")
    private WebElement monthlyCost;

    protected EstimateMail(WebDriver driver) {
        super(driver);
    }

    public String subject() {
        return subject.getText();
    }

    public String monthlyCost() {
        return monthlyCost.getText();
    }
}
