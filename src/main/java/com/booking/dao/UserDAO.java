package com.booking.dao;

import com.booking.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserDAO implements DAO<User> {

    private final File file;

    public UserDAO(String filename) {
        this.file = new File(filename);
    }

    @Override
    public Optional<User> get(int id) {
        return getAll().stream().filter(s -> s.getUserId() == id).findFirst();
    }

    @Override
    public Collection<User> getAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            Object read = ois.readObject();
            List<User> objects = (ArrayList<User>) read;
            return objects;
        } catch (IOException | ClassNotFoundException ex) {

            return new ArrayList<>();
        }
    }

    @Override
    public Collection<User> getAllBy(Predicate<User> p) {
        return getAll().stream().filter(p).collect(Collectors.toList());
    }

    @Override
    public void create(User user) {
        Collection<User> users = getAll();
        users.add(user);
        write(users);
    }

    @Override
    public void delete(int id) {
        Collection<User> users = getAllBy(s -> s.getUserId() != id);
        write(users);

    }

    public void write(Collection<User> user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            oos.writeObject(user);
        } catch (IOException ex) {
            throw new RuntimeException("DAO:write:IOException", ex);
        }
    }
}
