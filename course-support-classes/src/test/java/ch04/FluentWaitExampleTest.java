package ch04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * WebDriverWait is built on top of FluentWait,
 * we can use this to wait on anything, not just WebDriver
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class FluentWaitExampleTest {
    private WebDriver driver;

    @BeforeEach
    void createDriver() {
        driver = new ChromeDriver();
        // trigger time delays with the hash
        driver.get("https://eviltester.github.io/supportclasses/#2000");
    }

    @Test
    @SuppressWarnings("MagicNumber")
    void explicit_wait_is_fluent() {
        var resendButton = driver.findElement(By.id("resend-select"));
        resendButton.click();

        // WebDriverWait is built on FluentWait so we have a lot of control over the wait
        // todo: customise timeout message, poll every 50 milliseconds,
        //       and ignore StaleElementReferenceException.class
        var message = new WebDriverWait(driver, Duration.ofSeconds(5))
                .withMessage("Could not find a Message")
                .pollingEvery(Duration.ofMillis(50))
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#single-list li.message")));

        then(message.getText())
                .startsWith("Received message:");
    }

    // using fluent wait to wait using WebElement rather than driver
    @Test
    @SuppressWarnings("MagicNumber")
    void using_fluent_wait() {
        var resendButton = driver.findElement(By.id("resend-select"));
        resendButton.click();

        var singleListParent = driver.findElement(By.id("single-list"));
        var wait = new FluentWait<>(singleListParent)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("Could not find any new messages");

        wait.until(new HistoryMessagesIncreaseInNumber(0));

        var message = driver.findElement(By.cssSelector("#single-list li.message"));

        then(message.getText())
                .startsWith("Received message:");
    }

    @AfterEach
    void closeDriver() {
        driver.quit();
    }

    private record HistoryMessagesIncreaseInNumber(int initialCount) implements Function<WebElement, Boolean> {

        @Override
        public Boolean apply(final WebElement element) {
            int currentCount = element.findElements(By.cssSelector("li.message")).size();
            return currentCount > initialCount;
        }
    }
}
