package com.booking.testController;

import com.booking.controller.FlightController;
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

public class FlightControllerTest {
    private FlightController flightcontroller = new FlightController("flightControllerTest.txt");
    private Collection<Flight> expected = new ArrayList<>();
    Flight f1 = new Flight(32, "Kiev", Cities.AMSTERDAM, "13-03-2020", 25);
    Flight f2 = new Flight(35, "Kiev", Cities.OSLO, "14-03-2020", 35);
    Flight f3 = new Flight(43, "Kiev", Cities.MADRID, "15-03-2020", 135);
    List<Flight> readFlights;

    @BeforeEach
    public void createFile() {
        List<Flight> written = new ArrayList<>(Arrays.asList(f1, f2));
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("flightControllerTest.txt")))) {
            oos.writeObject(written);
        } catch (IOException ex) {
            throw new RuntimeException("DAO:write:IOException", ex);
        }
    }

    @Test
    public void testGetAll() {

        readFlights = (List<Flight>) flightcontroller.getAll();
        assertTrue(readFlights.contains(f1));
    }

    @Test
    public void testGet() {
        int id = 32;
        assertEquals(Optional.of(f1), flightcontroller.get(id));
    }

    @Test
    public void testCreate() {
        flightcontroller.create(f3);
        readFlights = (List<Flight>) flightcontroller.getAll();
        assertTrue(readFlights.contains(f3));
    }

    @Test
    public void testGetAllBy() {
        List<Flight> flight = new ArrayList<>();
        flight.add(f2);
        assertEquals(flight, this.flightcontroller.getAllBy(f -> f.getDestination().name().equals("OSLO")));
    }

    @Test
    public void testDelete() {
        int id = 35;
        flightcontroller.delete(id);
        readFlights = (List<Flight>) flightcontroller.getAll();
        assertFalse(readFlights.contains(f2));
    }
}
