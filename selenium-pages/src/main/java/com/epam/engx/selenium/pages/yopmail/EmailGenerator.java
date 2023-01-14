package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class EmailGenerator extends Page {
    private static final String URL = "https://yopmail.com/email-generator";

    @FindBy(id = "geny")
    private WebElement generatedEmail;

    @FindBy(css = "button.egenbut:last-child")
    private WebElement inboxButton;

    @FindBy(id = "accept")
    private WebElement acceptCookiesButton;

    public EmailGenerator(WebDriver driver) {
        super(driver);
    }

    public String randomEmailAddress() {
        return generatedEmail.getText();
    }

    public YopInbox inbox() {
        inboxButton.click();
        return new YopInbox(driver);
    }

    @Override
    public EmailGenerator to() {
        driver.get(URL);
        acceptCookiesButton.click();
        return this;
    }
}
