package com.epam.engx.selenium.pages.test;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tag("slow")
@ParameterizedTest(name = "for {0} estimated monthly cost is {2}")
@ArgumentsSource(PricingCalculatorParameters.class)
public @interface PricingCalculatorParameterizedTest {
}
