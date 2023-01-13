package ch05_06;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class PageSyncObjectTest {
    private WebDriver driver;

    @BeforeEach
    public void createDriver() {
        driver = new ChromeDriver();
    }

    @Test
    void can_see_message_in_history() {
        // to show a component on the page
        var page = new LoadableSupportPage(driver);
        page.get();

        // when
        page.select("Option 2");

        then(page.getMessage())
                .startsWith("Received message")
                .endsWith("selected 2");

        var history = page.messageHistory();
        // wait for the history component to be ready
        history.get();

        then(history.countSingleHistoryMessages())
                .isOne();

        then(history.getSingleHistoryMessage(0))
                .isEqualTo("Received message: selected 2");
    }

    @AfterEach
    public void closeDriver() {
        driver.quit();
    }
}
