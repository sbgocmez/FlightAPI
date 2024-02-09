package com.sbgocmez.FlightAPI.flight;

import com.sbgocmez.FlightAPI.airport.Airport;
import com.sbgocmez.FlightAPI.airport.AirportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class FlightController {
    @Autowired private FlightService service;
    @Autowired private AirportService helperService;
    @GetMapping("/flights")
    public String showFlightList(Model model)
    {

        List<Flight> listFlights = service.listAll();
        List<Airport> listAirports = helperService.listAll();
        model.addAttribute("listAirports", listAirports);
        model.addAttribute("listFlights", listFlights);
        return "flights";
    }

    @GetMapping("/flights/filter")
    public String filterFlightsByDate(
            @RequestParam(name = "departureDate", required = false) LocalDate departureDate,
            @RequestParam(name = "returnDate", required = false) LocalDate returnDate,
            Model model) {

        LocalDateTime departureDate2 = departureDate.atStartOfDay();
        LocalDateTime returnDate2 = returnDate.atStartOfDay();
        List<Flight> filteredFlights = service.getFlightsByDate(departureDate2, returnDate2);

        model.addAttribute("filteredFlights", filteredFlights);
        return "flights"; // Change this to your actual view name
    }
    @GetMapping("/flights/filterAirport")
    public String filterFlightsByAirport(
            @RequestParam(name = "departureAirport", required = false) Airport depart,
            @RequestParam(name = "returnAirport", required = false) Airport ret,
            Model model) {


        List<Flight> filteredFlights = service.getFlightsByAirport(depart, ret);

        model.addAttribute("listFlights", filteredFlights);
        return "flights"; // Change this to your actual view name
    }
    @GetMapping("/flights/new")
    public String showNewForm(Model model) {
        List<Airport> listAirports = helperService.listAll();
        model.addAttribute("listAirports", listAirports);
        model.addAttribute("pageTitle", "Add Flight");

        model.addAttribute("flight", new Flight());
        return "flight_form";
    }

    @PostMapping("/flights/save")
    public String saveFlight(Flight flight, RedirectAttributes ra) {
        service.save(flight);
        ra.addFlashAttribute("message", "Flight has been added successfully.");
        return "redirect:/flights";
    }

    @GetMapping("/flights/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra)
    {
        try {
            Flight flight = service.get(id);
            // dropdown icin airportlar
            List<Airport> listAirports = helperService.listAll();

            //maalesef ise yaramadi ya
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD'T'hh:mm");
            String formattedDepartureDate = flight.getDepartureDate().format(formatter);
            String formattedReturnDate = flight.getReturnDate().format(formatter);

            // date i formatladim html de daha farkli
            model.addAttribute("formattedDepartureDate", formattedDepartureDate);
            model.addAttribute("formattedReturnDate", formattedReturnDate);
            model.addAttribute("listAirports", listAirports);
            model.addAttribute("flight", flight);
            model.addAttribute("pageTitle", "Edit Flight with Id " + id.toString());

            System.out.println(formattedDepartureDate);
            System.out.println(formattedReturnDate);
            ra.addFlashAttribute("message", "The flight has been edited successfully. Id: " + id.toString());
            return "flight_form";
        } catch(FlightNotFoundException e) {
            ra.addFlashAttribute("message", "The flight has been saved successfully.");
            return "redirect:/flights";
        }
    }

    @GetMapping("/flights/delete/{id}")
    public String deleteFlight(@PathVariable("id") Integer id, RedirectAttributes ra)
    {
        try {
            service.delete(id);
            return "redirect:/flights";
        } catch(FlightNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/flights";
        }
    }


}
