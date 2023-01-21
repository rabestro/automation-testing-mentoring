package com.epam.engx.selenium.pages;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Objects;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class BaseLocalTest {
    protected static WebDriver driver;

    @BeforeAll
    protected static void setUp() {
        driver = new FirefoxDriver();
    }

    @AfterAll
    protected static void tearDown() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }

    protected void openHtmlFile(String htmlFile) {
        var url = getClass().getResource(htmlFile);
        driver.get("file://" + Objects.requireNonNull(url).getPath());
    }
}
