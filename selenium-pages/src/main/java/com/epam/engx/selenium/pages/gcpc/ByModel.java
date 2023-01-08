package com.epam.engx.selenium.pages.gcpc;

import com.paulhammant.ngwebdriver.ByAngular;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Custom locator for models used at Google Cloud Pricing Calculator
 *
 */
public final class ByModel extends By {
    private final By locator;

    public ByModel(String model) {
        locator = ByAngular.model(fullName(model));
    }

    private String fullName(String model) {
        var dots = model.replaceAll("[^.]", "").length();
        return switch (dots) {
            case 0 -> "listingCtrl.computeServer." + model;
            case 1 -> "listingCtrl." + model;
            default -> model;
        };
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return  context.findElements(locator);
    }

    @Override
    public String toString() {
        return locator.toString();
    }

}
