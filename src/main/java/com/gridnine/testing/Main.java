package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flightList = FlightBuilder.createFlights();

        for (int i = 1; i < 4; i++) {
            System.out.println("Тест фильтра номер " + i + ":\n");
            FilterFlight.printFilterList(i, flightList);
            System.out.println("\nТест фильтра номер " + i + " окончен\n-\n");
        }
    }
}
