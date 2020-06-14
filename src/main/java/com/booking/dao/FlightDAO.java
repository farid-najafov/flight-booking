package com.booking.dao;

import com.booking.entity.Cities;
import com.booking.entity.Flight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlightDAO implements DAO<Flight> {

    private final File file;// = new File("flights.txt");

    public FlightDAO(String filename) {
        this.file = new File(filename);
    }

    @Override
    public Optional<Flight> get(int id) {
        return getAll().stream().filter(s -> s.getFlightID() == id).findFirst();
    }

    @Override
    public Collection<Flight> getAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            Object read = ois.readObject();
            List<Flight> objects = (ArrayList<Flight>) read;
            return objects;
        } catch (IOException | ClassNotFoundException ex) {
            return generateFlights(20);
        }
    }

    @Override
    public Collection<Flight> getAllBy(Predicate<Flight> p) {
        return getAll().stream().filter(p).collect(Collectors.toList());
    }

    @Override
    public void create(Flight flight) {
        Collection<Flight> flights = getAll();
        flights.add(flight);
        write(flights);
    }

    @Override
    public void delete(int id) {
        Collection<Flight> flights = getAllBy(s -> s.getFlightID() != id);
        write(flights);

    }

    private void write(Collection<Flight> flightList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(flightList);
        } catch (IOException ex) {
            throw new RuntimeException("DAO:write:IOException", ex);
        }
    }

    public Collection<Flight> generateFlights(int numOfFlights) {
        List<Flight> flightList = new ArrayList<>();
        IntStream.range(0, numOfFlights).map(i -> (int) (Math.random() * 900) + 100).forEach(flightID -> {
            String source = "KIEV";
            Cities destination = Cities.values()[(int) (Math.random() * Cities.values().length)];
            String date = LocalDateTime.now()
                    .plusHours((int) (Math.random() * 72 + 1))
                    .plusMinutes((int) (Math.random() * 60 + 1))
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            int availableSeats = (int) (Math.random() * 300);
            flightList.add(new Flight(flightID, source, destination, date, availableSeats));
        });
        write(flightList);
        return flightList;
    }
}
