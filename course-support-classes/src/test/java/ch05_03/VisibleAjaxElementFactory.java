package ch05_03;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

final class VisibleAjaxElementFactory implements ElementLocatorFactory {
    private final WebDriver driver;
    private final int timeOutInSeconds;

    VisibleAjaxElementFactory(WebDriver driver, int timeOutInSeconds) {
        this.driver = driver;
        this.timeOutInSeconds = timeOutInSeconds;
    }

    @Override
    public ElementLocator createLocator(Field field) {
        return new VisibleAjaxElementLocator(driver, field, timeOutInSeconds);
    }

    private static class VisibleAjaxElementLocator extends AjaxElementLocator {
        public VisibleAjaxElementLocator(WebDriver driver, Field field, int timeOutInSeconds) {
            super(driver, field, timeOutInSeconds);
        }

        @Override
        protected boolean isElementUsable(WebElement element) {
            return element != null
                    && element.isDisplayed()
                    && element.isEnabled();
        }
    }
}
