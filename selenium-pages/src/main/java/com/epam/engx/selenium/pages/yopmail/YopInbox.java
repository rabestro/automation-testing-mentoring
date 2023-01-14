package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("SpellCheckingInspection")
public final class YopInbox extends Page {
    private static final String URL = "https://yopmail.com/wm";
    private static final int REFRESH_ATTEMPTS = 10;

    @FindBy(css = "div.wmtop > div.ycptalias > div.bname")
    private WebElement emailAddress;

    @FindBy(id = "nbmail")
    private WebElement mailCount;

    @FindBy(id = "refresh")
    private WebElement refreshButton;

    @FindBy(id = "ifmail")
    private WebElement mailFraim;

    public YopInbox(WebDriver driver) {
        super(driver);
    }

    public String emailAddress() {
        return emailAddress.getText();
    }

    public String mailCount() {
        return mailCount.getText();
    }

    public EstimateMail email() {
        driver.switchTo().frame(mailFraim);
        return new EstimateMail(driver);
    }

    @Override
    public YopInbox to() {
        driver.get(URL);
        return this;
    }

    public void waitForNewEmail() {
        for (int i = REFRESH_ATTEMPTS; i > 0 && mailCount().startsWith("0"); i--) {
            refreshButton.click();
        }
    }
}