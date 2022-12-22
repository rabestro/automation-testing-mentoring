package com.epam.engx.atm.icanwin.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PastebinPage {
    public static final String HOMEPAGE_URL = "https://pastebin.com/";

    @FindBy(id = "postform-text")
    private WebElement text;

    @FindBy(id = "postform-name")
    private WebElement title;

    @FindBy(id = "select2-postform-expiration-container")
    private WebElement tenMin;

    public PastebinPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void postCode(CharSequence name, CharSequence code) {
        text.sendKeys(code);
        title.sendKeys(name);
        tenMin.click();
    }

    public String code() {
        return text.getAttribute("value");
    }

    public String title() {
        return title.getAttribute("value");
    }
}
