package com.gridnine.testing.services;

import com.gridnine.testing.entities.Flight;
import com.gridnine.testing.entities.Segment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@link #getDeparturesBeforeCurrentTime} - Вылет до текущего момента времени.
 * <p>
 * {@link #getArrivalsBeforeDeparture} - Имеются сегменты с датой прилёта раньше даты вылета.
 * <p>
 * {@link #getGroundedTimeMoreThanTwoHours} - общее время, проведённое на земле превышает два часа
 * (время на земле — это интервал между прилётом одного сегмента и вылетом следующего за ним).
 */
@Service
public class FlightFilterService {
    private List<Flight> flights;

    @Autowired
    public FlightFilterService(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public List<Flight> getDeparturesBeforeCurrentTime() {
        return flights.stream().filter(f -> f.getSegments().stream().anyMatch(
                s -> s.getDepartureDate().isBefore(LocalDateTime.now()))).toList();
    }

    public List<Flight> getArrivalsBeforeDeparture() {
        return flights.stream().filter((f -> f.getSegments().stream().anyMatch(
                s -> s.getArrivalDate().isBefore(s.getDepartureDate())))).toList();
    }

    public List<Flight> getGroundedTimeMoreThanTwoHours() {
        return flights.stream().filter(f -> {
            List<Segment> segments = f.getSegments();
            LocalDateTime nextDeparture, prevArrival;
            Duration timeWaiting = Duration.ZERO;
            for (int i = 1; i < segments.size(); i++) {
                nextDeparture = segments.get(i).getDepartureDate();
                prevArrival = segments.get(i - 1).getArrivalDate();
                timeWaiting = timeWaiting.plus(Duration.between(nextDeparture, prevArrival).abs());
            }
            return timeWaiting.toHours() >= 2;
        }).toList();
    }
}
