package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class EmailGeneratorPage extends Page {
    private static final String HOME_PAGE = "https://yopmail.com/email-generator";

    @FindBy(id = "geny")
    private WebElement generatedEmail;

    @FindBy(css = "button.egenbut:last-child")
    private WebElement inboxButton;

    @FindBy(id = "accept")
    private WebElement acceptCookiesButton;

    public EmailGeneratorPage(WebDriver driver) {
        super(driver);
    }

    public String randomEmailAddress() {
        return generatedEmail.getText();
    }

    public YopInboxPage inbox() {
        inboxButton.click();
        return new YopInboxPage(driver);
    }

    @Override
    public EmailGeneratorPage to() {
        driver.get(HOME_PAGE);
        acceptCookiesButton.click();
        return this;
    }
}
