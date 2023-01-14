package com.epam.engx.selenium.pages.yopmail;

import com.epam.engx.selenium.pages.browser.Browser;
import com.epam.engx.selenium.pages.gcpc.GoogleCloudPricingCalculator;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EmailGeneratorTest {
    private static final String FRANKFURT = "Frankfurt";
    private static Browser browser;
    private static GoogleCloudPricingCalculator pricingCalculator;
    // System under test
    private static EmailGenerator emailGenerator;

    @BeforeAll
    static void setUp() {
        browser = Browser.firefox();
        pricingCalculator = browser.go(GoogleCloudPricingCalculator::new);
    }

    @AfterAll
    static void tearDown() {

//        browser.quit();
    }

    @Test
    void email() {
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
        emailGenerator = browser.addTab().go(EmailGenerator::new);
        var randomEmail = emailGenerator.email();

        then(randomEmail).endsWith("@yopmail.com");

        // when
        browser.switchTo(pricingCalculator).to();
        estimate.sendTo(randomEmail);
        browser.switchTo(emailGenerator);

        var yopInbox = emailGenerator.inbox();

        then(yopInbox.email())
                .isEqualTo(randomEmail);
    }
}