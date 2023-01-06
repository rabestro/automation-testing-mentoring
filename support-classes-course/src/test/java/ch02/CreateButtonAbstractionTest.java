package ch02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class CreateButtonAbstractionTest {
    static WebDriver driver;

    @BeforeAll
    public static void createDriver() {
        driver = new ChromeDriver();
        driver.get("https://eviltester.github.io/supportclasses/");
    }

    @AfterAll
    public static void closeDriver() {
        driver.quit();
    }

    @Test
    void click_a_button() {
        var buttonElement = driver.findElement(By.id("resend-select"));
        var button = new Button(buttonElement);

        then(button)
                .extracting(Button::getText)
                .isEqualTo("Resend Single Option Message");

        // rather than click on a button element,
        // could we click on a Button?

        button.click();

        new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.textToBe(By.id("message"),
                        "Received message: selected 1"));
    }

    private record Button(WebElement button) implements WrapsElement {

        @Override
        public WebElement getWrappedElement() {
            return button;
        }

        public String getText() {
            return button.getText();
        }

        public void click() {
            button.click();
        }
    }
}
