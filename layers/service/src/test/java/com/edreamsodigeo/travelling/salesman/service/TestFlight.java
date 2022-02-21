package com.edreamsodigeo.travelling.salesman.service;

import com.edreamsodigeo.travellingsalesman.model.Flight;

import java.time.LocalDateTime;

public class TestFlight implements Flight {

    private String source;
    private String destination;
    private Double price;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;



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
        return departureDate;
    }

    @Override
    public LocalDateTime getArrivalTime() {
        return arrivalDate;
    }

    public static final class TestFlightBuilder {
        private String source;
        private String destination;
        private Double price;
        private LocalDateTime departureDate;
        private LocalDateTime arrivalDate;

        private TestFlightBuilder() {
        }

        public static TestFlightBuilder newTestFlight() {
            return new TestFlightBuilder();
        }

        public TestFlightBuilder withSource(String source) {
            this.source = source;
            return this;
        }

        public TestFlightBuilder withDestination(String destination) {
            this.destination = destination;
            return this;
        }

        public TestFlightBuilder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public TestFlightBuilder withDepartureDate(LocalDateTime departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public TestFlightBuilder withArrivalDate(LocalDateTime arrivalDate) {
            this.arrivalDate = arrivalDate;
            return this;
        }

        public TestFlight build() {
            TestFlight testFlight = new TestFlight();
            testFlight.destination = this.destination;
            testFlight.arrivalDate = this.arrivalDate;
            testFlight.price = this.price;
            testFlight.departureDate = this.departureDate;
            testFlight.source = this.source;
            return testFlight;
        }
    }
}
