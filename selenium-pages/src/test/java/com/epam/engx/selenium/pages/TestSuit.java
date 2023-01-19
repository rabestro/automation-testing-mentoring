package com.epam.engx.selenium.pages;

import lombok.Data;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

@Data
public class TestSuit {
    List<TestCase> tests;

    public Stream<Arguments> stream() {
        return tests.stream().map(TestCase::arguments);
    }
}
