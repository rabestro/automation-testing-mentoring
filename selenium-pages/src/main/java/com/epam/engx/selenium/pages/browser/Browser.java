package com.epam.engx.selenium.pages.browser;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.Map;

public class Browser {
    private final WebDriver driver;
    private final BidiMap<String, Page> windows;

    public Browser(WebDriver driver) {
        this.driver = driver;
        this.windows = new DualHashBidiMap<>();
        windows.put(driver.getWindowHandle(), null);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public static Browser chrome() {
        var prefs = Map.of("profile.default_content_setting_values.cookies", 2);
        var driver = new ChromeDriver(new ChromeOptions()
                .addArguments("--incognito", "start-maximized")
                .setExperimentalOption("prefs", prefs)
        );
        return new Browser(driver);
    }

    public static Browser create() {
        return firefox();
    }

    public static Browser firefox() {
        return new Browser(new FirefoxDriver());
    }

    public void quit() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    public <T extends Page> T switchTo(T page) {
        driver.switchTo().window(windows.inverseBidiMap().get(page));
        return page;
    }

    public <T extends Page> T switchTo(Class<T> pageClass) {
        var pageEntry = windows.entrySet().stream()
                .filter(entry -> entry.getValue().getClass() == pageClass)
                .findFirst();

        pageEntry.map(Map.Entry::getKey)
                .ifPresent(driver.switchTo()::window);

        return pageEntry
                .map(Map.Entry::getValue)
                .map(pageClass::cast)
                .orElseThrow();
    }

    public <T extends Page> T go(PageConstructor<T> pageConstructor) {
        var page = pageConstructor.apply(driver).to();
        windows.put(driver.getWindowHandle(), page);
        return (T) page;
    }

    public Browser addTab() {
        driver.switchTo().newWindow(WindowType.TAB);
        windows.put(driver.getWindowHandle(), null);
        return this;
    }

    public String title() {
        return driver().getTitle();
    }

    WebDriver driver() {
        return driver;
    }

    BidiMap<String, Page> tabs() {
        return windows;
    }
}
