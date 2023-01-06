package ch02;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class SelectExampleTest {
    /*
        There are many types of abstractions that we can use and a very simple,
        low level abstraction is an Element Abstraction,
        a wrapper around an element on the screen.
        In this video we will explain what this means and how it can help us using Select as an example.
    */
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

    // or we could instantiate a Select object to wrap the selectMenu WebElement
    // then use the Select methods like selectByVisibleText
    // and assert with methods like getFirstSelectedOption
    // and if I did that I should have a method named
    // canSelectAnOptionUsingSelect

    // explore the rest of the select class functions

    @Test
    void select_an_option_using_select() {
        // given
        var selectMenu = driver.findElement(By.id("select-menu"));
        var select = new Select(selectMenu);

        // when
        select.selectByVisibleText("Option 3");

        then(select.getFirstSelectedOption().getAttribute("value"))
                .isEqualTo("3");
    }

    @Test
    void get_info_about_select() {
        var selectMenu = driver.findElement(By.id("select-menu"));
        var select = new Select(selectMenu);

        then(select.isMultiple())
                .as("the isMultiple method should be false for the select-menu item")
                .isFalse();

        var multiSelectMenu = driver.findElement(By.id("select-multi"));
        var multiSelect = new Select(multiSelectMenu);

        then(multiSelect.isMultiple())
                .as("the isMultiple method should be true for multi select")
                .isTrue();
    }

    @Test
    void get_all_options_from_select() {
        var selectMenu = driver.findElement(By.id("select-menu"));
        var select = new Select(selectMenu);

        // getOptions will return a List of WebElement
        // and allow me to access the options using
        // simple List methods
        var options = select.getOptions();

        then(options)
                .hasSize(4);

        then(options)
                .first()
                .extracting("text")
                .isEqualTo("Option 1");
    }

    @Test
    void select_single_options() {
        // demo test to show single-select capabilities
        var selectMenu = driver.findElement(By.id("select-menu"));
        var select = new Select(selectMenu);

        select.selectByIndex(0);

        then(select.getFirstSelectedOption())
                .as("select will do nothing because this option is selected by default")
                .extracting("text")
                .isEqualTo("Option 1");

        select.selectByIndex(1);

        then(select.getFirstSelectedOption())
                .as("select the second item by index 1 to choose 'Option 2'")
                .extracting("text")
                .isEqualTo("Option 2");

        select.selectByValue("1");

        then(select.getFirstSelectedOption())
                .as("select the first item by using the value '1'")
                .extracting("text")
                .isEqualTo("Option 1");

        select.selectByVisibleText("Option 3");

        then(select.getFirstSelectedOption().getAttribute("value"))
                .as("select using the text in the option")
                .isEqualTo("3");
    }

    @Test
    void select_and_deselect_multi_options() {
        // demo test to show multi-select capabilities
        var selectMenu = driver.findElement(By.id("select-multi"));
        var select = new Select(selectMenu);

        // make sure nothing is selected with deselectAll
        select.deselectAll();

        // A normal select by index to get the First item
        select.selectByIndex(0);

        then(select.getFirstSelectedOption())
                .as("select by index to get the First item")
                .extracting("text")
                .isEqualTo("First");

        // if I select I can deselect - by index, text or value
        select.deselectByIndex(0);

        assertThatThrownBy(select::getFirstSelectedOption)
                .as("when nothing is selected a NoSuchElementException is thrown")
                .isInstanceOf(NoSuchElementException.class);

        // select two items - values 20 and 30
        select.selectByValue("20");
        select.selectByValue("30");

        var selected = select.getAllSelectedOptions();

        then(selected)
                .as("there should be 2 in the list")
                .hasSize(2);

        then(selected)
                .as("assert on the getText for the list entries")
                .extracting("text")
                .containsExactly("Second", "Third");

        select.deselectByVisibleText("Second");

        then(select.getAllSelectedOptions())
                .as("deselect the first one - 'Second")
                .extracting(WebElement::getText)
                .doesNotContain("Second");

        then(select.getFirstSelectedOption())
                .as("the first selected option text is 'Third'")
                .extracting("text")
                .isEqualTo("Third");

        // deselect them all to finish
        select.deselectAll();

        then(select.getAllSelectedOptions())
                .isEmpty();

        then(select.getWrappedElement())
                .isSameAs(selectMenu);
    }
}
