package ch06_02;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;

import java.io.File;
import java.io.IOException;

final public class ScreenshotEventListener implements WebDriverListener {

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        var screenshot = ((TakesScreenshot) driver).
                getScreenshotAs(OutputType.FILE);

        var screenshotFolder = new File(System.getProperty("user.dir"),
                "screenshots");

        screenshotFolder.mkdirs();

        try {
            FileUtils.copyFile(screenshot,
                    new File(screenshotFolder, System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
