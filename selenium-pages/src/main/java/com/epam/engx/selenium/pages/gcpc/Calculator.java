package com.epam.engx.selenium.pages.gcpc;

import com.epam.engx.selenium.pages.gcpc.model.Model;

import java.util.Map;

/**
 * Public calculator API used in tests
 */
public interface Calculator {

    /**
     * Parameter model used in the calculator
     *
     * @param model - name to search for a model in the DOM
     * @return the model found by name
     */
    Model model(String model);

    /**
     * Current values of all parameters in the calculator
     *
     * @return returns a map containing the name of the models with their value
     */
    Map<String, String> parameters();

    /**
     * Estimation of the monthly rent for current parameters
     *
     * @return an estimate of the cost of the virtual machine
     */
    Estimate estimate();
}
