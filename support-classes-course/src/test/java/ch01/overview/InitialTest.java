package ch01.overview;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Initial test")
final class InitialTest {
    private static WebDriver driver;

    @BeforeAll
    static void createDriver() {
        driver = new ChromeDriver();
        driver.get("https://eviltester.github.io/supportclasses/");
    }

    @AfterAll
    static void closeDriver() {
        driver.quit();
    }

    @Test
    @DisplayName("The title is correct")
    void anInitialTest() {
        assertThat(driver.getTitle())
                .isEqualTo("Support Classes Example");
    }
}
