package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@SuppressWarnings("SpellCheckingInspection")
public final class YopInboxPage extends Page {
    private static final String HOME_PAGE = "https://yopmail.com/wm";
    private static final int REFRESH_ATTEMPTS = 10;

    @FindBy(css = "div.wmtop > div.ycptalias > div.bname")
    private WebElement emailAddressLabel;

    @FindBy(id = "nbmail")
    private WebElement mailCount;

    @FindBy(id = "refresh")
    private WebElement refreshButton;

    @FindBy(id = "ifmail")
    private WebElement mailFraim;

    public YopInboxPage(WebDriver driver) {
        super(driver);
    }

    public String emailAddress() {
        return emailAddressLabel.getText();
    }

    public String mailCount() {
        return mailCount.getText();
    }

    public EstimateMailPage email() {
        driver.switchTo().frame(mailFraim);
        return new EstimateMailPage(driver);
    }

    @Override
    public YopInboxPage to() {
        driver.get(HOME_PAGE);
        return this;
    }

    public void waitForNewEmail() {
        for (int i = REFRESH_ATTEMPTS; i > 0 && mailCount().startsWith("0"); i--) {
            refreshButton.click();
        }
    }
}