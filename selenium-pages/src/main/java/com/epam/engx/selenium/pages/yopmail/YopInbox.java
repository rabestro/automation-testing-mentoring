package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public final class YopInbox extends Page {
    private static final String URL = "https://yopmail.com/wm";

    @FindBy(css = "div.wmtop > div.ycptalias > div.bname")
    private WebElement email;

    public YopInbox(WebDriver driver) {
        super(driver);
    }

    public String email() {
        return email.getText();
    }

    @Override
    public YopInbox to() {
        driver.get(URL);
        return this;
    }
}