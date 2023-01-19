package com.epam.engx.selenium.pages.model;

import org.javamoney.moneta.Money;

public record EstimatedBill(String subject, Money monthlyCost) {
}
