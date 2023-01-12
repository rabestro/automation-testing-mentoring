package com.epam.engx.selenium.pages.google;

import com.epam.engx.selenium.pages.browser.Browser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class GoogleCloudTest {

    private static Browser browser;

    @BeforeAll
    static void setUp() {
        browser = Browser.chrome();
    }

    @AfterAll
    static void tearDown() {
        browser.quit();
    }

    @Test
    void goTo() {
        var page = browser.go(GoogleCloud::new);

        then(browser.title())
                .contains("Google", "Cloud", "Computing Services");
    }

}