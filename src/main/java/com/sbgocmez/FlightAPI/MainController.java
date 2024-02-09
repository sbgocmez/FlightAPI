package com.sbgocmez.FlightAPI;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage()
    {
        System.out.println("Main controller is running, direct to home page.");
        return "index";
    }

}
