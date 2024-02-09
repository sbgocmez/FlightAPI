package com.sbgocmez.FlightAPI.flight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
