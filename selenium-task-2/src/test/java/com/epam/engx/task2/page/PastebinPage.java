package com.epam.engx.task2.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public final class PastebinPage {
    private static final String HOMEPAGE_URL = "https://pastebin.com/";
    private static final String XPATH_TEN_MINUTE = "/html/body/span[2]/span/span[2]/ul/li[3]";

    private WebDriver driver;

    @FindBy(id = "postform-text")
    private WebElement text;

    @FindBy(id = "postform-name")
    private WebElement title;

    @FindBy(id = "select2-postform-expiration-container")
    private WebElement expirationContainer;

    @FindBy(xpath = XPATH_TEN_MINUTE)
    private WebElement expirationTenMin;

    // #w0 > div.post-form__bottom > div.post-form__left > div.form-group.field-postform-format > div > span > span.selection > span
    // //*[@id="w0"]/div[5]/div[1]/div[3]/div/span/span[1]/span
//    @FindBy(id = "select2-postform-format-container")
    // #w0 > div.post-form__bottom > div.post-form__left > div.form-group.field-postform-format > div
    // //*[@id="w0"]/div[5]/div[1]/div[3]/div
//    @FindBy(id = "postform-format")
//    @FindBy(xpath = "//*[@id=\"w0\"]/div[5]/div[1]/div[3]/div/span/span[1]/span")
//    @FindBy(xpath = "//span[@id='select2-postform-format-container']")
//    @FindBy(id = "select2-postform-format-container")
//    @FindBy(xpath = "//*[@id='w0']/div[5]/div[1]/div[3]/div/span/span[1]/span/span[2]")
//    private WebElement formatContainer;

//    @FindBy(css = ".select2-search__field")
//    @FindBy(xpath = "//input[@type='search']")
//    private WebElement formatSearch;

    // #select2-postform-format-result-qoww-8
//    @FindBy(id = "/html/body/span[2]/span/span[2]/ul/li[2]/ul/li[1]")
//    private WebElement formatBash;

    @FindBy(id = "/html/body/div[1]/div[2]/div[1]/div[2]/div/form/div[5]/div[1]/div[10]/button")
    private WebElement submitButton;

    public PastebinPage(WebDriver driver) {
        this.driver = driver;
        driver.get(HOMEPAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void postCode(CharSequence name, CharSequence code) {


        text.sendKeys(code);
        title.sendKeys(name);
        expirationContainer.click();
        expirationTenMin.click();

        try {
            // 2 | click | id=select2-postform-format-container |
            driver.findElement(By.id("select2-postform-format-container")).click();
            // 3 | type | css=.select2-search__field | Bash
            driver.findElement(By.cssSelector(".select2-search__field")).sendKeys("Bash");
            // 4 | sendKeys | css=.select2-search__field | ${KEY_ENTER}
            driver.findElement(By.cssSelector(".select2-search__field")).sendKeys(Keys.ENTER);

        } catch (ElementClickInterceptedException e) {
            System.out.println(e.getMessage());
        }

//        formatContainer.click();
//        formatSearch.sendKeys("Bash");
//        formatSearch.sendKeys(Keys.ENTER);
//        formatBash.click();
//        submitButton.click();
    }

    public String code() {
        return text.getAttribute("value");
    }

    public String title() {
        return title.getAttribute("value");
    }
}

