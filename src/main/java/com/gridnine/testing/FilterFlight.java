package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FilterFlight {

    public static void filter1(List<Flight> flightList) {
        //вылет до текущего момента времени

        LocalDateTime timeNow = LocalDateTime.now();
        for (Flight flight : flightList) {
            for (Segment segment : flight.getSegments()) {
                if (timeNow.isBefore(segment.getDepartureDate())) {
                    System.out.println(flight);
                    break;
                }
            }
        }
    }

    public static void filter2(List<Flight> flightList) {
        //имеются сегменты с датой прилёта раньше даты вылета

        for (Flight flight : flightList) {
            for (Segment segment : flight.getSegments()) {
                if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                    System.out.println(flight);
                    break;
                }
            }
        }
    }

    public static void filter3(List<Flight> flightList) {
        /*общее время, проведённое на земле превышает два часа (время на земле — это интервал между
                                                    прилётом одного сегмента и вылетом следующего за ним)
         */
        for (Flight flight : flightList) {
            int allTForEach = 0;
            for (int i=1;i<flight.getSegments().size();i++) {
                allTForEach += transferTime(flight.getSegments().get(i-1).getArrivalDate(),
                                            flight.getSegments().get(i).getDepartureDate());
            }
            if (allTForEach>120) {
                System.out.println(flight);
            }
        }
    }

    private static int transferTime(LocalDateTime arrival, LocalDateTime departure) {
        return (int) ChronoUnit.MINUTES.between(arrival, departure);
    }
}