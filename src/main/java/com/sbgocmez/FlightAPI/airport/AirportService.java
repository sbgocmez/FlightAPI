package com.sbgocmez.FlightAPI.airport;

import com.sbgocmez.FlightAPI.airport.Airport;
import com.sbgocmez.FlightAPI.airport.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {
    @Autowired private AirportRepository repo;

    public List<Airport> listAll()
    {
        return (List<Airport>) repo.findAll();
    }
}
