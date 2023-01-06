package ch04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

/**
 * Explanation of synchronisation with an example of what happens when
 * we don't synchronise and a test works once, but doesn't work second time through.
 * <p>
 * Explain difference between implicit wait and explicit wait
 * and why we use WebDriverWait as an explicit wait.
 * <p>
 * The key to writing good synchronisation.
 * <p>
 * Explain how WebDriverWait works and general practices around doing it
 * - wait and return, rather than wait, then find
 * - re-use waits
 * - timeouts
 * - this is synchronisation not 'sleep' based on time
 * - we can also use WebDriverWait as an assertion mechanism, so we don't need asserts in page objects
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class SynchronisationExampleTest {
    private WebDriver driver;

    @BeforeEach
    void createDriver() {
        driver = new ChromeDriver();
        // trigger time delays with the hash
        driver.get("https://eviltester.github.io/supportclasses/#2000");
    }

    @Test
    void why_waits_are_required() {
        // given
        var resendButton = driver.findElement(By.id("resend-select"));
        resendButton.click();

        // when
        var thrown = catchThrowable(() ->
                driver.findElement(By.cssSelector("#single-list li.message"))
        );

        then(thrown)
                .as("the message is not immediately displayed, need to wait")
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void implicit_wait() {
        var resendButton = driver.findElement(By.id("resend-select"));
        resendButton.click();

        // implicit wait forces all findElement to poll until it passes or timeout end 5 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // the message is not immediately displayed so this line will fail without an implicit wait
        var message = driver.findElement(By.cssSelector("#single-list li.message"));

        // (1) the simplest way
        then(message.getText())
                .startsWith("Received message:");

        // slows down tests on failures
        // may cause some tests to pass which should not
        // only option is to increase global timeout when timing issues happen, which slows tests down further
        // hard to check for absence of something since it takes as long as the timeout

        // remember to set implicit waits off if you use them otherwise it will affect all findElement commands
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
    }

    @Test
    void explicit_wait() {
        // explicit wait means only waiting at specific points
        var resendButton = driver.findElement(By.id("resend-select"));
        resendButton.click();

        // the message is not immediately displayed, so I need wait for
        // visibility of Element to change
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#single-list li.message")
                )
        );

        // need to wait for the element to be visible prior to trying to find it
        // and assert on the result
        var message = driver.findElement(By.cssSelector("#single-list li.message"));

        // (2) alternative
        then(message)
                .extracting("text")
                .asString()
                .startsWith("Received message:");
    }

    @Test
    void share_wait_and_use_on_return() {
        // often we share a wait e.g. set this up in @BeforeX methods
        var resendButton = driver.findElement(By.id("resend-select"));
        resendButton.click();

        // normally we 'wait and return' rather than wait then repeat find
        var wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        var message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#single-list li.message")));

        // (3) alternative
        then(message)
                .extracting(WebElement::getText)
                .asString()
                .startsWith("Received message:");
    }

    @AfterEach
    public void closeDriver() {
        driver.quit();
    }
}
