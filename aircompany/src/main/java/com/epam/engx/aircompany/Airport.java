package com.epam.engx.aircompany;

import com.epam.engx.aircompany.planes.Plane;
import com.epam.engx.aircompany.models.MilitaryType;
import com.epam.engx.aircompany.planes.ExperimentalPlane;
import com.epam.engx.aircompany.planes.MilitaryPlane;
import com.epam.engx.aircompany.planes.PassengerPlane;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;

public record Airport(List<? extends Plane> planes) {

    public List<PassengerPlane> getPassengerPlanes() {
        return planesOf(PassengerPlane.class).toList();
    }

    public List<MilitaryPlane> getMilitaryPlanes() {
        return planesOf(MilitaryPlane.class).toList();
    }

    public List<ExperimentalPlane> getExperimentalPlanes() {
        return planesOf(ExperimentalPlane.class).toList();
    }

    private <T> Stream<T> planesOf(Class<? extends T> planeType) {
        return planes.stream().filter(planeType::isInstance).map(planeType::cast);
    }

    public PassengerPlane getPassengerPlaneWithMaxPassengersCapacity() {
        return planesOf(PassengerPlane.class)
                .max(comparingInt(PassengerPlane::passengersCapacity))
                .orElseThrow();
    }

    private List<MilitaryPlane> militaryPlanes(MilitaryType type) {
        return planesOf(MilitaryPlane.class)
                .filter(plane -> plane.type() == type)
                .toList();
    }

    public List<MilitaryPlane> getTransportMilitaryPlanes() {
        return militaryPlanes(MilitaryType.TRANSPORT);
    }

    public List<MilitaryPlane> getBomberMilitaryPlanes() {
        return militaryPlanes(MilitaryType.BOMBER);
    }

    public List<? extends Plane> sortByMaxDistance() {
        return planes.stream()
                .sorted(comparingInt(Plane::maxFlightDistance))
                .toList();
    }

    public List<? extends Plane> sortByMaxSpeed() {
        return planes.stream()
                .sorted(comparingInt(Plane::maxSpeed))
                .toList();
    }

    public List<? extends Plane> sortByMaxLoadCapacity() {
        return planes.stream()
                .sorted(comparingInt(Plane::maxLoadCapacity))
                .toList();
    }
}
