package com.epam.engx.aircompany.planes;

@FunctionalInterface
public interface Plane {
    Specification specification();

    default int maxFlightDistance() {
        return specification().maxFlightDistance();
    }

    default int maxSpeed() {
        return specification().maxSpeed();
    }

    default int maxLoadCapacity() {
        return specification().maxLoadCapacity();
    }
}
