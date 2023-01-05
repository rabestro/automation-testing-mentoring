package ch05_01;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

import static org.openqa.selenium.support.ui.ExpectedConditions.textMatches;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

final class SupportClassesTestPage {
    private static final By SELECT_MENU_LOCATOR = By.id("select-menu");
    private static final By MESSAGE_LOCATOR = By.id("message");
    private static final Pattern NON_WHITESPACE = Pattern.compile("\\S");
    private final WebDriverWait wait;
    private final WebDriver driver;

    // basic constructor using WebDriver
    SupportClassesTestPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    void selectSingleOptionMessage(final String singleOptionText) {
        var singleSelectMenu = wait.until(visibilityOfElementLocated(SELECT_MENU_LOCATOR));
        var select = new Select(singleSelectMenu);
        select.selectByVisibleText(singleOptionText);
    }

    void waitForMessageReceived() {
        wait.until(visibilityOfElementLocated(MESSAGE_LOCATOR));
        wait.until(textMatches(MESSAGE_LOCATOR, NON_WHITESPACE));
    }

    String getLastSingleMessage() {
        return driver.findElement(MESSAGE_LOCATOR).getText();
    }
}
