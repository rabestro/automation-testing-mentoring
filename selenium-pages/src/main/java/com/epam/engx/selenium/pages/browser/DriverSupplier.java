package com.epam.engx.selenium.pages.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Supplier;


final class DriverSupplier implements Supplier<WebDriver> {
    private static final String LOCAL_HOST = "http://localhost:4444/wd/hub";
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(System.getProperty("environment", "dev"));
    private static final Driver DRIVER = Driver
            .valueOf(getString("browser.name", Driver.FIREFOX.name()).toUpperCase());

    private static String getString(String key, String def) {
        return Objects.requireNonNullElse(RESOURCE_BUNDLE.getString(key), def);
    }

    private static URL seleniumHostUrl() {
        try {
            var seleniumHostAddress = getString("remote.host.url", LOCAL_HOST);
            return new URL(seleniumHostAddress);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebDriver get() {
        return switch (DRIVER) {
            case FIREFOX -> new FirefoxDriver();
            case REMOTE -> new RemoteWebDriver(seleniumHostUrl(),
                    new DesiredCapabilities(Map.of("browserName", Driver.FIREFOX.name())));
            case INCOGNITO -> {
                var prefs = Map.of("profile.default_content_setting_values.cookies", 2);
                var options = new ChromeOptions()
                        .addArguments("--incognito", "start-maximized")
                        .setExperimentalOption("prefs", prefs);
                yield new ChromeDriver(options);
            }
            case CHROME -> new ChromeDriver();
            case SAFARI -> new SafariDriver();
        };
    }

    enum Driver {
        FIREFOX, REMOTE, INCOGNITO, CHROME, SAFARI
    }
}
