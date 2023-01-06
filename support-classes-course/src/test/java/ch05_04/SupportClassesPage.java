package ch05_04;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Avoid making your WebElement objects public
 * Use a WebElement proxy in a WebDriverWait
 * You can use custom ElementLocatorFactory approaches
 * You don't have to extend PageFactory
 * You can annotate List of WebElement
 * <p>
 * Additional annotations
 * <p>
 * - @FindBys
 * - use an array of @FindBy in sequence to find an element { @FindBy(...),@FindBy(...) }
 * - @FindAll
 * - use an array of @FindBy and return all matching items
 * - @CacheLookup
 * - if an Element never changes in the DOM then we can cache it
 */
final class SupportClassesPage {
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "resend-select")
    private WebElement singleResendButton;

    @FindBy(how = How.CSS, using = "#message")
    private WebElement message;

    //@FindBy(how = How.CLASS_NAME, using="message")
    @FindBys({
            @FindBy(how = How.ID, using = "single"),
            @FindBy(how = How.CLASS_NAME, using = "message")
    })
    private List<WebElement> singleMessages;

    @CacheLookup
    @FindBy(how = How.ID, using = "history")
    private WebElement historyTitle;

    SupportClassesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    String waitForMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.visibilityOf(message));
        return message.getText();
    }

    int countSingleMessageHistory() {
        return singleMessages.size();
    }

    String getHistoryTitle() {
        return historyTitle.getText();
    }

    void clickResendSingleButton() {
        singleResendButton.click();
    }
}
