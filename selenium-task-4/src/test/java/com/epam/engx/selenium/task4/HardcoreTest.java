package com.epam.engx.selenium.task4;

import com.epam.engx.selenium.pages.browser.Browser;
import com.epam.engx.selenium.pages.gcpc.Estimate;
import com.epam.engx.selenium.pages.gcpc.GoogleCloudPricingCalculatorPage;
import com.epam.engx.selenium.pages.google.GoogleCloudPage;
import com.epam.engx.selenium.pages.yopmail.EmailGeneratorPage;
import com.epam.engx.selenium.pages.yopmail.YopInboxPage;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.and;
import static org.assertj.core.api.BDDAssertions.then;

@SuppressWarnings("AccessStaticViaInstance")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Search for a Pricing Calculator, Estimate computer engine and Send email")
class HardcoreTest {
    private static final String TASK_COMPUTER_ENGINE_PARAMETERS = """
        quantity: '4'
        os: free
        class: regular
        series: n1
        instance: n1-standard-8
        addGPUs: 'true'
        gpuType: NVIDIA Tesla V100
        gpuCount: '1'
        ssd: 2x375
        location: Frankfurt
        cud: '1'
        """;

    private static final String FRANKFURT = "Frankfurt";
    private static final String EXPECTED_CURRENCY = "USD";
    private static final String EXPECTED_MONTHLY_COST = "1,081.20";
    private static final BigDecimal EXPECTED_COST = BigDecimal.valueOf(1081.20);

    private static Browser browser;

    private static GoogleCloudPricingCalculatorPage pricingCalculator;
    private static EmailGeneratorPage emailGeneratorPage;
    private static Estimate estimate;
    private static String randomEmailAddress;
    private static YopInboxPage yopInboxPage;

    @RegisterExtension
    @SuppressWarnings("unused")
    private final ScreenshotWatcher5 watcher =
        new ScreenshotWatcher5(browser.driver(), "target/surefire-reports");

    @BeforeAll
    static void setUp() {
        browser = Browser.create();
    }

    @AfterAll
    static void tearDown() {
        browser.quit();
    }

    @Test
    @Order(1)
    void search_for_google_cloud_platform_pricing_calculator() {
        // given
        var googleCloud = browser.go(GoogleCloudPage::new);
        var searchResults = googleCloud
            .search("Google Cloud Platform Pricing Calculator");

        browser.go(searchResults).clickFirstLink();

        // when
        pricingCalculator = browser.go(GoogleCloudPricingCalculatorPage::new);

        then(pricingCalculator.getParameters())
            .as("initial state of the parameters")
            .isNotEmpty()
            .hasSizeGreaterThan(10)
            .containsEntry("computeServer.class", "Regular")
            .containsEntry("computeServer.family", "General purpose")
            .containsEntry("computeServer.series", "E2")
            .containsEntry("computeServer.addGPUs", "false");
    }

    @Test
    @Order(2)
    void estimate_the_monthly_rent_for_a_computer_engine() {
        // when
        estimate = pricingCalculator
            .setParameters(TASK_COMPUTER_ENGINE_PARAMETERS)
            .estimate();

        then(estimate.getItems())
            .as("parameters in the Estimate result block")
            .containsEntry("Region", FRANKFURT)
            .containsEntry("Provisioning model", "Regular")
            .containsEntry("Commitment term", "1 Year")
            .containsEntry("Instance type", "n1-standard-8")
            .containsEntry("Local SSD", "2x375 GiB");

        and.then(estimate.totalEstimatedCost())
            .as("the monthly rent for configured computer engine")
            .startsWith("Total Estimated Cost:")
            .endsWith("per 1 month")
            .contains(EXPECTED_CURRENCY)
            .contains(EXPECTED_MONTHLY_COST);
    }

    @Test
    @Order(3)
    void generate_random_yopmail_email_address() {
        // when
        emailGeneratorPage = browser.addTab().go(EmailGeneratorPage::new);

        randomEmailAddress = emailGeneratorPage.randomEmailAddress();

        then(randomEmailAddress)
            .as("address ends with YopMail domain")
            .endsWith("@yopmail.com");
    }

    @Test
    @Order(5)
    void send_estimate_to_generated_email() {
        browser.switchTo(pricingCalculator).switchToCalculatorFrame();
        estimate.sendTo(randomEmailAddress);
        browser.switchTo(emailGeneratorPage);

        yopInboxPage = emailGeneratorPage.inbox();

        then(yopInboxPage.emailAddress())
            .as("our address in Inbox equals to generated email address")
            .isEqualTo(randomEmailAddress);
    }

    @Test
    @Order(6)
    void wait_for_a_new_email() {
        // when
        yopInboxPage.waitForNewEmail();

        then(yopInboxPage.mailCount())
            .as("a new mail arrived to our inbox")
            .startsWith("1");
    }

    @Test
    @Order(7)
    void read_mail_with_estimated_monthly_cost() {
        var expectedCost = Money.of(EXPECTED_COST, "USD");
        var estimatedBill = yopInboxPage.getEstimateEmail().getEstimatedBill();

        then(estimatedBill.subject())
            .isEqualTo("Google Cloud Price Estimate");

        then(estimatedBill.monthlyCost().getCurrency())
            .asString().isEqualTo("USD");

        then(estimatedBill.monthlyCost().getNumberStripped())
            .isEqualByComparingTo(EXPECTED_COST);

        then(estimatedBill.monthlyCost())
            .isEqualByComparingTo(expectedCost);
    }
}
