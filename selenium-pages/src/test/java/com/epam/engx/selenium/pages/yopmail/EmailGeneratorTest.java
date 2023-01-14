package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Browser;
import com.epam.engx.selenium.pages.gcpc.GoogleCloudPricingCalculator;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.then;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EmailGeneratorTest {
    private static Browser browser;
    private static GoogleCloudPricingCalculator pricingCalculator;
    private static YopInbox yopInbox;

    @BeforeAll
    static void setUp() {
        browser = Browser.firefox();
        pricingCalculator = browser.go(GoogleCloudPricingCalculator::new);
    }

    @AfterAll
    static void tearDown() {
        browser.quit();
    }

    @Test
    @Order(1)
    void send_estimate_to_generated_email() {
        // given
        var estimate = pricingCalculator
                .model("computeServer.quantity").set("4")
                .model("computeServer.os").set("free")
                .model("computeServer.class").set("regular")
                .model("computeServer.series").set("n1")
                .model("computeServer.instance").set("n1-standard-8")
                .model("computeServer.addGPUs").set("true")
                .model("computeServer.gpuType").set("NVIDIA Tesla V100")
                .model("computeServer.gpuCount").set("1")
                .model("computeServer.ssd").set("2x375")
                .model("computeServer.location").set("Frankfurt")
                .model("computeServer.cud").set("1")
                .estimate();

        // when
        var emailGenerator = browser.addTab().go(EmailGenerator::new);
        var randomEmail = emailGenerator.email();
        System.out.println(randomEmail);

        then(randomEmail)
                .endsWith("@yopmail.com");

        // when
        browser.switchTo(pricingCalculator).to();
        estimate.sendTo(randomEmail);
        browser.switchTo(emailGenerator);

        yopInbox = emailGenerator.inbox();

        then(yopInbox.emailAddress())
                .isEqualTo(randomEmail);
    }

    @Test
    @Order(2)
    void wait_for_a_new_email() {
        System.out.println(yopInbox.mailCount());

        then(yopInbox.mailCount())
                .startsWith("0");

        // when
        yopInbox.waitForNewEmail();

        then(yopInbox.mailCount())
                .startsWith("1");
    }

    @Test
    @Order(3)
    void read_mail_with_estimated_monthly_cost() {
        var email = yopInbox.email();

        then(email.subject())
                .isEqualTo("Google Cloud Price Estimate");

        then(email.monthlyCost())
                .startsWith("Estimated Monthly Cost")
                .contains("USD", "1,081.20");
    }
}