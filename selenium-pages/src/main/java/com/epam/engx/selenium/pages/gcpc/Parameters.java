package com.epam.engx.selenium.pages.gcpc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByAll;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;

record Parameters(WebDriver driver) implements Supplier<Map<String, String>> {
    private static final By PARAMETERS_LOCATOR = new ByAll(
            Stream.of("md-select", "input", "md-checkbox")
                    .map(tag -> tag + "[ng-model^='listingCtrl.']")
                    .map(By::cssSelector)
                    .toArray(By[]::new));

    @Override
    public Map<String, String> get() {
        return driver.findElements(PARAMETERS_LOCATOR)
                .stream()
                .collect(toUnmodifiableMap(this::getModel, this::getValue));
    }

    private String getModel(WebElement element) {
        return element
                .getAttribute("ng-model")
                .substring("listingCtrl.".length());
    }

    private String getValue(WebElement element) {
        return switch (element.getTagName()) {
            case "md-select" -> element.getText();
            case "input" -> element.getAttribute("value");
            case "md-checkbox" -> element.getDomAttribute("aria-checked");
            default -> "unknown";
        };
    }
}
