package com.gridnine.testing;

import com.gridnine.testing.entities.Flight;
import com.gridnine.testing.entities.FlightBuilderFactory;
import com.gridnine.testing.services.FlightFilterService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestingApplicationTests {

    static List<Flight> flights  = FlightBuilderFactory.createFlights();


    @Test
    void listOfExampleFlightsIsEmptyAfterEachFilter(){
        List<Flight>testList = flights;
        assertTrue(testList.size() == flights.size());
        System.out.println(testList + "\n");

        testList = new FlightFilterService(testList).getGroundedTimeMoreThanTwoHours();
        assertTrue(testList.size() < flights.size());
        System.out.println(testList + "\n");

        testList = new FlightFilterService(testList).getArrivalsBeforeDeparture();
        System.out.println(testList + "\n");

        testList = new FlightFilterService(testList).getDeparturesBeforeCurrentTime();
        System.out.println(testList + "\n");
        assertTrue(testList.isEmpty());
    }

	@Test
	void serviceReturnsUnfilteredList() {
        List<Flight> completeList = new FlightFilterService(flights).getFlights();
        assertFalse(completeList.isEmpty());
    }
	@Test
	void serviceFiltered() {
        List<Flight> fullList = flights;
        List<Flight> filteredBeforeList = new FlightFilterService(flights).getArrivalsBeforeDeparture();
        List<Flight> filteredArrivalBeforeDepartureList = new FlightFilterService(flights).getArrivalsBeforeDeparture();
        List<Flight> filteredTwoHourSpread = new FlightFilterService(flights).getGroundedTimeMoreThanTwoHours();

        assertNotSame(fullList,filteredBeforeList);
        assertNotSame(fullList,filteredArrivalBeforeDepartureList);
        assertNotSame(fullList,filteredTwoHourSpread);
    }
	@Test
	void checkIfListsAreEquallyFiltered() {
        List<Flight> fullListFirst = new FlightFilterService(flights).getArrivalsBeforeDeparture();
        List<Flight> fullListSecond = new FlightFilterService(flights).getArrivalsBeforeDeparture();
        assertEquals(fullListFirst,fullListSecond);
    }


}
