package com.gridnine.testing;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class FilterFlightTest extends FilterFlight {
    static List<Flight> flightList;

    @BeforeClass
    public static void setFlightList() {
        flightList = FlightBuilder.createFlights();
    }

    @Test
    public void filter2ValidateResult() {
        //имеются сегменты с датой прилёта раньше даты вылета
        List<Flight> testListFlight = FilterFlight.filter2(flightList);

        for (Flight flight : testListFlight) {
            for (Segment segment : flight.getSegments()) {
                Assert.assertTrue(segment.getArrivalDate().isBefore(segment.getDepartureDate()));
            }
        }
    }

    @Test
    public void filter3ValidateResult() {
        /*общее время, проведённое на земле превышает два часа (время на земле — это интервал между
                                                    прилётом одного сегмента и вылетом следующего за ним)
         */
        List<Flight> testListFlight = FilterFlight.filter3(flightList);

        for (Flight flight : testListFlight) {
            int allTForEach = 0;
            for (int i = 1; i < flight.getSegments().size(); i++) {
                allTForEach += (int)ChronoUnit.MINUTES.between(flight.getSegments().get(i - 1).getArrivalDate(),
                        flight.getSegments().get(i).getDepartureDate());
            }
            Assert.assertTrue(allTForEach > 120);
        }
    }
}
