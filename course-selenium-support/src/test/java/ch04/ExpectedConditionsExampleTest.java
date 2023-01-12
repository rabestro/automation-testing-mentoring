package ch04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.BDDAssertions.then;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * The ExpectedConditions class provides a lot of pre-built methods for synchronisation,
 * provide a description of these. showing some in action. Look at the code to see how
 * they work as static methods that return an Expected Condition. Create an expected condition
 * based on the patterns shown in ExpectedConditions class to sync on something specific.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ExpectedConditionsExampleTest {
    private WebDriver driver;

    @BeforeEach
    public void createDriver() {
        driver = new ChromeDriver();
        // trigger time delays with the hash
        driver.get("https://eviltester.github.io/supportclasses/#2000");
    }


    @Test
    void explicit_wait_using_expected_conditions() {
        var resendButton = driver.findElement(By.id("resend-select"));
        resendButton.click();

        // the message is not immediately displayed but our explicit wait will handle this
        // presenceOfElementLocated
        // visibilityOfElementLocated
        // explain other conditions

        final WebElement message = new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(visibilityOfElementLocated(By.cssSelector("#single-list li.message")));

        // view code and see how expected conditions work
        then(message.getText())
                .startsWith("Received message:");
    }

    @Test
    void explicit_wait_using_custom_expected_condition() {
        var resendButton = driver.findElement(By.id("resend-select"));
        resendButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                historyMessagesIncreaseInNumber()
        );
        var message = driver.findElement(By.cssSelector("#single-list li.message"));

        then(message.getText())
                .startsWith("Received message:");
    }

    private ExpectedCondition<Boolean> historyMessagesIncreaseInNumber() {
        return new ExpectedCondition<>() {
            private final int initialCount = driver.findElements(By.cssSelector("li.message")).size();

            @Override
            public Boolean apply(WebDriver webDriver) {
                int currentCount = driver.findElements(By.cssSelector("li.message")).size();
                return currentCount > initialCount;
            }
        };
    }

    @AfterEach
    public void closeDriver() {
        driver.quit();
    }
}
