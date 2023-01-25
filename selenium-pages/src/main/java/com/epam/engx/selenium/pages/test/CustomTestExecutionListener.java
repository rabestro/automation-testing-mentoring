package com.epam.engx.selenium.pages.test;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

import static java.lang.System.Logger.Level.INFO;

public class CustomTestExecutionListener implements TestExecutionListener {
    private static final System.Logger LOGGER = System.getLogger(CustomTestExecutionListener.class.getName());

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        TestExecutionListener.super.executionFinished(testIdentifier, testExecutionResult);

        LOGGER.log(INFO, testIdentifier);
        LOGGER.log(INFO, testExecutionResult);
    }
}
