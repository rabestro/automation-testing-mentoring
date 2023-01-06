package ch05_05;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class PageSyncObjectTest {
    private WebDriver driver;

    @BeforeEach
    void createDriver() {
        driver = new ChromeDriver();
    }

    @Test
    void can_see_message_in_history() {
        // to show a component on the page
        var page = new LoadableSupportPage(driver);

        // we should really wait for page to load prior to doing anything,
        // to make sure it is valid to work with
        page.get();

        // when
        page.select("Option 2");

        then(page.getMessage())
                .isEqualTo("Received message: selected 2");

        var history = page.messageHistory();

        // wait for the history component to be ready
        history.waitTillReady();

        then(history.countSingleHistoryMessages()).isOne();

        then(history.getSingleHistoryMessage(0))
                .isEqualTo("Received message: selected 2");
    }

    @AfterEach
    void closeDriver() {
        driver.quit();
    }
}
