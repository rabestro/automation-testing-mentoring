package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Page;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

@SuppressWarnings("SpellCheckingInspection")
public final class YopInboxPage extends Page {
    private static final String HOME_PAGE = "https://yopmail.com/wm";

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

    public EstimateMailPage getEstimateEmail() {
        driver.switchTo().frame(mailFraim);
        return new EstimateMailPage(driver);
    }

    @Override
    public YopInboxPage to() {
        driver.get(HOME_PAGE);
        return this;
    }

    @SuppressWarnings("MagicNumber")
    public void waitForNewEmail() {
        new FluentWait<>(refreshButton)
                .withTimeout(Duration.ofSeconds(30L))
                .pollingEvery(Duration.ofSeconds(5L))
                .ignoring(NoSuchElementException.class)
                .until(this::isMailArrived);
    }

    private boolean isMailArrived(WebElement button) {
        button.click();
        return !mailCount().startsWith("0");
    }
}
