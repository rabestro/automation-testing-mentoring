package com.epam.engx.selenium.pages.browser;

import com.epam.engx.selenium.pages.google.GoogleCloud;
import com.epam.engx.selenium.pages.google.SearchResult;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BrowserTest {
    private static final String TERM = "Google Cloud Platform Pricing Calculator";
    private static Browser browser;

    @BeforeAll
    static void setUp() {
        browser = Browser.firefox();
    }

    @AfterAll
    static void tearDown() {
        browser.quit();
    }

    @Test
    void add_new_tab_without_opening_a_new_page() {
        // when we open a page in the browser
        var page = browser.go(GoogleCloud::new);

        then(browser.title())
                .as("the browser has a title of opened page")
                .contains("Google", "Cloud", "Computing Services");

        then(browser.tabs())
                .as("the browser has only one tab")
                .hasSize(1)
                .containsValue(page);

        // when we add a new tab to the browser
        browser.addTab();

        then(browser.title())
                .as("the title of new tab is empty")
                .isEmpty();

        then(browser.tabs())
                .as("the browser has two tabs one of them is null")
                .hasSize(2)
                .containsValues(page, null);
    }

    @Test
    void open_a_new_page_in_the_same_tab() {
        // given the browser opens the Google Cloud page
        var googleCloud = browser.go(GoogleCloud::new);

        // when we perform search without opening the new tab
        var searchResult = browser.go(googleCloud.searchFor(TERM));

        then(searchResult)
                .as("the search produce a lot of results")
                .isInstanceOf(SearchResult.class)
                .extracting(SearchResult::links).asList()
                .hasSizeGreaterThan(10);

        then(browser.title())
                .as("the current page has an appropriate title")
                .startsWith("Search results for")
                .contains(TERM);

        then(browser.tabs())
                .as("the single tab has only the Search Results page")
                .hasSize(1)
                .doesNotContainValue(googleCloud)
                .containsValues(searchResult);
    }

}