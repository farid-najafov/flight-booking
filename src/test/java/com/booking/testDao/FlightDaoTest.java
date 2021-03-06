package com.booking.testDao;

import com.booking.dao.FlightDAO;
import com.booking.entity.Cities;
import com.booking.entity.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FlightDaoTest {
    private FlightDAO flightDAO = new FlightDAO("flightDaoTest.txt");
    private Collection<Flight> expected = new ArrayList<>();
    Flight f1 = new Flight(32, "Kiev", Cities.AMSTERDAM, "13-03-2020", 25);
    Flight f2 = new Flight(35, "Kiev", Cities.OSLO, "14-03-2020", 35);
    Flight f3 = new Flight(43, "Kiev", Cities.MADRID, "15-03-2020", 135);
    List<Flight> readFlights;

    @BeforeEach
    public void createFile() {
        List<Flight> written = new ArrayList<>(Arrays.asList(f1, f2));
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("flightDaoTest.txt")))) {
            oos.writeObject(written);
        } catch (IOException ex) {
            throw new RuntimeException("DAO:write:IOException", ex);
        }
    }

    @Test
    public void testGetAll() {

        readFlights = (List<Flight>) flightDAO.getAll();
        assertTrue(readFlights.contains(f1));
    }

    @Test
    public void testGet() {
        int id = 32;
        assertEquals(Optional.of(f1), flightDAO.get(id));
    }

    @Test
    public void testCreate() {
        flightDAO.create(f3);
        readFlights = (List<Flight>) flightDAO.getAll();
        assertTrue(readFlights.contains(f3));
    }

    @Test
    public void testGetAllBy() {
        List<Flight> flight = new ArrayList<>();
        flight.add(f2);
        assertEquals(flight, flightDAO.getAllBy(f -> f.getDestination().name().equals("OSLO")));
    }

    @Test
    public void testDelete() {
        int id = 35;
        flightDAO.delete(id);
        readFlights = (List<Flight>) flightDAO.getAll();
        assertFalse(readFlights.contains(f2));
    }
}
