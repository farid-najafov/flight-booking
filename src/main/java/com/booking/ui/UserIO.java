package com.booking.ui;

import com.booking.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class UserIO {
    public static void userInput(User user) throws IOException {
        Menu menu = new Menu(user);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = System.out;
        while (true) {
            Menu.showMainMenu();
            out.println("Please choose one of the options:");
            String opt = br.readLine();
            switch (opt) {
                case "1":
                    menu.onlineBoard();
                    break;
                case "2":
                    menu.showTheFlightInfo();
                    break;
                case "3":
                    menu.searchAFlight();
                    menu.bookAFlight();
                    break;
                case "4":
                    menu.cancelTheBooking();
                    break;
                case "5":
                    menu.myFlights();
                    break;
                case "6":
                    LoginIO.userInput();
                    break;
                case "7":
                    out.println("We are sorry to see you leave...");
                    System.exit(0);
                default:
                    out.println("Invalid option. Please try again!");
            }
        }
    }
}
