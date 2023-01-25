package com.epam.engx.selenium.pages.gcpc.component;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Factory for creating and configuring components for the calculator page
 *
 * @param root search context used to find the menu options
 * @param jsActionClick mouse click action using javascript
 */
public record ComponentFactory(
        SearchContext root,
        Consumer<WebElement> jsActionClick
) implements Function<String, Component> {

    @Override
    public Component apply(String model) {
        var element = root.findElement(new ByModel(model));

        return switch (element.getTagName().toLowerCase()) {
            case "md-select" -> new SelectComponent(root, element, jsActionClick, OptionPredicate::new);
            case "input" -> new InputComponent(element);
            case "md-checkbox" -> new CheckboxComponent(element, jsActionClick);
            default -> throw new UnsupportedOperationException(
                    "no implementation for " + element.getTagName());
        };
    }
}
