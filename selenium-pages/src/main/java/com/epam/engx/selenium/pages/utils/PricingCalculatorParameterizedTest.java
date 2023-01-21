package com.epam.engx.selenium.pages.utils;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tag("slow")
@ParameterizedTest(name = "for {0} estimated monthly cost is {2}")
@MethodSource("com.epam.engx.selenium.pages.utils.PricingCalculatorParameters#provideComputerEngineParameters")
public @interface PricingCalculatorParameterizedTest {
}
