package ch05_02;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

/**
 * extending PageFactory gives us the initFactory
 * method, which allows us to use annotations
 * <p>
 * - create the web elements
 * - annotate them with @FindBy
 * - init the factory then use the elements
 */
public final class SupportClassesPage extends PageFactory {
    private final WebDriver driver;

    @FindBy(how = How.ID, using = "resend-select")
    public WebElement singleResendButton;

    @FindBy(how = How.CSS, using = "#message")
    public WebElement message;

    public SupportClassesPage(WebDriver driver) {
        // initialize the @FindBy
        // annotated WebElements with proxies
        this.driver = driver;
        initElements(driver, this);
    }

    public String waitForMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(visibilityOf(message));
        return message.getText();
    }
}
