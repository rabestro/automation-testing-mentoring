package com.epam.engx.aircompany;

import com.epam.engx.aircompany.models.ClassificationLevel;
import com.epam.engx.aircompany.models.MilitaryType;
import com.epam.engx.aircompany.planes.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.Comparator.comparingInt;

public class AirportTest {
    private static final List<Plane> planes = List.of(
            new PassengerPlane(new Specification("Boeing-737", 900, 12000, 60500), 164),
            new PassengerPlane(new Specification("Boeing-737-800", 940, 12300, 63870), 192),
            new PassengerPlane(new Specification("Boeing-747", 980, 16100, 70500), 242),
            new PassengerPlane(new Specification("Airbus A320", 930, 11800, 65500), 188),
            new PassengerPlane(new Specification("Airbus A330", 990, 14800, 80500), 222),
            new PassengerPlane(new Specification("Embraer 190", 870, 8100, 30800), 64),
            new PassengerPlane(new Specification("Sukhoi Superjet 100", 870, 11500, 50500), 140),
            new PassengerPlane(new Specification("Bombardier CS300", 920, 11000, 60700), 196),
            new MilitaryPlane(new Specification("B-1B Lancer", 1050, 21000, 80000), MilitaryType.BOMBER),
            new MilitaryPlane(new Specification("B-2 Spirit", 1030, 22000, 70000), MilitaryType.BOMBER),
            new MilitaryPlane(new Specification("B-52 Stratofortress", 1000, 20000, 80000), MilitaryType.BOMBER),
            new MilitaryPlane(new Specification("F-15", 1500, 12000, 10000), MilitaryType.FIGHTER),
            new MilitaryPlane(new Specification("F-22", 1550, 13000, 11000), MilitaryType.FIGHTER),
            new MilitaryPlane(new Specification("C-130 Hercules", 650, 5000, 110000), MilitaryType.TRANSPORT),
            new ExperimentalPlane(new Specification("Bell X-14", 277, 482, 500), ClassificationLevel.SECRET),
            new ExperimentalPlane(new Specification("Ryan X-13 Vertijet", 560, 307, 500), ClassificationLevel.TOP_SECRET)
    );

    private static final PassengerPlane planeWithMaxPassengerCapacity =
            new PassengerPlane(new Specification("Boeing-747", 980, 16100, 70500), 242);

    private Airport airport;

    @BeforeMethod(description = "create an airport with predefined com.epam.engx.aircompany.planes")
    void setup() {
        airport = new Airport(planes);
    }

    @Test(testName = "should return transport military com.epam.engx.aircompany.planes")
    public void shouldReturnTransportMilitaryPlanes() {
        var transportMilitaryPlanes = airport.getTransportMilitaryPlanes();

        var isAllMilitaryTransportType = transportMilitaryPlanes.stream()
                .map(MilitaryPlane::type)
                .allMatch(MilitaryType.TRANSPORT::equals);

        Assert.assertTrue(
                isAllMilitaryTransportType,
                "getTransportMilitaryPlanes should return all military transport"
        );
    }

    @Test(testName = "should return passenger plane with maximum capacity")
    public void shouldReturnPassengerPlaneWithMaxCapacity() {
        var actualPassengerPlaneWithMaxPassengersCapacity = airport.getPassengerPlaneWithMaxPassengersCapacity();

        Assert.assertEquals(
                actualPassengerPlaneWithMaxPassengersCapacity,
                planeWithMaxPassengerCapacity,
                "getPassengerPlaneWithMaxPassengersCapacity doesn't return a plain with requested specification!"
        );
    }

    @Test(testName = "should sort com.epam.engx.aircompany.planes by maximum load capacity")
    public void shouldSortByMaxLoadCapacity() {
        var expectedSortedPlanes = planes.stream()
                .sorted(comparingInt(Plane::maxLoadCapacity))
                .toList();

        var actualSortedPlanes = airport.sortByMaxLoadCapacity();

        Assert.assertEquals(
                actualSortedPlanes,
                expectedSortedPlanes,
                "sortByMaxLoadCapacity doesn't return com.epam.engx.aircompany.planes sorted by maximum load capacity"
        );
    }

    @Test(testName = "should have at least one bomber")
    public void shouldHaveAtLeastOneBomber() {
        var bomberMilitaryPlanes = airport.getBomberMilitaryPlanes();

        var hasBomberType = bomberMilitaryPlanes.stream()
                .map(MilitaryPlane::type)
                .anyMatch(MilitaryType.BOMBER::equals);

        Assert.assertTrue(
                hasBomberType,
                "getBomberMilitaryPlanes doesn't return any plane with type bomber"
        );
    }

    @Test(testName = "should not have a plane with UNCLASSIFIED level")
    public void shouldNotHaveUnclassifiedPlane() {
        var experimentalPlanes = airport.getExperimentalPlanes();

        var hasUnclassifiedPlanes = experimentalPlanes.stream()
                .map(ExperimentalPlane::classificationLevel)
                .anyMatch(ClassificationLevel.UNCLASSIFIED::equals);

        Assert.assertFalse(
                hasUnclassifiedPlanes,
                "getExperimentalPlanes return a plane with UNCLASSIFIED level"
        );
    }
}
