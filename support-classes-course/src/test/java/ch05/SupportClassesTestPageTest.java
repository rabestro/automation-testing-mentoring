package ch05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Test using abstractions is:
 * <p>
 * - easy to read
 * - only has to change if intent of test changes
 * - does not have to change if application changes - the page objects change
 * - page objects used in multiple tests
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class SupportClassesTestPageTest {
    private WebDriver driver;

    @BeforeEach
    public void createDriver(){
        driver = new ChromeDriver();
        driver.get("https://eviltester.github.io/supportclasses/#2000");
    }

    @Test
    void send_message(){
        // given
        var page = new SupportClassesTestPage(driver);

        // when
        page.selectSingleOptionMessage("Option 2");
        page.waitForMessageReceived();

        then(page.getLastSingleMessage())
                .isEqualTo("Received message: selected 2");
    }

    @AfterEach
    public void closeDriver(){
        driver.quit();
    }
}
