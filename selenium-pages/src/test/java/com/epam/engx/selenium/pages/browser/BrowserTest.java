package com.epam.engx.selenium.pages.browser;

import com.epam.engx.selenium.pages.gcpc.GoogleCloudPricingCalculator;
import com.epam.engx.selenium.pages.google.GoogleCloud;
import com.epam.engx.selenium.pages.google.SearchResult;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.and;
import static org.assertj.core.api.BDDAssertions.then;

@SuppressWarnings("AccessStaticViaInstance")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(10)
    void open_a_new_page_in_the_same_tab() {
        // given the browser opens the Google Cloud page
        var googleCloud = browser.go(GoogleCloud::new);

        // when we perform search without opening the new tab
        var searchResult = browser.go(googleCloud.search(TERM));

        then(searchResult)
                .as("the search produce a lot of results")
                .isInstanceOf(SearchResult.class)
                .extracting(SearchResult::links).asList()
                .hasSizeGreaterThan(10);

        and.then(browser.title())
                .as("the current page has an appropriate title")
                .startsWith("Search results for")
                .contains(TERM);

        and.then(browser.tabs())
                .as("the single tab has only the Search Results page")
                .hasSize(1)
                .doesNotContainValue(googleCloud)
                .containsValues(searchResult);
    }

    @Test
    @Order(20)
    void add_new_tab_without_opening_a_new_page() {
        // when we open a page in the browser
        var googleCloud = browser.go(GoogleCloud::new);

        then(browser.title())
                .as("the browser has a title of opened page")
                .contains("Google", "Cloud", "Computing Services");

        and.then(browser.tabs())
                .as("the browser has only one tab")
                .hasSize(1)
                .containsValue(googleCloud);

        // when we add a new tab to the browser
        browser.addTab();

        then(browser.title())
                .as("the title of new tab is empty")
                .isEmpty();

        and.then(browser.tabs())
                .as("the browser has two tabs one of them is null")
                .hasSize(2)
                .containsValues(googleCloud, null);
    }

    @Test
    @Order(30)
    void open_a_new_page_in_the_second_tab() {
        // when we open a page in the current tab
        var calculator = browser.go(GoogleCloudPricingCalculator::new);

        then(browser.title())
                .as("the title is correspond to the opened page")
                .isEqualTo("Google Cloud Pricing Calculator");

        and.then(browser.tabs())
                .hasSize(2)
                .containsValues(calculator);
    }

    @Test
    @Order(40)
    void switch_between_open_tabs_by_page_class() {
        // when we switch to a tab with Google Cloud page
        var googleCloud = browser.switchTo(GoogleCloud.class);

        then(googleCloud)
                .isInstanceOf(GoogleCloud.class);

        and.then(browser.title())
                .as("the title is correspond to the google cloud page")
                .contains("Computing Services");

        // when we switch to a tab with Pricing Calculator
        var calculator = browser.switchTo(GoogleCloudPricingCalculator.class);

        then(calculator)
                .isInstanceOf(GoogleCloudPricingCalculator.class);

        and.then(browser.title())
                .as("the title is correspond to the pricing calculator page")
                .isEqualTo("Google Cloud Pricing Calculator");
    }
}