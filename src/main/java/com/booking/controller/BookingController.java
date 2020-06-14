package com.booking.controller;

import com.booking.entity.Booking;
import com.booking.service.BookingService;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class BookingController {

    private final BookingService bookingService;

    public BookingController() {
        this("bookings.txt");
    }

    public BookingController(String fileName) {
        this.bookingService = new BookingService(fileName);
    }

    public Optional<Booking> get(int id) {
        return bookingService.get(id);
    }

    public Collection<Booking> getAll() {
        return bookingService.getAll();
    }

    public Collection<Booking> getAllBy(Predicate<Booking> p) {
        return bookingService.getAllBy(p);
    }

    public void create(Booking booking) {
        bookingService.create(booking);
    }

    public void delete(int id) {
        bookingService.delete(id);
    }

}


