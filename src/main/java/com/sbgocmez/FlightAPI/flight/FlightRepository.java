package com.sbgocmez.FlightAPI.flight;

import com.sbgocmez.FlightAPI.airport.Airport;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends CrudRepository<Flight, Integer> {
    public Long countById(Integer id);
    List<Flight> findByDepartureAirport(Airport departureAirport);
    List<Flight> findByReturnAirport(Airport returnAirport);
    List<Flight> findByDepartureDate(LocalDateTime departureDate);
    List<Flight> findByReturnDate(LocalDateTime returnDate);

    List<Flight> findByDepartureDateAndReturnDate(LocalDateTime departureDate, LocalDateTime returnDate);
    List<Flight> findByDepartureAirportAndReturnAirport(Airport departureAirport, Airport returnAirport);

}
