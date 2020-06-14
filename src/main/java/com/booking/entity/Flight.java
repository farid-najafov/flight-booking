package com.booking.entity;

import java.io.Serializable;
import java.util.Objects;

public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;

    private int flightID;
    private String source;
    private Cities destination;
    private String date;
    private int availableSeats;

    public Flight(int flightID, String source, Cities destination, String date, int availableSeats) {
        this.flightID = flightID;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.availableSeats = availableSeats;
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Cities getDestination() {
        return destination;
    }

    public void setDestination(Cities destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flightID == flight.flightID &&
                availableSeats == flight.availableSeats &&
                source.equals(flight.source) &&
                destination == flight.destination &&
                date.equals(flight.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightID, source, destination, date, availableSeats);
    }

    @Override
    public String toString() {
        return String.format("%-3s : %-5s : %-10s : %-10s : %d", flightID, source, destination, date, availableSeats);
    }

    public String representShort() {
        return String.format("%-3s : %-5s : %-10s : %-10s", flightID, source, destination, date);
    }

    public String represent() {
        return String.format("%-3s : %-5s : %-10s : %-10s : %d", flightID, source, destination, date, availableSeats);
    }
}
