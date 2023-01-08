package com.epam.engx.selenium.pages.gcpc.model;

import com.epam.engx.selenium.pages.gcpc.Calculator;

/**
 * Interface for parameter models used in the calculator
 */
public interface Model {
    Calculator set(String value);

    String get();
}
