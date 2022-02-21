package com.edreamsodigeo.travelling.salesman.service;

import com.edreamsodigeo.travellingsalesman.model.Flight;
import com.edreamsodigeo.travellingsalesman.store.FlightStore;

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

        List<Flight> originFlights = flightStore.getFlights(originCity,destinationCity)
                .stream()
                .sorted(Comparator.comparing(Flight::getPrice))
                .sorted(Comparator.comparing(Flight::getDepartureTime))
                .collect(Collectors.toList());


//
        return originFlights;


    }



}
