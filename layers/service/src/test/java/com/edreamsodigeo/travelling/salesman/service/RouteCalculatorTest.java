package com.edreamsodigeo.travelling.salesman.service;


import com.edreamsodigeo.travellingsalesman.model.Flight;
import com.edreamsodigeo.travellingsalesman.store.FlightStore;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

public class RouteCalculatorTest {



        @Test
        public void testEasyRouting() {
                FlightStore flightStore = Mockito.mock(FlightStore.class);

                List<Flight> flightListFrom = List.of(
                        TestFlight.TestFlightBuilder.newTestFlight()
                                .withSource("BCN")
                                .withDestination("NYC")
                                .withDepartureDate(LocalDateTime.of(2022,1,13,12,00,00))
                                .withArrivalDate(LocalDateTime.of(2022,1,13,15,00,00))
                                .withPrice(550d)
                                .build(),
                        TestFlight.TestFlightBuilder.newTestFlight()
                                .withSource("BCN")
                                .withDestination("ORY")
                                .withDepartureDate(LocalDateTime.of(2022,1,13,12,00,00))
                                .withArrivalDate(LocalDateTime.of(2022,1,13,14,00,00))
                                .withPrice(100d)
                                .build(),
                        TestFlight.TestFlightBuilder.newTestFlight()
                                .withSource("ORY")
                                .withDestination("NYC")
                                .withDepartureDate(LocalDateTime.of(2022,1,13,15,00,00))
                                .withArrivalDate(LocalDateTime.of(2022,1,13,18,00,00))
                                .withPrice(500d)
                                .build());

                when(flightStore.getFlights("BCN","NYC")).thenReturn(flightListFrom);
                RouteCalculator routeCalculator = new RouteCalculator(flightStore);
                List<Flight> flights = routeCalculator.calculate("BCN","NYC");

                Assert.assertEquals( flights.size(),1);
                Assert.assertEquals(flights.get(0).getSource(),"BCN");
                Assert.assertEquals(flights.get(0).getDestination(),"NYC");
                Assert.assertEquals(flights.get(0).getPrice(),Double.valueOf(550d));


        }


        @Test
        public void testMediumRouting() {
                FlightStore flightStore = Mockito.mock(FlightStore.class);
                List<Flight> flightListFrom = List.of(
                        TestFlight.TestFlightBuilder.newTestFlight()
                                .withSource("BCN")
                                .withDestination("LON")
                                .withDepartureDate(LocalDateTime.of(2022,1,13,12,00,00))
                                .withArrivalDate(LocalDateTime.of(2022,1,13,15,00,00))
                                .withPrice(100d)
                                .build(),
                        TestFlight.TestFlightBuilder.newTestFlight()
                                .withSource("BCN")
                                .withDestination("ORY")
                                .withDepartureDate(LocalDateTime.of(2022,1,13,12,00,00))
                                .withArrivalDate(LocalDateTime.of(2022,1,13,14,00,00))
                                .withPrice(100d)
                                .build(),
                        TestFlight.TestFlightBuilder.newTestFlight()
                                .withSource("LON")
                                .withDestination("NYC")
                                .withDepartureDate(LocalDateTime.of(2022,1,13,16,00,00))
                                .withArrivalDate(LocalDateTime.of(2022,1,13,19,00,00))
                                .withPrice(500d)
                                .build(),
                        TestFlight.TestFlightBuilder.newTestFlight()
                                .withSource("ORI")
                                .withDestination("NYC")
                                .withDepartureDate(LocalDateTime.of(2022,1,13,15,00,00))
                                .withArrivalDate(LocalDateTime.of(2022,1,13,18,00,00))
                                .withPrice(500d)
                                .build()
                );


                when(flightStore.getFlights("BCN","NYC")).thenReturn(flightListFrom);
                RouteCalculator routeCalculator = new RouteCalculator(flightStore);
                List<Flight> flights = routeCalculator.calculate("BCN","NYC");

                Assert.assertEquals( flights.size(),1);
                Assert.assertEquals(flights.get(0).getSource(),"BCN");
                Assert.assertEquals(flights.get(0).getDestination(),"NYC");
                Assert.assertEquals(flights.get(0).getPrice(),Double.valueOf(550d));

        }




        @Test
        public void testComplexRouting() {
                FlightStore flightStore = Mockito.mock(FlightStore.class);
                /*

                BCN-LON, 100€, 2022-01-13 12:00, 2022-01-13 15:00
                BCN-ORY, 100€, 2022-01-13 12:00, 2022-01-13 14:00
                LON-NYC, 500€, 2022-01-13 16:00, 2022-01-13 19:00
                ORY-NYC, 500€, 2022-01-13 15:00, 2022-01-13 18:00


                when(flightStore.getFlightsFrom("BCN")).thenReturn(flightListFrom);
                when(flightStore.getFlightsTo("NYC")).thenReturn(flightListTo);

                 */
                RouteCalculator routeCalculator = new RouteCalculator(flightStore);
                List<Flight> flights = routeCalculator.calculate("BCN","NYC");

                Assert.assertEquals( flights.size(),2);

                Assert.assertEquals(flights.get(0).getSource(),"BCN");
                Assert.assertEquals(flights.get(0).getDestination(),"ORY");
                Assert.assertEquals(flights.get(0).getPrice(),Double.valueOf(100d));

                Assert.assertEquals(flights.get(0).getSource(),"ORY");
                Assert.assertEquals(flights.get(0).getDestination(),"NYC");
                Assert.assertEquals(flights.get(0).getPrice(),Double.valueOf(550d));

                Double price = flights.stream().map(Flight::getPrice).reduce(0d, Double::sum);
                Assert.assertEquals(price, Double.valueOf(600d));
        }

}
/*


        Example 2
        Input: BCN-NYC

        Storage:

        BCN-LON, 100€, 2022-01-13 12:00, 2022-01-13 15:00
        BCN-ORY, 100€, 2022-01-13 12:00, 2022-01-13 14:00
        LON-NYC, 500€, 2022-01-13 16:00, 2022-01-13 19:00
        ORY-NYC, 500€, 2022-01-13 15:00, 2022-01-13 18:00
        Output: BCN-ORY, ORY-NYC

        Example 3
        Input: BCN-HND

        Storage:

        BCN-MAD, 10€, 2022-01-13 12:00, 2022-01-13 14:00
        BCN-PAR, 50€, 2022-01-13 12:00, 2022-01-13 14:00
        PAR-LIS, 10€, 2022-01-13 15:00, 2022-01-13 17:00
        PAR-MNL, 50€, 2022-01-13 15:00, 2022-01-13 17:00
        BCN-SVQ, 100€, 2022-01-13 12:00, 2022-01-13 14:00
        MNL-HND, 500€, 2022-01-13 18:00, 2022-01-14 04:00
        MAD-LON, 10€, 2022-01-13 15:00, 2022-01-13 17:00
        WAW-NYC, 500€, 2022-01-13 21:00, 2022-01-14 02:00
        SVQ-WAW, 500€, 2022-01-13 15:00, 2022-01-13 20:00
        NYC-HND, 500€, 2022-01-14 03:00, 2022-01-14 08:00
        Output: BCN-PAR, PAR-MNL, MNL-HND



BCN -> MAD -> LON
    -> PAR -> LIS
           -> MNL -> HND
    -> SVQ -> WAW -> NYC
 */