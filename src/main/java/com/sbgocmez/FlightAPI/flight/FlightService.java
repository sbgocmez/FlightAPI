package com.sbgocmez.FlightAPI.flight;

import com.sbgocmez.FlightAPI.airport.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    @Autowired private FlightRepository repo;

    public List<Flight> listAll()
    {
        return (List<Flight>) repo.findAll();
    }

    public void save(Flight flight) {
        repo.save(flight);
    }

    public Flight get(Integer id) throws FlightNotFoundException
    {
        Optional<Flight> flight = repo.findById(id);
        if (flight.isPresent())
        {
            return flight.get();
        }
        throw new FlightNotFoundException("Could not find any user with Id " + id);
    }

    public void delete(Integer id) throws FlightNotFoundException
    {
        Long count = repo.countById(id);
        if (count == null || count ==0)
        {
            throw new FlightNotFoundException("Flight not found.");
        }
        repo.deleteById(id);
    }

    public List<Flight> getFlightsByAirport(Airport depart, Airport ret)
    {
        if (depart != null && ret != null) {
            // Both departure and return dates are provided
            return repo.findByDepartureAirportAndReturnAirport(depart, ret);
        }else if (depart != null) {
            // Only departure date is provided
            return repo.findByDepartureAirport(depart);
        } else if (ret != null) {
            // Only return date is provided
            return repo.findByReturnAirport(ret);
        } else {
            // Neither departure nor return date is provided, return all flights
            return listAll();
        }

    }
    public List<Flight> getFlightsByDate(LocalDateTime departureDate, LocalDateTime returnDate) {
        if (departureDate != null && returnDate != null) {
            // Both departure and return dates are provided
            return repo.findByDepartureDateAndReturnDate(departureDate, returnDate);
        } else if (departureDate != null) {
            // Only departure date is provided
            return repo.findByDepartureDate(departureDate);
        } else if (returnDate != null) {
            // Only return date is provided
            return repo.findByReturnDate(returnDate);
        } else {
            // Neither departure nor return date is provided, return all flights
            return listAll();
        }
    }
}
