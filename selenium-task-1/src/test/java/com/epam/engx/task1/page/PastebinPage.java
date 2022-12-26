package com.epam.engx.task1.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public final class PastebinPage {
    public static final String HOMEPAGE_URL = "https://pastebin.com/";

    @FindBy(id = "postform-text")
    private WebElement text;

    @FindBy(id = "postform-name")
    private WebElement title;

    @FindBy(id = "select2-postform-expiration-container")
    private WebElement expirationContainer;

    @FindBy(xpath = "/html/body/span[2]/span/span[2]/ul/li[3]")
    private WebElement expirationTenMin;

    public PastebinPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void postCode(CharSequence name, CharSequence code) {
        text.sendKeys(code);
        title.sendKeys(name);
        expirationContainer.click();
        expirationTenMin.click();
    }

    public String code() {
        return text.getAttribute("value");
    }

    public String title() {
        return title.getAttribute("value");
    }

    public String expiration() {
        return expirationContainer.getText();
    }
}
