package com.booking.controller;

import com.booking.entity.Flight;
import com.booking.service.FlightService;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class FlightController {
    private final FlightService flightService;

    public FlightController() {
        this("flights.txt");
    }

    public FlightController(String fileName) {
        this.flightService = new FlightService(fileName);
    }

    public Optional<Flight> get(int id) {
        return flightService.get(id);
    }

    public Collection<Flight> getAll() {
        return flightService.getAll();
    }

    public Collection<Flight> getAllBy(Predicate<Flight> p) {
        return flightService.getAllBy(p);
    }

    public void create(Flight flight) {
        flightService.create(flight);
    }

    public void delete(int id) {
        flightService.delete(id);
    }

}
