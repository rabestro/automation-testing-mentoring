package ch05_05;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

final class LoadableSupportPage extends LoadableComponent<LoadableSupportPage> {
    private final WebDriverWait wait;
    private final WebDriver driver;

    @FindBy(id = "select-menu")
    private WebElement selectMenu;

    @FindBy(id = "resend-select")
    private WebElement resendSelect;

    @FindBy(id = "message")
    private WebElement message;

    LoadableSupportPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    void select(String singleOptionText) {
        var select = new Select(selectMenu);
        select.selectByVisibleText(singleOptionText);
    }

    String getMessage() {
        wait.until(visibilityOf(message));
        wait.until(textToBePresentInElement(message, "Received"));
        return message.getText();
    }

    MessageHistoryComponent messageHistory() {
        return new MessageHistoryComponent(driver);
    }

    @Override
    protected void load() {
        driver.get("https://eviltester.github.io/supportclasses/#_2000");
    }

    @Override
    protected void isLoaded() throws Error {
        if (notLoaded()) {
            throw new Error("Page has not loaded");
        }
    }

    private boolean notLoaded() {
        try {
            return !driver.getTitle().equals("Support Classes Example")
                    || !resendSelect.isEnabled();
        } catch (Exception e) {
            return true;
        }
    }
}
