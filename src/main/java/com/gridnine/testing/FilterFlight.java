package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class FilterFlight {

    public static void printFilterList(int filterId, List<Flight> flightList) {
        switch (filterId) {
            case 1 -> filter1(flightList).forEach(System.out::println);
            //вылет до текущего момента времени
            case 2 -> filter2(flightList).forEach(System.out::println);
            //имеются сегменты с датой прилёта раньше даты вылета
            case 3 -> filter3(flightList).forEach(System.out::println);
            /*общее время, проведённое на земле превышает два часа (время на земле — это интервал между
                                                    прилётом одного сегмента и вылетом следующего за ним)
         */
            default -> throw new FilterFlightException("Wrong select filter!");
        }
    }

    // multiFiltration(2x) -> firstFilter(filterId) -> secondFilter(filterId2)
    public static void printFilterList(int filterId, int filterId2, List<Flight> flightList) {
        switch ((filterId*10)+filterId2) {
            case 12 -> filter2(filter1(flightList)).forEach(System.out::println);
            case 13 -> filter3(filter1(flightList)).forEach(System.out::println);
            case 21 -> filter1(filter2(flightList)).forEach(System.out::println);
            case 23 -> filter3(filter2(flightList)).forEach(System.out::println);
            case 31 -> filter1(filter3(flightList)).forEach(System.out::println);
            case 32 -> filter2(filter3(flightList)).forEach(System.out::println);
            default -> throw new FilterFlightException("Wrong select filter!");
        }
    }

    //multiFiltration(3x) -> firstFilter(filterId) -> secondFilter(filterId2) -> thirdFilter(filterId3)
    public static void printFilterList(int filterId, int filterId2, int filterId3, List<Flight> flightList) {
        switch ((filterId*100)+(filterId2*10)+filterId3) {
            case 123 -> filter3(filter2(filter1(flightList))).forEach(System.out::println);
            case 132 -> filter2(filter3(filter1(flightList))).forEach(System.out::println);
            case 213 -> filter3(filter1(filter2(flightList))).forEach(System.out::println);
            case 231 -> filter1(filter3(filter2(flightList))).forEach(System.out::println);
            case 312 -> filter2(filter1(filter3(flightList))).forEach(System.out::println);
            case 321 -> filter1(filter2(filter3(flightList))).forEach(System.out::println);
            default -> throw new FilterFlightException("Wrong select filter!");
        }
    }

    private static List<Flight> filter1(List<Flight> flightList) {
        //вылет до текущего момента времени

        if(flightList.isEmpty()){
            throw new FilterFlightException("filter1: FlightList is Empty!");
        }

        List<Flight> list = new ArrayList<>();
        LocalDateTime timeNow = LocalDateTime.now();

        for (Flight flight : flightList) {
            for (Segment segment : flight.getSegments()) {
                if (timeNow.isBefore(segment.getDepartureDate())) {
                    list.add(flight);
                    break;
                }
            }
        }
        return list;
    }

    protected static List<Flight> filter2(List<Flight> flightList) {
        //имеются сегменты с датой прилёта раньше даты вылета

        if(flightList.isEmpty()){
            throw new FilterFlightException("filter2: FlightList is Empty!");
        }

        List<Flight> list = new ArrayList<>();

        for (Flight flight : flightList) {
            for (Segment segment : flight.getSegments()) {
                if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                    list.add(flight);
                    break;
                }
            }
        }
        return list;
    }

    protected static List<Flight> filter3(List<Flight> flightList) {
        /*общее время, проведённое на земле превышает два часа (время на земле — это интервал между
                                                    прилётом одного сегмента и вылетом следующего за ним)
         */
        if(flightList.isEmpty()){
            throw new FilterFlightException("filter3: FlightList is Empty!");
        }

        List<Flight> list = new ArrayList<>();

        for (Flight flight : flightList) {
            int allTForEach = 0;
            for (int i = 1; i < flight.getSegments().size(); i++) {
                allTForEach += transferTime(flight.getSegments().get(i - 1).getArrivalDate(),
                        flight.getSegments().get(i).getDepartureDate());
            }
            if (allTForEach > 120) {
                list.add(flight);
            }
        }
        return list;
    }

    private static int transferTime(LocalDateTime arrival, LocalDateTime departure) {
        return (int) ChronoUnit.MINUTES.between(arrival, departure);
    }
}