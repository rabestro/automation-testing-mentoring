package ch05_04;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class PageFactoryTest {
    private WebDriver driver;

    @BeforeEach
    public void createDriver() {
        driver = new ChromeDriver();
        driver.get("https://eviltester.github.io/supportclasses");
    }

    @Test
    void send_message_with_wait_in_page_object() {
        // when
        var page = new SupportClassesPage(driver);

        then(page.countSingleMessageHistory())
                .isEqualTo(0);

        // when
        page.clickResendSingleButton();

        then(page.countSingleMessageHistory())
                .isEqualTo(1);

        then(page.waitForMessage())
                .isEqualTo("Received message: selected 1");
    }

    @AfterEach
    void closeDriver() {
        driver.quit();
    }
}
