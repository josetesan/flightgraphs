package com.edreamsodigeo.travellingsalesman.model;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class FlightDetails /* extends DefaultWeightedEdge implements Comparable<Weight> */{

    private final Double price;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;

    public FlightDetails() {
        price = 0d;
        departureTime = null;
        arrivalTime = null;
    }

    public FlightDetails(Double price, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDetails flightDetails = (FlightDetails) o;
        return Objects.equals(getPrice(), flightDetails.getPrice()) && Objects.equals(getDepartureTime(), flightDetails.getDepartureTime()) && Objects.equals(getArrivalTime(), flightDetails.getArrivalTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice(), getDepartureTime(), getArrivalTime());
    }


    @Override
    public String toString() {
        return String.format("%s, %s,%s", price, departureTime, arrivalTime);
    }

    public Double getWeight() {
        return 10d * price +
               1d *  ChronoUnit.HOURS.between(departureTime, arrivalTime);
    }


/*
    @Override
    public int compareTo(Weight o) {

        return Comparator.comparing(Weight::getPrice) // use smallest price as first
                .thenComparing(weight -> ChronoUnit.SECONDS.between(weight.getDepartureTime(), weight.getArrivalTime()))
                .compare(this,o); // if all are equal, or null, use default compare ( hash )
    }
 */
}
