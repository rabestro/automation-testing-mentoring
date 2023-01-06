package ch06_01;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ExampleEventFiringTest {
    WebDriver driver;

    @BeforeEach
    public void createDriver() {
        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/supportclasses/");

    }

    @Test
    void logging_finding_elements() {
        var resend = By.id("resend-select");
        var noSuchElement = By.id("no-such-element");

        var events = new EventFiringWebDriver(driver);
        events.register(new LocalEventFiringListener());

        var resendElem = events.findElement(resend);

        then(resendElem).isNotNull();

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
    public void closeDriver() {
        driver.quit();
    }

    private static class LocalEventFiringListener extends AbstractWebDriverEventListener {

        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("Looking For " + by.toString());
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("Finished looking for " + by.toString());
        }
    }
}
