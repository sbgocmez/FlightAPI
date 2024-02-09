package com.sbgocmez.FlightAPI.flight;

import com.sbgocmez.FlightAPI.airport.Airport;
import com.sbgocmez.FlightAPI.airport.AirportService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FlightController {
    @Autowired private FlightService service;
    @Autowired private AirportService helperService;
    @GetMapping("/flights")
    public String showFlightList(Model model)
    {
        List<Flight> listFlights = service.listAll();
        model.addAttribute("listFlights", listFlights);
        return "flights";
    }

    @GetMapping("/flights/new")
    public String showNewForm(Model model) {
        List<Airport> listAirports = helperService.listAll();
        model.addAttribute("listAirports", listAirports);

        model.addAttribute("flight", new Flight());
        return "flight_form";
    }

    @GetMapping("/flights/save")
    public String saveNewFlight(Model model) {
        List<Airport> listAirports = helperService.listAll();
        model.addAttribute("listAirports", listAirports);

        model.addAttribute("flight", new Flight());
        return "index";
    }
}
