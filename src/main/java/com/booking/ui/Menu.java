package com.booking.ui;

import com.booking.controller.BookingController;
import com.booking.controller.FlightController;
import com.booking.dao.DAO;
import com.booking.dao.FlightDAO;
import com.booking.entity.Booking;
import com.booking.entity.Flight;
import com.booking.entity.Passenger;
import com.booking.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private final FlightController fc = new FlightController();
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private final Scanner sc = new Scanner(System.in);
    private final PrintStream out = System.out;
    private int numOfPassengers = 0;
    private int serialNumber;
    private final User user;
    private final BookingController bc;

    public Menu(User user) {
        this.user = user;
        this.bc = new BookingController(user.getUsername() + "'s bookings" + ".txt");
    }

    public static void showMainMenu() {
        StringBuilder a = new StringBuilder();
        a.append("===================================================\n");
        a.append("|                  BOOKING APP                    |\n");
        a.append("===================================================\n");
        a.append("| 1. Online Board                                 |\n");
        a.append("| 2. Show the flight info                         |\n");
        a.append("| 3. Search and book a flight                     |\n");
        a.append("| 4. Cancel the com.booking                           |\n");
        a.append("| 5. My flights                                   |\n");
        a.append("| 6. Log out                                      |\n");
        a.append("| 7. Exit                                         |\n");
        a.append("===================================================\n");
        System.out.println(a);
    }

    public void onlineBoard() {
        out.println("ID   Source   Dest.      Departure time");
        fc.getAll().stream().map(Flight::representShort).forEach(System.out::println);
    }

    public void showTheFlightInfo() {
        System.out.println("Please enter the ID of the flight to see amount of available seats:\n" +
                "(or enter any letter to return to main menu)");
        String orElse;
        String s;
        try {
            do {
                int serialNumber = Integer.parseInt(sc.nextLine());
                out.println("ID   Source   Dest.      Departure time      Av. seats");
                orElse = "No flight with this ID is found! Please try again:\n" +
                        "(or enter any letter to return to main menu)";
                s = fc.get(serialNumber).map(Flight::represent).orElse(orElse);
                System.out.println(s);
            } while (orElse.equals(s));
        } catch (NumberFormatException ignored) {
        }
    }

    public void bookAFlight() throws IOException {
//        new BookingController(user.getUsername()+"'s bookings" +".txt");
        out.println("Would you like to book one of these flights?\n" +
                "Enter 'Yes' to book or 'No' to return to main menu");
        String option = bufferedReader.readLine();
        switch (option.trim().toUpperCase()) {
            case "YES":
                System.out.println("Please specify ID of the flight");
                serialNumber = Integer.parseInt(bufferedReader.readLine());
                fc.getAll().stream()
                        .filter(p -> p.getFlightID() == serialNumber).findFirst()//.orElseThrow(() ->new RuntimeException("notfound"));
                        .ifPresent(f -> {
                            try {
                                fillNameSurnameAndCreate(user);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                break;
            case "NO":
                break;
            default:
                out.println("Invalid option. Please try again!");
        }
    }


    public void fillNameSurnameAndCreate(User user) throws IOException {
        List<Passenger> passengerList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < numOfPassengers; i++) {
            System.out.println("Enter passenger details");
            System.out.println("Name:");
            String firstName = bufferedReader.readLine();
            System.out.println("Surname:");
            String lastName = bufferedReader.readLine();
            passengerList.add(new Passenger(firstName.trim().toUpperCase(), lastName.trim().toUpperCase()));
        }

        DAO<Flight> flightDAO = new FlightDAO("flights.txt");
        flightDAO.getAll().stream()
                .filter(p -> p.getFlightID() == serialNumber).findFirst()//.orElseThrow(() ->new RuntimeException("notfound"));
                .ifPresentOrElse(f -> bc.create(new Booking(f, passengerList)),
                        () -> new RuntimeException("There is no such a flight.You are directed to MainMenu"));
    }

    public void searchAFlight() {
        out.println("Please enter the following information:");
        while (true) {
            try {
                out.println("Destination:");
                String destination = bufferedReader.readLine();
                out.println("Date: (e.g. 12-03-2020)");
                String date = bufferedReader.readLine().trim();
                out.println("Number of people:");
                numOfPassengers = Integer.parseInt(bufferedReader.readLine());
                Collection<Flight> f = fc.getAllBy(d -> d.getDestination().toString().equals(destination.trim().toUpperCase()) &&
                        d.getDate().contains(date) &&
                        d.getAvailableSeats() >= numOfPassengers);
                if (f.isEmpty()) {
                    out.println("No flights with such details are found. Please try again:");
                } else {
                    out.println("ID   Source   Dest.      Departure time      Av. seats");
                    f.forEach(System.out::println);
                    break;
                }
            } catch (InputMismatchException | IOException | NumberFormatException e) {
                out.println("Wrong input type! Try again:");
            }
        }
    }

    public void myFlights() {
        out.println("Enter full name of one of the passengers:");
        while (true) {
            try {
                String[] nameSurname = bufferedReader.readLine().trim().toUpperCase().split(" ");
                List<Booking> bookings = bc.getAll().stream().filter(p -> p.getPassengers().contains(new Passenger(nameSurname[0], nameSurname[1]))).collect(Collectors.toList());
                if (bookings.isEmpty()) {
                    System.out.println("Incorrect passenger details! Try again.");
                } else {
                    bookings.forEach(System.out::println);
                }
                break;
            } catch (ArrayIndexOutOfBoundsException | IOException e) {
                out.println("Please enter full name of one of the passengers");
            }
        }
    }

    public void cancelTheBooking() {
        out.println("Enter the ID of the flight you want to cancel\n" +
                "(or enter 0 to go back to main menu.)");
        int del = sc.nextInt();
        if (del == 0) showMainMenu();
        bc.delete(del);
        out.println("Your flight has been cancelled.");
    }
}
