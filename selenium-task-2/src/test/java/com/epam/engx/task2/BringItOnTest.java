package com.epam.engx.task2;

import com.epam.engx.task2.page.PastebinPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public final class BringItOnTest {
    private static final String TITLE = "how to gain dominance among developers";
    private static final String CODE = """
            git config --global user.name  "New Sheriff in Town"
            git reset $(git commit-tree HEAD^{tree} -m "Legacy code")
            git push origin master --force
            """;

    private WebDriver driver;
    private PastebinPage pastebinPage;

    @BeforeMethod(description = "Open PasteBin site")
    public void setUp() {
        driver = webDriver();
        pastebinPage = new PastebinPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test(testName = "Publish code snippets on PasteBin site")
    void postCode() {
        var response = pastebinPage.postCode(TITLE, CODE);

        assertEquals(response.title(), "Bad Request (#400)");
        assertEquals(response.text(), "Unable to verify your data submission.");
    }

    private WebDriver webDriver() {
        var prefs = Map.of("profile.default_content_setting_values.cookies", 2);
        var driver = new ChromeDriver(
                new ChromeOptions()
                        .addArguments("--incognito", "start-maximized")
                        .setExperimentalOption("prefs", prefs)
        );
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }
}
