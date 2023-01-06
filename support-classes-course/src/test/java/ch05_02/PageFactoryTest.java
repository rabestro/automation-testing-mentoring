package ch05_02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class PageFactoryTest {

    private WebDriver driver;

    @BeforeEach
    public void createDriver() {
        driver = new ChromeDriver();
        driver.get("https://eviltester.github.io/supportclasses/#2000");
    }

    @Test
    void send_message_without_wait() {
        // given
        var page = new SupportClassesPage(driver);
        page.singleResendButton.click();

        // when
        var thrown = catchThrowable(page.message::getText);

        then(thrown)
                .isInstanceOf(NoSuchElementException.class)
                .message()
                .startsWith("no such element")
                .contains("Unable to locate element")
                .contains("#message");
    }

    // the default most people use for handling timeout issues with
    // page factory is implicit waits

    @Test
    void send_message_with_wait_in_page_object() {
        // given
        var page = new SupportClassesPage(driver);
        page.singleResendButton.click();

        // when
        var message = page.waitForMessage();

        then(message)
                .startsWith("Received message")
                .endsWith("selected 1");
    }

    @AfterEach
    public void closeDriver() {
        driver.quit();
    }
}
