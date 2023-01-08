package com.epam.engx.selenium.pages.gcpc.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Wrapper over the standard WebDriver to ensure
 * correct work with the angular application
 */
interface AngularDriver {

    void click(WebElement element);

    WebElement findElement(By by);
}
