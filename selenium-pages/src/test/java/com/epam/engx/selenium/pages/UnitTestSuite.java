package com.epam.engx.selenium.pages;

import org.junit.platform.suite.api.*;

@Suite
@IncludeTags("fast")
@SelectPackages("com.epam.engx.selenium.pages.utils")
@SelectClasses(JavaMoneyTest.class)
@SuiteDisplayName("Unit Tests for Page Objects")
public class UnitTestSuite {
}
