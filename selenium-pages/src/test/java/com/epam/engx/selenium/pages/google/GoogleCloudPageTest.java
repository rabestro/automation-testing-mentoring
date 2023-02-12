package com.epam.engx.selenium.pages.google;

import com.epam.engx.selenium.pages.SmokeTest;
import com.epam.engx.selenium.pages.browser.Browser;
import com.epam.reportportal.junit5.ReportPortalExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(ReportPortalExtension.class)
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
