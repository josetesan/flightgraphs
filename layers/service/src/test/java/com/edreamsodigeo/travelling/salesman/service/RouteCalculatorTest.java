package com.edreamsodigeo.travelling.salesman.service;


import com.edreamsodigeo.travellingsalesman.model.Flight;
import com.edreamsodigeo.travellingsalesman.model.FlightImplementation;
import com.edreamsodigeo.travellingsalesman.model.FlightDetails;
import com.edreamsodigeo.travellingsalesman.store.FlightStore;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
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
                        new FlightImplementation("BCN","NYC",550d,
                                LocalDateTime.of(2022,1,13,12,0,0),
                                LocalDateTime.of(2022,1,13,15,0,0)),
                        new FlightImplementation("BCN","ORY",100d,
                                LocalDateTime.of(2022,1,13,12,0,0),
                                LocalDateTime.of(2022,1,13,14,0,0)),
                        new FlightImplementation("ORY","NYC", 500d,
                                LocalDateTime.of(2022,1,13,15,0,0),
                                LocalDateTime.of(2022,1,13,18,0,0))
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
        public void testMediumRouting() {
                FlightStore flightStore = Mockito.mock(FlightStore.class);
                List<Flight> flightListFrom = List.of(
                        new FlightImplementation("BCN","LON",100d,
                                LocalDateTime.of(2022,1,13,12,0,0),
                                LocalDateTime.of(2022,1,13,15,0,0)),
                        new FlightImplementation("BCN","ORY",100d,
                                LocalDateTime.of(2022,1,13,12,0,0),
                                LocalDateTime.of(2022,1,13,14,0,0)),
                        new FlightImplementation("LON","NYC",500d,
                                LocalDateTime.of(2022,1,13,16,0,0),
                                LocalDateTime.of(2022,1,13,19,0,0)),
                        new FlightImplementation("ORY","NYC",500d,
                                LocalDateTime.of(2022,1,13,15,0,0),
                                LocalDateTime.of(2022,1,13,18,0,0)
                        )
                );


                when(flightStore.getFlights("BCN","NYC")).thenReturn(flightListFrom);
                RouteCalculator routeCalculator = new RouteCalculator(flightStore);
                List<Flight> flights = routeCalculator.calculate("BCN","NYC");

                Assert.assertEquals( flights.size(),2);
                Assert.assertEquals(flights.get(0).getSource(),"BCN");
                Assert.assertEquals(flights.get(0).getDestination(),"ORY");
                Assert.assertEquals(flights.get(1).getSource(),"ORY");
                Assert.assertEquals(flights.get(1).getDestination(),"NYC");

                var totalPrice = flights.stream().map(Flight::getPrice).reduce(Double::sum);

                Assert.assertEquals(totalPrice.get().doubleValue(), 600d);

                flights.forEach(System.out::println);



        }

        @Test
        public void testComplexRouting() {
                FlightStore flightStore = Mockito.mock(FlightStore.class);

                List<Flight> flightListFrom = List.of(
                        new FlightImplementation("BCN","MAD",10d,
                                LocalDateTime.of(2022,1,13,12,0,0),
                                LocalDateTime.of(2022,1,13,14,0,0)),
                        new FlightImplementation("BCN","PAR",50d,
                                LocalDateTime.of(2022,1,13,12,0,0),
                                LocalDateTime.of(2022,1,13,14,0,0)),
                        new FlightImplementation("PAR","LIS",10d,
                                LocalDateTime.of(2022,1,13,15,0,0),
                                LocalDateTime.of(2022,1,13,17,0,0)),
                        new FlightImplementation("PAR","MNL",50d,
                                LocalDateTime.of(2022,1,13,15,0,0),
                                LocalDateTime.of(2022,1,13,17,0,0)),
                        new FlightImplementation("BAR","SVQ",100d,
                                LocalDateTime.of(2022,1,13,12,0,0),
                                LocalDateTime.of(2022,1,13,14,0,0)),
                        new FlightImplementation("MNL","HND",500d,
                                LocalDateTime.of(2022,1,13,18,0,0),
                                LocalDateTime.of(2022,1,14,4,0,0)),
                        new FlightImplementation("MAD","LON",10d,
                                LocalDateTime.of(2022,1,13,15,0,0),
                                LocalDateTime.of(2022,1,13,17,0,0)),
                        new FlightImplementation("WAW","NYC",500d,
                                LocalDateTime.of(2022,1,13,21,0,0),
                                LocalDateTime.of(2022,1,14,2,0,0)),
                        new FlightImplementation("SVQ","WAW",500d,
                                LocalDateTime.of(2022,1,13,15,0,0),
                                LocalDateTime.of(2022,1,13,20,0,0)),
                        new FlightImplementation("NYC","HND",500d,
                                LocalDateTime.of(2022,1,14,3,0,0),
                                LocalDateTime.of(2022,1,14,8,0,0)));

                when(flightStore.getFlights("BCN","HND")).thenReturn(flightListFrom);

                RouteCalculator routeCalculator = new RouteCalculator(flightStore);
                List<Flight> flights = routeCalculator.calculate("BCN","HND");

                Assert.assertEquals(flights.size(),3);
                Assert.assertEquals(flights.get(0).getSource(),"BCN");
                Assert.assertEquals(flights.get(0).getDestination(),"PAR");
                Assert.assertEquals(flights.get(1).getSource(),"PAR");
                Assert.assertEquals(flights.get(1).getDestination(),"MNL");
                Assert.assertEquals(flights.get(2).getSource(),"MNL");
                Assert.assertEquals(flights.get(2).getDestination(),"HND");

                var totalPrice = flights.stream().map(Flight::getPrice).reduce(Double::sum);

                Assert.assertEquals(totalPrice.get().doubleValue(), 600d);

        }


        @Test
        public void testGraphs() {

                SimpleDirectedWeightedGraph<String, FlightDetails> simpleDirectedWeightedGraph = new SimpleDirectedWeightedGraph<String, FlightDetails>(FlightDetails.class);
                simpleDirectedWeightedGraph.addVertex("BCN");
                simpleDirectedWeightedGraph.addVertex("MAD");
                simpleDirectedWeightedGraph.addVertex("PAR");
                simpleDirectedWeightedGraph.addVertex("LIS");
                simpleDirectedWeightedGraph.addVertex("MNL");
                simpleDirectedWeightedGraph.addVertex("SVQ");
                simpleDirectedWeightedGraph.addVertex("HND");
                simpleDirectedWeightedGraph.addVertex("LON");
                simpleDirectedWeightedGraph.addVertex("WAW");
                simpleDirectedWeightedGraph.addVertex("NYC");


                simpleDirectedWeightedGraph.addEdge("BCN","MAD",  new FlightDetails(10d, LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
                simpleDirectedWeightedGraph.addEdge("BCN","PAR",  new FlightDetails(50d,LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
                simpleDirectedWeightedGraph.addEdge("BCN","SVQ",  new FlightDetails(10d,LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
                simpleDirectedWeightedGraph.addEdge("PAR","LIS",  new FlightDetails(10d,LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
                simpleDirectedWeightedGraph.addEdge("PAR","MNL",  new FlightDetails(50d,LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
                simpleDirectedWeightedGraph.addEdge("MNL","HND",  new FlightDetails(50d,LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
                simpleDirectedWeightedGraph.addEdge("MAD","LON",  new FlightDetails(10d,LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
                simpleDirectedWeightedGraph.addEdge("WAW","NYC",  new FlightDetails(50d,LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
                simpleDirectedWeightedGraph.addEdge("SVQ","WAW",  new FlightDetails(50d,LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
                simpleDirectedWeightedGraph.addEdge("NYC","HND",  new FlightDetails(50d,LocalDateTime.now(), LocalDateTime.now().plusHours(1)));

                DijkstraShortestPath<String, FlightDetails> dijkstraShortestPath = new DijkstraShortestPath<String, FlightDetails>(simpleDirectedWeightedGraph);

                var path2 = dijkstraShortestPath.getPath("BCN", "HND");

                List<String> cities2 = path2.getVertexList();

                cities2.forEach(System.out::println);

                List<FlightDetails> flightDetails = path2.getEdgeList();
                flightDetails.forEach(System.out::println);

                var list = GraphCreator.toFlightList(path2);
                list.forEach(System.out::println);


        }

}
