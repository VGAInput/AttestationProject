package com.gridnine.testing;

import com.gridnine.testing.entities.Flight;
import com.gridnine.testing.entities.FlightBuilderFactory;
import com.gridnine.testing.services.FlightFilterService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TestingApplication {

    static List<Flight> flights = FlightBuilderFactory.createFlights();

    public static void main(String[] args) {
        SpringApplication.run(TestingApplication.class, args);

        List<Flight> getAllFlights = new FlightFilterService(flights).getFlights();
        List<Flight> getDeparturesBeforeCurrentTime = new FlightFilterService(flights).getDeparturesBeforeCurrentTime();
        List<Flight> getArrivalsBeforeDeparture = new FlightFilterService(flights).getArrivalsBeforeDeparture();
        List<Flight> getGroundedTimeMoreThanTwoHours = new FlightFilterService(flights).getGroundedTimeMoreThanTwoHours();

        System.out.printf("Список всех полётов: %s\n\n", getAllFlights);
        System.out.printf("Список полётов с вылетом до настоящего момента: %s\n\n", getDeparturesBeforeCurrentTime);
        System.out.printf("Список полётов с прилётом раньше вылета: %s\n\n", getArrivalsBeforeDeparture);
        System.out.printf("Список полётов с интервалом прилёта и вылета более двух часов: %s\n\n", getGroundedTimeMoreThanTwoHours);

    }

}
