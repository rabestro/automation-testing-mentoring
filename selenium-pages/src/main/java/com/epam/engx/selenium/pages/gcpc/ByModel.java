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
public class ByModel extends By {
    private final String model;

    public ByModel(String shortModelName) {
        this.model = fullModelName(shortModelName);
    }

    private String fullModelName(String shortModelName) {
        var dots = shortModelName.replaceAll("[^.]", "").length();
        return switch (dots) {
            case 0 -> "listingCtrl.computeServer." + shortModelName;
            case 1 -> "listingCtrl." + shortModelName;
            default -> shortModelName;
        };
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        return  context.findElements(ByAngular.model(model));
    }
}
