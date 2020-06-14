package com.booking.entity;

import java.io.Serializable;
import java.util.Objects;

public class Passenger implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;

    public Passenger(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return firstName.equals(passenger.firstName) &&
                lastName.equals(passenger.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return String.format("%-3s %s\n", firstName, lastName);
    }

    public String represent() {
        return String.format("%-3s %s\n", firstName, lastName);
    }
}
