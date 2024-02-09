package com.sbgocmez.FlightAPI;
import com.sbgocmez.FlightAPI.airport.Airport;
import com.sbgocmez.FlightAPI.airport.AirportRepository;
import com.sbgocmez.FlightAPI.flight.Flight;
import com.sbgocmez.FlightAPI.flight.FlightRepository;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.stream.StreamSupport;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class FlightRepositoryTest {
    @Autowired
    private FlightRepository repo;

    @Autowired
    private AirportRepository airportRepository;
    @Test
    public void testAddNew()
    {

        Optional<Airport> departureAirportOptional = airportRepository.findById(8);
        Optional<Airport> returnAirportOptional = airportRepository.findById(16);

        // sure that these airports already in table
        Assertions.assertTrue(departureAirportOptional.isPresent());
        Assertions.assertTrue(returnAirportOptional.isPresent());

        // Create flight
        Flight flight = new Flight();
        flight.setDepartureDate(LocalDateTime.of(2024, 10, 18, 17, 45)); // Set specific departure date
        flight.setReturnDate(LocalDateTime.of(2024, 10, 20, 8, 55)); // Set specific return date
        flight.setDepartureAirport(departureAirportOptional.get());
        flight.setReturnAirport(returnAirportOptional.get());

        // Save flight
        Flight savedFlight = repo.save(flight);

        // Assertions
        Assertions.assertNotNull(savedFlight);
        Assertions.assertTrue(savedFlight.getId() > 0);
    }

    @Test
    public void listAll()
    {
        Iterable<Flight> flights = repo.findAll();
        long size = StreamSupport.stream(flights.spliterator(), false).count();
        Assertions.assertTrue(size > 0);

        for (Flight f: flights)
        {
            System.out.println(f.toString());
        }
    }

    @Test
    public void testGetByAirports()
    {
        // i tried to change cities named ny to nyc an dit worked
        Airport depart = airportRepository.findByIataCode("JFK");
        Airport ret = airportRepository.findByIataCode("NRT");
        Iterable<Flight> flightsFromDepart = repo.findByDepartureAirport(depart);
        Iterable<Flight> flightsFromReturn = repo.findByReturnAirport(ret);

        for (Flight f: flightsFromDepart)
        {
            System.out.println(f.toString());
        }
        System.out.println("*****************");
        for (Flight f: flightsFromReturn)
        {
            System.out.println(f.toString());
        }
        System.out.println("*****************");
        Iterable<Flight> flightsBothReturnDepart = repo.findByDepartureAirportAndReturnAirport(depart, ret);
        for (Flight f: flightsBothReturnDepart)
        {
            System.out.println(f.toString());
        }
    }
    @Test
    public void testUpdate()
    {
        // jfk havaalani kapaliymis, hepsi nyc ye yonlendiriliyor
        Airport depart = airportRepository.findByIataCode("JFK");
        // sadece kalkislara baktim inisler de yapilabilir tabi
        Iterable<Flight> flightsFromDepartJFK = repo.findByDepartureAirport(depart);
        Airport new_departure = airportRepository.findByIataCode("NYC");

        for (Flight f: flightsFromDepartJFK)
        {
            System.out.println(f.toString());
            f.setDepartureAirport(new_departure);
            LocalDateTime current = f.getDepartureDate();
            // gecikmeli kalkacakmis
            LocalDateTime gecikmeliKalkis = current.plusHours(3);
            f.setDepartureDate(gecikmeliKalkis);
        }

        Airport depart2 = airportRepository.findByIataCode("NYC");
        Iterable<Flight> flightsFromDepartNYC = repo.findByDepartureAirport(depart2);

        for (Flight f: flightsFromDepartNYC)
        {
            System.out.println(f.toString());
        }
    }
}
