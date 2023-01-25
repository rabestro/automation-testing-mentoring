package com.epam.engx.selenium.pages.gcpc.component;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("CssInvalidHtmlTagReference")
public record SelectComponent(
        SearchContext root, WebElement menu,
        Consumer<WebElement> jsActionClick,
        Function<String, Predicate<WebElement>> searchStrategy
) implements Component {

    @Override
    public void set(String text) {
        jsActionClick.accept(menu);
        select(option(text));
    }

    private void select(WebElement option) {
        if (notSelected(option)) {
            jsActionClick.accept(option);
        }
    }

    private boolean notSelected(WebElement option) {
        return Boolean
                .valueOf(option.getAttribute("aria-selected"))
                .equals(Boolean.FALSE);
    }

    private WebElement option(String text) {
        var areaId = menu.getAttribute("aria-owns");
        return root.findElement(By.id(areaId))
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
