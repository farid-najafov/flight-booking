package com.booking.testDao;

import com.booking.dao.UserDAO;
import com.booking.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private UserDAO userDAO = new UserDAO("UserDaoTest.txt");
    User u1 = new User("Ferid","123",123);
    User u2 = new User("Samir","231",124);
    User u3 = new User("Taleh","2345",125);
    List<User> readyUser;


    @BeforeEach
    public void createFile() {
        List<User> written = new ArrayList<>(Arrays.asList(u1, u2));
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("UserDaoTest.txt")))) {
            oos.writeObject(written);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("DAO:write:IOException", ex);
        }
    }

    @Test
    public void testGetAll() {

        readyUser = (List<User>) userDAO.getAll();
        assertTrue(readyUser.contains(u1));
    }
    @Test
    public void testGet() {
        int id = 124;
        assertEquals(Optional.of(u2), userDAO.get(id));
    }

    @Test
    public void testCreate() {
        userDAO.create(u3);
        readyUser = (List<User>) userDAO.getAll();
        assertTrue(readyUser.contains(u3));
    }

    @Test
    public void testGetAllBy() {
        List<User> user = new ArrayList<>();
        user.add(u2);
        assertEquals(user, userDAO.getAllBy(f -> f.getUsername().equals("Samir")));
    }

    @Test
    public void testDelete() {
        int id = 123;
        userDAO.delete(id);
        readyUser = (List<User>) userDAO.getAll();
        assertFalse(readyUser.contains(u1));
    }


}
