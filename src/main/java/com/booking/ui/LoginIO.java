package com.booking.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginIO {
    public static void userInput() throws IOException {
        LoginMenu loginMenu = new LoginMenu();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            loginMenu.showLogInMenu();
            System.out.println("Choose one of the options:");
            String opt = br.readLine();
            switch (opt) {
                case "1":
                    loginMenu.Login();
                    break;
                case "2":
                    loginMenu.createNewAccount();
                    break;
                case "3":
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again!");
            }
        }
    }
}
