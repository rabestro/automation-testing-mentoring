package ch03;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class LocatorsTest {
    private static WebDriver driver;

    @BeforeAll
    static void createDriver() {
        driver = new ChromeDriver();
        driver.get("https://eviltester.github.io/supportclasses/");

    }

    @AfterAll
    public static void closeDriver() {
        driver.quit();
    }

    @Test
    void core_By_exploration() {
        // Most of the By classes are in core selenium webdriver
        var title = driver.findElement(By.id("instruction-title"));

        then(title.getText())
                .startsWith("Instructions");

        // support classes have
        // ByIdOrName
        // ByAll - union of all by
        // ByChained
    }

    @Test
    void find_element_by_id_or_name() {
        var idButton = driver.findElement(By.id("resend-select"));

        then(idButton)
                .extracting(WebElement::getText)
                .isEqualTo("Resend Single Option Message");


        var namedButton = driver.findElement(By.name("resend-select"));

        then(namedButton)
                .extracting("text")
                .isEqualTo("Resend Multi Option Message");

        // ByIdOrName can match by id, and if that doesn't match treat it as a name
        // use ByIdOrName to find a button element "resend-select"
        // and the assertions should pass
        var button = driver.findElement(new ByIdOrName("resend-select"));

        then(button)
                .isEqualTo(idButton)
                .isNotEqualTo(namedButton);


        //ByIdOrName findElements returns all id and name matches
        //findElements for "resend-select" should find 2 buttons
        var buttons = driver.findElements(new ByIdOrName("resend-select"));

        then(buttons)
                .as("the elements identified should be the same as we found initially")
                .hasSize(2)
                .contains(idButton, namedButton);
    }

    @Test
    void find_element_by_all() {
        // we could use ByAll to find by id or by name
        // by all is a collator, so given a number of locators, find all items that match
        var buttons = driver.findElements(
                new ByAll(By.id("resend-select"), By.name("resend-select")));

        then(buttons)
                .hasSize(2)
                .contains(driver.findElement(By.id("resend-select")))
                .contains(driver.findElement(By.name("resend-select")));
    }

    @Test
    void find_element_by_chained() {
        var resendSingle = driver.findElement(By.id("resend-select"));
        resendSingle.click();
        resendSingle.click();
        resendSingle.click();
        resendSingle.click();

        var resend = driver.findElement(By.id("resend-multi"));
        resend.click();
        resend.click();

        // TODO: make this more specific to only find messages under a 'list'
        var allMessages = driver.findElements(
                new ByChained(By.name("list"), By.className("message")));

        then(allMessages)
                .hasSize(6);

        // then just the #single list .message
        var singleMessages = driver.findElements(
                new ByChained(By.id("single"), By.name("list"), By.className("message")));

        then(singleMessages)
                .hasSize(4);

        // then the #multi list .message
        var multiMessages = driver.findElements(
                new ByChained(By.id("multi"), By.name("list"), By.className("message")));

        then(multiMessages)
                .hasSize(2);
    }
}
