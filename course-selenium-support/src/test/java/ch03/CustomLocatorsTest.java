package ch03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class CustomLocatorsTest {
    private static WebDriver driver;

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
    void find_by_class_attribute() {
        var resendSingle = driver.findElement(By.id("resend-select"));
        resendSingle.click();
        resendSingle.click();
        resendSingle.click();
        resendSingle.click();

        var resend = driver.findElement(By.id("resend-multi"));
        resend.click();
        resend.click();

        // TODO: create a custom ByAttributeValue locator for any attribute i.e. "class", "message"
        var messages = driver.findElements(
                new ByAttributeValue("class", "message"));

        then(messages)
                .hasSize(6);
    }

    @Test
    void attribute_find_by() {
        // find the instructions via the data-locator attribute
        // https://developer.mozilla.org/en-US/docs/Web/HTML/Global_attributes/data-*
        var instructions = driver.findElement(
                new ByAttributeValue("data-locator", "instructions"));

        then(instructions.getText())
                .isEqualTo("Select an item from the list to show the response message.");
    }

    @Test
    void data_locator_find_by() {
        // TODO create a ByGlobalDataAttribute
        var title = driver.findElement(
                new ByGlobalDataAttribute("title", "historytitle"));

        then(title)
                .extracting("text")
                .isEqualTo("Message History");

    }

    private static class ByAttributeValue extends By {
        private final String name;
        private final String value;

        ByAttributeValue(String attributeName, String attributeValue) {
            this.name = attributeName;
            this.value = attributeValue;
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            return context.findElements(By.cssSelector(
                    "[%s='%s']".formatted(name, value)
            ));
        }
    }

    private static class ByGlobalDataAttribute extends By {
        private final String name;
        private final String value;

        public ByGlobalDataAttribute(String dataAttributeName, String valueToMatch) {
            this.name = dataAttributeName;
            this.value = valueToMatch;
        }

        @Override
        public List<WebElement> findElements(SearchContext context) {
            return context.findElements(By.cssSelector(
                    "[data-%s='%s']".formatted(name, value)
            ));
        }
    }
}
