package com.epam.engx.selenium.pages.google;

import com.epam.engx.selenium.pages.SmokeTest;
import com.epam.engx.selenium.pages.browser.Browser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GoogleCloudPageTest {
    private static Browser browser;

    @BeforeAll
    static void setUp() {
        browser = Browser.create();
    }

    @AfterAll
    static void tearDown() {
        browser.quit();
    }

    @SmokeTest
    void go_to_google_cloud_page() {
        browser.go(GoogleCloudPage::new);

        then(browser.title())
            .contains("Google", "Cloud", "Computing Services");
    }
}
