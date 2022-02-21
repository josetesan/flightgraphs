package com.edreamsodigeo.travellingsalesman.model;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Objects;

public class Weight extends DefaultWeightedEdge implements Comparable<Weight> {

    private final Double price;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;

    public Weight() {
        price = 0d;
        departureTime = null;
        arrivalTime = null;
    }

    public Weight(Double price, LocalDateTime departureTime, LocalDateTime arrivalTime) {
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
        Weight weight = (Weight) o;
        return Objects.equals(getPrice(), weight.getPrice()) && Objects.equals(getDepartureTime(), weight.getDepartureTime()) && Objects.equals(getArrivalTime(), weight.getArrivalTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice(), getDepartureTime(), getArrivalTime());
    }


    @Override
    public String toString() {
        return "Weight{" +
                "price=" + price +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }



    @Override
    public int compareTo(Weight o) {

        return Comparator.comparing(Weight::getPrice) // use smallest price as first
                .thenComparing(weight -> ChronoUnit.SECONDS.between(weight.getDepartureTime(), weight.getArrivalTime()))
                .compare(this,o); // if all are equal, or null, use default compare ( hash )
    }
}
