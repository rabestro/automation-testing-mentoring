package ch05_03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class ElementLocatorPageFactoryTest {
    private WebDriver driver;

    @BeforeEach
    void createDriver() {
        driver = new ChromeDriver();

        // trigger time delays with a hash e.g. #2000
        // trigger extra delay to display with an underscore #_2000
        driver.get("https://eviltester.github.io/supportclasses/#_2000");
    }

    @Test
    void send_message() {
        var page = new SupportPage(driver);
        page.singleResendButton.click();

        then(page.waitForMessage())
                .isEqualTo("Received message: selected 1");
    }

    @AfterEach
    void closeDriver() {
        driver.quit();
    }
}
