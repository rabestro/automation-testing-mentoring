package com.epam.engx.selenium.pages.gcpc;

import com.paulhammant.ngwebdriver.ByAngular;
import org.openqa.selenium.By;

public enum Dropdown {
    OS("listingCtrl.computeServer.os"),
    PROVISIONING_MODEL("listingCtrl.computeServer.class"),
    MACHINE_FAMILY("listingCtrl.computeServer.family"),
    SERIES("listingCtrl.computeServer.series"),
    INSTANCE_TYPE("listingCtrl.computeServer.instance"),
    DATACENTER_LOCATION("listingCtrl.computeServer.location");
    private final String model;

    Dropdown(String model) {
        this.model = model;
    }

    public By locator() {
        return ByAngular.model(model);
    }

    public String model() {
        return model;
    }
}
