package com.epam.tat.module4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

abstract class CalculatorTest {
    static final double DELTA = 0.000_000_000_1;

    Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @AfterEach
    public void clear() {
        calculator = null;
    }

}
