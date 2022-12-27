package com.epam.engx.aircompany;

import com.epam.engx.aircompany.models.MilitaryType;
import com.epam.engx.aircompany.planes.MilitaryPlane;
import com.epam.engx.aircompany.planes.PassengerPlane;
import com.epam.engx.aircompany.planes.Plane;
import com.epam.engx.aircompany.planes.Specification;

import java.util.List;

public class Runner {
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
            new MilitaryPlane(new Specification("C-130 Hercules", 650, 5000, 110000), MilitaryType.TRANSPORT)
    );

    @SuppressWarnings("squid:S106")
    public static void main(String[] args) {
        var airport = new Airport(planes);
        var militaryAirport = new Airport(airport.getMilitaryPlanes());
        var passengerAirport = new Airport(airport.getPassengerPlanes());

        System.out.println("Military airport sorted by max distance: " + militaryAirport
                .sortByMaxDistance());

        System.out.println("Passenger airport sorted by max speed: " + passengerAirport
                .sortByMaxSpeed());

        System.out.println("Plane with max passenger capacity: " + passengerAirport.getPassengerPlaneWithMaxPassengersCapacity());
    }
}
