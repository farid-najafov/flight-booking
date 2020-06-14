package com.booking.controller;

import com.booking.entity.User;
import com.booking.service.UserService;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class UserController {
    private final UserService userService;

    public UserController() {
        this("users.txt");
    }

    public UserController(String filename) {
        this.userService = new UserService(filename);
    }

    public Optional<User> get(int id) {
        return userService.get(id);
    }

    public Collection<User> getAll() {
        return userService.getAll();
    }

    public Collection<User> getAllBy(Predicate<User> p) {
        return userService.getAllBy(p);
    }

    public void create(User user) {
        userService.create(user);
    }

    public void delete(int id) {
        userService.delete(id);
    }
}
