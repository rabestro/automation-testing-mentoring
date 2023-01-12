package ch06_01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ExampleEventFiringTest {
    private WebDriver driver;

    @BeforeEach
    void createDriver() {
        driver = new ChromeDriver();
        driver.get("https://eviltester.github.io/supportclasses/");
    }

    @Test
    void logging_finding_elements() {
        // given
        var resend = By.id("resend-select");
        var noSuchElement = By.id("no-such-element");

        var events = new EventFiringDecorator<>(new LocalEventFiringListener())
                .decorate(driver);

        // when
        var resendElem = events.findElement(resend);

        then(resendElem)
                .isNotNull();

        // when
        var thrown = catchThrowable(
                () -> events.findElement(noSuchElement)
        );

        then(thrown)
                .isInstanceOf(NoSuchElementException.class)
                .message()
                .startsWith("no such element");
    }


    @AfterEach
    void closeDriver() {
        driver.quit();
    }

    public static class LocalEventFiringListener implements WebDriverListener {
        @Override
        public void beforeFindElement(WebDriver driver, By locator) {
            System.out.println("Looking For " + locator.toString());
        }

        @Override
        public void afterFindElement(WebDriver driver, By locator, WebElement result) {
            System.out.println("Finished looking for " + locator.toString());
        }
    }
}
