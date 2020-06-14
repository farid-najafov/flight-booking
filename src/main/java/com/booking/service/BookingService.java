package com.booking.service;

import com.booking.dao.BookingDAO;
import com.booking.dao.DAO;
import com.booking.entity.Booking;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class BookingService {
    private final DAO<Booking> bookingDAO;

    public BookingService() {
        this("bookings.txt");
    }

    public BookingService(String fileName) {
        this.bookingDAO = new BookingDAO(fileName);
    }

    public Optional<Booking> get(int id) {
        return bookingDAO.get(id);
    }

    public Collection<Booking> getAll() {
        return bookingDAO.getAll();
    }

    public Collection<Booking> getAllBy(Predicate<Booking> p) {
        return bookingDAO.getAllBy(p);
    }

    public void create(Booking booking) {
        bookingDAO.create(booking);
    }

    public void delete(int id) {
        bookingDAO.delete(id);
    }

}
