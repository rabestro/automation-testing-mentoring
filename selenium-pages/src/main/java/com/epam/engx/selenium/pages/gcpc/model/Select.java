package com.epam.engx.selenium.pages.gcpc.model;

import com.epam.engx.selenium.pages.gcpc.Calculator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("CssInvalidHtmlTagReference")
public record Select(AngularCalculator calculator, WebElement menu,
                     Function<String, Predicate<WebElement>> searchStrategy
) implements Model {

    @Override
    public Calculator set(String text) {
        calculator.click(menu);
        select(option(text));
        return calculator;
    }

    private void select(WebElement option) {
        if (notSelected(option)) {
            calculator.click(option);
        }
    }

    private boolean notSelected(WebElement option) {
        return Boolean
                .valueOf(option.getAttribute("aria-selected"))
                .equals(Boolean.FALSE);
    }

    private WebElement option(String text) {
        var areaId = menu.getAttribute("aria-owns");
        return calculator.findElement(By.id(areaId))
                .findElements(By.cssSelector("md-option"))
                .stream()
                .filter(searchStrategy().apply(text))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public String get() {
        return menu().getText();
    }
}
