package com.edreamsodigeo.travelling.salesman.service;

import com.edreamsodigeo.travellingsalesman.model.Flight;
import com.edreamsodigeo.travellingsalesman.model.Weight;
import com.edreamsodigeo.travellingsalesman.store.FlightStore;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

public class RouteCalculator {

    @Inject
    private FlightStore flightStore;

    public RouteCalculator(FlightStore flightStore) {
        this.flightStore = flightStore;
    }

    public List<Flight> calculate(String originCity, String destinationCity) {

        List<Flight> originFlights = flightStore.getFlights(originCity,destinationCity);
        var simpleDirectedWeightedGraph = GraphCreator.from(originFlights);
        var dijkstraShortestPath = new DijkstraShortestPath<>(simpleDirectedWeightedGraph);
        var path  = dijkstraShortestPath.getPath(originCity,destinationCity);
        return GraphCreator.toFlightList(path);

    }



}
