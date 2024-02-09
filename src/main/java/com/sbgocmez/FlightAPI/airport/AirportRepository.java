package com.sbgocmez.FlightAPI.airport;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AirportRepository extends CrudRepository<Airport, Integer> {
    List<Airport> findByCity(String city);
    Airport findByIataCode(String iata);
}
