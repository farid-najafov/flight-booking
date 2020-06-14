package com.booking.dao;

import com.booking.entity.Booking;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookingDAO implements DAO<Booking>, Serializable {
    private static final long serialVersionUID = 1L;
    File file;

    public BookingDAO(String fileName) {
        this.file = new File(fileName);
    }

    @Override
    public Optional<Booking> get(int id) {
        return getAll().stream().filter(s -> s.getFlights().getFlightID() == id).findFirst();
    }

    @Override
    public Collection<Booking> getAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            Object read = ois.readObject();
            List<Booking> objects = (ArrayList<Booking>) read;
            return objects;
        } catch (IOException | ClassNotFoundException ex) {
//            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Collection<Booking> getAllBy(Predicate<Booking> p) {
        return getAll().stream().filter(p).collect(Collectors.toList());
    }

    @Override
    public void create(Booking booking) {
        Collection<Booking> bookings = getAll();
        bookings.add(booking);
        write(bookings);
    }

    @Override
    public void delete(int id) {
        Collection<Booking> bookings = getAllBy(s -> s.getFlights().getFlightID() != id);
        write(bookings);
    }

    private void write(Collection<Booking> bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(bookings);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("DAO:write:IOException", ex);
        }
    }
}
