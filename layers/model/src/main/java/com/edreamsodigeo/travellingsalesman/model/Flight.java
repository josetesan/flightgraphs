package com.edreamsodigeo.travellingsalesman.model;

import java.time.LocalDateTime;

public interface Flight {

    String getSource();

    String getDestination();

    Double getPrice();

    LocalDateTime getDepartureTime();

    LocalDateTime getArrivalTime();
}
