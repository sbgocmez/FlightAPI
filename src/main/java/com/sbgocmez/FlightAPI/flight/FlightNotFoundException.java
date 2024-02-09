package com.sbgocmez.FlightAPI.flight;

public class FlightNotFoundException extends Throwable {
    public FlightNotFoundException(String message)
    {
        super(message);
    }
}
