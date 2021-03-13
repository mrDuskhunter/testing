package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flightList = FlightBuilder.createFlights();

        FilterFlight.filter1(flightList);
        System.out.println("\n" +
                "\nfilter2\n");
        FilterFlight.filter2(flightList);

        System.out.println("\n" +
                "\nfilter3\n");

        FilterFlight.filter3(flightList);
    }
}
