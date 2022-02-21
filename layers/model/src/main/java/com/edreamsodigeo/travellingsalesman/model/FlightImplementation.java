package com.edreamsodigeo.travellingsalesman.model;

import java.time.LocalDateTime;

public class FlightImplementation implements Flight{

    private final String source;
    private final String destination;
    private final Double price;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;


    public FlightImplementation(String source, String destination, Double price, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.source = source;
        this.destination = destination;
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    @Override
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s for %s from %s to %s",
                source,
                destination,
                price,
                departureTime,
                arrivalTime
                );

    }
}
