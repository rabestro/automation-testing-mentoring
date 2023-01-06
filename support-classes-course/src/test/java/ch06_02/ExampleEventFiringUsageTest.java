package ch06_02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.BDDAssertions.then;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ExampleEventFiringUsageTest {
    private WebDriver driver;

    @BeforeEach
    public void createDriver() {
        driver = new ChromeDriver();

        // trigger time delays with the hash and extra delay with _
        driver.get("https://eviltester.github.io/supportclasses/#_1000");
    }

    @Test
    void create_from_abstract() {
        var events = new EventFiringDecorator<>(
                new HighLighterEventListener(), new ScreenshotEventListener()
        ).decorate(driver);

        var page = new SimpleSupportPageObject(events);
        var message = page.chooseOption(4);

        then(message)
                .endsWith("selected 4");

        message = page.chooseOption(3);

        then(message)
                .endsWith("selected 3");

        message = page.chooseOption(2);

        then(message)
                .endsWith("selected 2");

        message = page.chooseOption(1);

        then(message)
                .endsWith("selected 1");
    }

    @AfterEach
    void closeDriver() {
        driver.quit();
    }

    private static class SimpleSupportPageObject {
        private final WebDriver driver;
        private final WebDriverWait wait;

        SimpleSupportPageObject(final WebDriver driver) {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }

        String chooseOption(int optionNumber) {

            var option = this.driver.findElement(By.id("select-menu"));
            var selectOption = new Select(option);
            selectOption.selectByIndex(optionNumber - 1);

            // wait for all messages to render and return the message response text
            wait.until(
                    visibilityOfElementLocated(By.cssSelector("#single-list li.message")));
            var message = wait.until(
                    visibilityOfElementLocated(By.cssSelector("#message")));
            wait.until(
                    textToBePresentInElementLocated(By.cssSelector("#message"), "Received message:"));

            return message.getText();
        }
    }
}
