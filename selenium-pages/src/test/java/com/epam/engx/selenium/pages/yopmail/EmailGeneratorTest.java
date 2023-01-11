package com.epam.engx.selenium.pages.yopmail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.BDDAssertions.then;

class EmailGeneratorTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void get() {
        driver.get("https://yopmail.com/email-generator");
        var geny = new EmailGenerator(driver);

        var email = geny.get();

        System.out.println(email);

        then(email)
                .endsWith("@yopmail.com");

    }
}