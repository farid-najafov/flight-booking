package com.booking.ui;

import com.booking.controller.UserController;
import com.booking.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LoginMenu {
    User user;
    UserController userController = new UserController();
    private final Scanner sc = new Scanner(System.in);

    public void showLogInMenu() {
        String a = "===============================\n" +
                "|         Booking App         |\n" +
                "===============================\n" +
                "| 1. Log IN                   |\n" +
                "| 2. Create new account       |\n" +
                "| 3. Exit                     |\n" +
                "===============================\n";
        System.out.println(a);
    }

    public void createNewAccount() {
        System.out.println("========= SignUp =========");
        System.out.println("Enter Username:");
        String username = sc.nextLine();

        System.out.print("Enter Password:");
        String password = sc.nextLine();

        user = new User(username, password);
        userController.create(user);
        System.out.println("Registration is successful!");
    }

    public void Login() throws IOException {
        System.out.println("========= SignIn =========");
        System.out.println("Enter Username:");
        String userName = sc.nextLine();
        System.out.println("Enter Password:");
        String password = sc.nextLine();
        List<User> user = (List<User>) userController.
                getAllBy(p -> p.getPassword().equals(password) && p.getUsername().
                        equals(userName));
        if (user.isEmpty()) {
            System.out.println("Invalid username or password!");
        } else {
            UserIO.userInput(user.get(0));
        }
    }
}
