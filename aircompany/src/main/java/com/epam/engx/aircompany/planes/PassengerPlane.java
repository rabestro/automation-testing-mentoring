package com.epam.engx.aircompany.planes;

public record PassengerPlane(Specification specification, int passengersCapacity) implements Plane {
}
