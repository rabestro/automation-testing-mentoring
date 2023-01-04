package ch01.overview;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ByIdOrName;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.Colors;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.BDDAssertions.then;

final class QuickWinsTest {
    /*
    This test contains code to demo:

    - Select, which is a WebElement Abstraction
    - a custom By Selector ByIdOrName
    - Quotes for creating XPath quoted locators
    - Colours for working with Colours
    */

    static WebDriver driver;

    @BeforeAll
    static void createDriver() {
        driver = new ChromeDriver();
        driver.get("https://eviltester.github.io/supportclasses/");

    }

    @AfterAll
    public static void closeDriver() {
        driver.quit();
    }

    // The select class makes it easy to work with Select options
    // rather than finding the select menu and then all the options
    // below it - this is the only Element Abstraction in the
    // support classes
    @Test
    @DisplayName("select an option using 'Select' class")
    void canSelectAnOptionUsingSelect() {
        // given
        var selectMenu = driver.findElement(By.id("select-menu"));
        var select = new Select(selectMenu);
        // when
        select.selectByVisibleText("Option 3");

        then(select.getFirstSelectedOption().getAttribute("value"))
                .isEqualTo("3");
    }

    @Test
    @DisplayName("find instructions ByIdOrName")
    void findInstructionsByIdOrName() {
        // findElement returns the element with the id if it exists,
        // and if not searches for it via the name
        var instructionsPara = driver.findElement(new ByIdOrName("instruction-text"));
        var instructionsParaAgain = driver.findElements(new ByIdOrName("instructions"));

        then(instructionsParaAgain)
                .first()
                .extracting("text")
                .isEqualTo(instructionsPara.getText());
    }

    @Test
    @SuppressWarnings("SpellCheckingInspection")
    @DisplayName("escape quotes to create xpath")
    void quotesEscapingToCreateXPath() {
        then(Quotes.escape("literal"))
                .isEqualTo("\"literal\"");

        then(Quotes.escape("'single-quoted'"))
                .isEqualTo("\"'single-quoted'\"");

        then(Quotes.escape("\"double-quoted\""))
                .isEqualTo("'\"double-quoted\"'");

        then(Quotes.escape("\"quot'end\""))
                .isEqualTo("concat(\"\", '\"', \"quot'end\", '\"')");

        then(Quotes.escape("'quo\"ted'"))
                .isEqualTo("concat(\"'quo\", '\"', \"ted'\")");
    }

    @Test
    @DisplayName("convert colors between RBG, HEX and RGBA")
    void colors() {
        var title = driver.findElement(By.id("instruction-title"));

        // Colors is an enum of named Color objects

        var blackValue = Colors.BLACK.getColorValue();

        // Color has methods to help convert between RBG, HEX

        then(blackValue.asHex())
                .isEqualTo("#000000");

        then(blackValue.asRgba())
                .isEqualTo("rgba(0, 0, 0, 1)");

        then(blackValue.asRgb())
                .isEqualTo("rgb(0, 0, 0)");

        // color values returned by WebElement's getCSSValue are always
        // RGBA format, not the HTML source HEX or RGB

        then(title.getCssValue("background-color"))
                .as("color value returned by WebElement's getCSSValue")
                .isEqualTo(blackValue.asRgba());

        // can create custom colors using the RGB input constructor
        // if the Colors enum does not have what we need

        var redValue = new Color(255, 0, 0, 1);

        then(title.getCssValue("color"))
                .isEqualTo(redValue.asRgba());
    }

    @Test
    @DisplayName("wait for message")
    void waitForMessage() {

        var selectMenu = driver.findElement(By.id("select-menu"));
        var select = new Select(selectMenu);

        select.selectByVisibleText("Option 2");

        // We are so used to using WebDriverWait and the ExpectedConditions class
        // that we might not have realised these are part of the support packages

        then(new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions
                .textToBe(By.id("message"), "Received message: selected 2")))
                .isTrue();
    }
}
