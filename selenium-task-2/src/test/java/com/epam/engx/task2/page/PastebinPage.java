package com.epam.engx.task2.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public final class PastebinPage {
    private static final String HOMEPAGE_URL = "https://pastebin.com/";
    private static final String XPATH_TEN_MINUTE = "/html/body/span[2]/span/span[2]/ul/li[3]";

    @FindBy(id = "postform-text")
    private WebElement text;

    @FindBy(id = "postform-name")
    private WebElement title;

    @FindBy(id = "select2-postform-expiration-container")
    private WebElement expirationContainer;

    @FindBy(xpath = XPATH_TEN_MINUTE)
    private WebElement expirationTenMin;

    @FindBy(id = "select2-postform-format-container")
    private WebElement format;

    public PastebinPage(WebDriver driver) {
        driver.get(HOMEPAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void postCode(CharSequence name, CharSequence code) {
        text.sendKeys(code);
        title.sendKeys(name);
        expirationContainer.click();
        expirationTenMin.click();

//        format.click();
        text.submit();
    }

    public String code() {
        return text.getAttribute("value");
    }

    public String title() {
        return title.getAttribute("value");
    }
}

