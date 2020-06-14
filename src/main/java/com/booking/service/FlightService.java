package com.booking.service;

import com.booking.dao.DAO;
import com.booking.dao.FlightDAO;
import com.booking.entity.Flight;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class FlightService {
    private final DAO<Flight> flightDAO;

    public FlightService() {
        this("flights.txt");
    }

    public FlightService(String filename) {
        this.flightDAO = new FlightDAO(filename);
    }

    public Optional<Flight> get(int id) {
        return flightDAO.get(id);
    }

    public Collection<Flight> getAll() {
        return flightDAO.getAll();
    }

    public Collection<Flight> getAllBy(Predicate<Flight> p) {
        return flightDAO.getAllBy(p);
    }

    public void create(Flight flight) {
        flightDAO.create(flight);
    }

    public void delete(int id) {
        flightDAO.delete(id);
    }


}
