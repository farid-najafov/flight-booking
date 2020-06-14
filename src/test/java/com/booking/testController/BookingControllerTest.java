package com.booking.testController;

import com.booking.controller.BookingController;
import com.booking.entity.Booking;
import com.booking.entity.Cities;
import com.booking.entity.Flight;
import com.booking.entity.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookingControllerTest {
    private BookingController bookingController = new BookingController("bookingControllerTest.txt");
    Flight f1 = new Flight(32, "Kiev", Cities.AMSTERDAM, "13-03-2020", 25);
    Flight f2 = new Flight(35, "Kiev", Cities.OSLO, "16-03-2020", 15);
    Flight f3 = new Flight(43, "Kiev", Cities.MADRID, "15-03-2020", 135);
    Flight f4 = new Flight(45, "Kiev", Cities.STOCKHOLM, "12-03-2020", 26);
    Booking b1 = new Booking(f1, setPassenger1());
    Booking b2 = new Booking(f2, setPassenger2());
    Booking b3 = new Booking(f3, setPassenger3());
    Booking b4 = new Booking(f4, setPassenger4());
    List<Booking> readyBookings;

    private List<Passenger> setPassenger1() {
        Passenger passenger1 = new Passenger("Samir", "Allahverdiyev");
        List<Passenger> bookingB1Passengers = new ArrayList<>();
        bookingB1Passengers.add(passenger1);
        return bookingB1Passengers;
    }

    private List<Passenger> setPassenger2() {
        Passenger passenger2 = new Passenger("Farid", "Najafov");
        List<Passenger> bookingB2Passengers = new ArrayList<>();
        bookingB2Passengers.add(passenger2);
        return bookingB2Passengers;
    }

    private List<Passenger> setPassenger3() {
        Passenger passenger3 = new Passenger("Elmar", "Memmedyarov");
        List<Passenger> bookingB3Passengers = new ArrayList<>();
        bookingB3Passengers.add(passenger3);
        return bookingB3Passengers;
    }

    private List<Passenger> setPassenger4() {
        Passenger passenger3 = new Passenger("Niyazi", "Memmedyarov");
        List<Passenger> bookingB3Passengers = new ArrayList<>();
        bookingB3Passengers.add(passenger3);
        return bookingB3Passengers;
    }

    @BeforeEach
    public void createFile() {
        List<Booking> written = new ArrayList<>(Arrays.asList(b1, b2));
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("bookingControllerTest.txt")))) {
            oos.writeObject(written);
        } catch (IOException ex) {
            throw new RuntimeException("DAO:write:IOException", ex);
        }
    }

    @Test
    public void testGetAll() {

        readyBookings= (List<Booking>) bookingController.getAll();
        assertTrue(readyBookings.contains(b1) && readyBookings.contains(b2));
    }

    @Test
    public void testGet() {
        int id = 32;
        assertEquals(Optional.of(b1), bookingController.get(id));
    }

    @Test
    public void testGetAllBy() {
        List<Booking> booking = new ArrayList<>();
        booking.add(b2);
        assertEquals(booking, bookingController.getAllBy(f -> f.getPassengers().get(0).getFirstName().equals("Farid")));
    }

    @Test
    public void testDelete() {
        int id = 35;
        bookingController.delete(id);
        readyBookings = (List<Booking>) bookingController.getAll();
        assertFalse(readyBookings.contains(b2));
    }

    @Test
    public void testCreate() {
        bookingController.create(b2);
        List<Booking> readyBookings = (List<Booking>) bookingController.getAll();
        assertTrue(readyBookings.contains(b2));

    }
}
