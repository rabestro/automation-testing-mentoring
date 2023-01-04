package com.epam.engx.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Map;

public class WebDriverFabric {

    public WebDriver incognito() {
        var prefs = Map.of("profile.default_content_setting_values.cookies", 2);
//        var driver = new ChromeDriver(new ChromeOptions()
//                .addArguments("--incognito", "start-maximized")
//                .setExperimentalOption("prefs", prefs)
//        );
        var driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        return driver;
    }
}
