package ch06_02;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

final public class HighLighterEventListener implements WebDriverListener {
    private static final String HIDE_BORDER = "arguments[0].style.border='none'";
    private static final String SHOW_BORDER = "arguments[0].style.border='10px dashed red'";

    private WebElement lastElement;

    // JavaScript to set style off use element.style.border = 'none';
    // JavaScript to set style on use element.style.border='10px dashed red';
    // e.g.
    // document.getElementById("resend-select").style.border='10px dashed red';
    // JavascriptExecutor uses arguments[0] for the elements passed in as params

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        var js = (JavascriptExecutor) driver;

        if (lastElement != null) {
            try {
                js.executeScript(HIDE_BORDER, lastElement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lastElement = null;
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        lastElement = result;
        var js = (JavascriptExecutor) driver;
        js.executeScript(SHOW_BORDER, result);
    }
}
