package com.epam.engx.task2.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public final class BadCodePage {

    @FindBy(className = "content__title")
    private WebElement title;

    @FindBy(className = "content__text")
    private WebElement text;

    public BadCodePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String title() {
        return title.getText();
    }

    public String text() {
        return text.getText();
    }
}
