package com.sbgocmez.FlightAPI;

import com.sbgocmez.FlightAPI.airport.Airport;
import com.sbgocmez.FlightAPI.airport.AirportRepository;
import org.junit.jupiter.api.Assertions;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.stream.StreamSupport;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AirportRepositoryTest {
    @Autowired private AirportRepository repo;
    @Test
    public void testAddNew()
    {
        Airport airport = new Airport();
        airport.setCity("Istanbul");
        airport.setIataCode("SAW");

        Airport savedAirport = repo.save(airport);

        Assertions.assertNotNull(savedAirport);
        Assertions.assertTrue(savedAirport.getId() > 0);
    }

    @Test
    public void testListAll()
    {
        Iterable<Airport> airports = repo.findAll();
        long size = StreamSupport.stream(airports.spliterator(), false).count();
        Assertions.assertTrue(size > 0);

        for (Airport a: airports)
        {
            System.out.println(a.toString());
        }
    }

    @Test
    public void testUpdate()
    {
        // i tried to change cities named ny to nyc an dit worked
        Iterable<Airport> airports = repo.findByCity("Istanbul");
        String new_city = "İstanbul";
        for (Airport a: airports)
        {
            a.setCity(new_city);
        }

        Airport updatedAirport = repo.findByIataCode("IST");
        Assertions.assertEquals(updatedAirport.getCity(), new_city);
        Airport updatedAirport2 = repo.findByIataCode("SAW");
        Assertions.assertEquals(updatedAirport2.getCity(), new_city);
    }

    @Test
    public void testGet()
    {
        Integer aId = 5;
        Optional<Airport> airport = repo.findById(aId);
        Assertions.assertTrue(airport.isPresent());
        System.out.println(airport);
    }

    @Test
    public void testDelete()
    {
        Integer aId = 5;
        repo.deleteById(aId);
        Optional<Airport> airport = repo.findById(aId);
        Assertions.assertFalse(airport.isPresent());
    }
}
