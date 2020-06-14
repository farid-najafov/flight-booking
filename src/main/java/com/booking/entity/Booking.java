package com.booking.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private Flight flight;
    private List<Passenger> passengers;

    public Booking(Flight flight, List<Passenger> passengers) {
        this.flight = flight;
        this.passengers = passengers;
    }

    public Flight getFlights() {
        return flight;
    }

    public void setFlights(Flight flight) {
        this.flight = flight;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return flight.equals(booking.flight) &&
                passengers.equals(booking.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight, passengers);
    }

    @Override
    public String toString() {
        return String.format("Flight:\nID   Source   Dest.      Departure time\n%s\nPassengers:\nName  Surname\n%s",
                flight.representShort(), passengers.stream()
                        .map(Passenger::represent).collect(Collectors.joining()));
    }
}
