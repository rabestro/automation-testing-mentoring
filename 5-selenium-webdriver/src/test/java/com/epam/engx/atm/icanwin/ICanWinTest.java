package com.epam.engx.atm.icanwin;

import com.epam.engx.atm.icanwin.page.PastebinPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ICanWinTest {
    private static final String CODE = "Hello from WebDriver";
    private static final String TITLE = "helloweb";

    private WebDriver driver;
    private PastebinPage pastebinPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(PastebinPage.HOMEPAGE_URL);

        pastebinPage = new PastebinPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        pastebinPage.postCode(TITLE, CODE);

        assertEquals(CODE, pastebinPage.code());
        assertEquals(TITLE, pastebinPage.title());
    }

}
