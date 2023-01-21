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
import java.util.function.Supplier;

final class DriverSupplier implements Supplier<WebDriver> {
    private static final String LOCAL_HOST = "http://localhost:4444/wd/hub";
    private static final String FIREFOX = "firefox";

    private static URL seleniumHostUrl() {
        try {
            var seleniumHostAddress = System.getProperty("selenium.host", LOCAL_HOST);
            return new URL(seleniumHostAddress);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WebDriver get() {
        var browser = System.getProperty("browser", FIREFOX);

        return switch (browser) {
            case FIREFOX -> new FirefoxDriver();
            case "remote" -> new RemoteWebDriver(seleniumHostUrl(),
                    new DesiredCapabilities(Map.of("browserName", FIREFOX)));
            case "incognito" -> {
                var prefs = Map.of("profile.default_content_setting_values.cookies", 2);
                var options = new ChromeOptions()
                        .addArguments("--incognito", "start-maximized")
                        .setExperimentalOption("prefs", prefs);
                yield new ChromeDriver(options);
            }
            case "chrome" -> new ChromeDriver();
            case "safari" -> new SafariDriver();
            default -> throw new IllegalStateException("Unexpected value: " + browser);
        };
    }
}
