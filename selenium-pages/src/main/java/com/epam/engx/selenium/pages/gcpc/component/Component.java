package com.epam.engx.selenium.pages.gcpc.component;

/**
 * Interface for data components used in the pricing calculator
 */
public sealed interface Component permits CheckboxComponent, InputComponent, SelectComponent {
    /**
     * Set a value for a data component
     *
     * @param value to set
     */
    void set(String value);

    /**
     * Get current value of component
     *
     * @return current value of a data component
     */
    String get();
}
