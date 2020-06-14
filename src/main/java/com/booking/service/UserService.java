package com.booking.service;

import com.booking.dao.DAO;
import com.booking.dao.UserDAO;
import com.booking.entity.User;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class UserService {
    DAO<User> userDAO;

    public UserService() {
        this("users.txt");
    }

    public UserService(String filename) {
        this.userDAO = new UserDAO(filename);
    }

    public Optional<User> get(int id) {
        return userDAO.get(id);
    }

    public Collection<User> getAll() {
        return userDAO.getAll();
    }

    public Collection<User> getAllBy(Predicate<User> p) {
        return userDAO.getAllBy(p);
    }

    public void create(User user) {
        userDAO.create(user);
    }

    public void delete(int id) {
        userDAO.delete(id);
    }
}
