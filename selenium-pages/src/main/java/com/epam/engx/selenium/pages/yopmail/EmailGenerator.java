package com.epam.engx.selenium.pages.yopmail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.function.Supplier;

// page_url = https://yopmail.com/email-generator
public class EmailGenerator extends PageFactory implements Supplier<String> {
    @FindBy(id = "geny")
    private WebElement generatedEmail;

    public EmailGenerator(WebDriver driver) {
        initElements(driver, this);
    }

    @Override
    public String get() {
        return generatedEmail.getText();
    }
}