package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EmailGenerator extends Page {
    private static final String URL = "https://yopmail.com/email-generator";

    @FindBy(id = "geny")
    private WebElement generatedEmail;

    public EmailGenerator(WebDriver driver) {
        super(driver);
    }

    public String email() {
        return generatedEmail.getText();
    }

    @Override
    public EmailGenerator to() {
        driver.get(URL);
        return this;
    }
}
