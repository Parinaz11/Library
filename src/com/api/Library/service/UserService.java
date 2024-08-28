package com.api.Library.service;

import com.api.Library.model.Admin;
import com.api.Library.model.Library;

import com.api.Library.model.Manager;
import com.api.Library.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> populateUsers() {
        List<User> users = new ArrayList<>();
        // Create an com.api.Library.model.Admin instance
        Admin admin = new Admin("admin", "Jack", "Smith", "admin@gmail.com", "dotin123");
        users.add(admin);
        // Create a com.api.Library.model.Manager instance
        Manager manager = new Manager("manager", "Jane", "Smith", "manager@gmail.com", "dotin123");
        users.add(manager);
        users.add(new User("johnny", "John", "Doe", "john.doe@example.com", "password123"));
        users.add(new User("jane_smith", "Jane", "Smith", "jane.smith@example.com", "password456"));
        users.add(new User("alice_j", "Alice", "Johnson", "alice.johnson@example.com", "password789"));
        users.add(new User("bob_b", "Bob", "Brown", "bob.brown@example.com", "password101"));
        users.add(new User("charlie_d", "Charlie", "Davis", "charlie.davis@example.com", "password102"));
        users.add(new User("diana_m", "Diana", "Miller", "diana.miller@example.com", "password103"));
        users.add(new User("eve_w", "Eve", "Wilson", "eve.wilson@example.com", "password104"));
        users.add(new User("frank_m", "Frank", "Moore", "frank.moore@example.com", "password105"));
        users.add(new User("grace_t", "Grace", "Taylor", "grace.taylor@example.com", "password106"));
        users.add(new User("henry_a", "Henry", "Anderson", "henry.anderson@example.com", "password107"));

        return users;
    }

    public List<User> getUsers() {

        return populateUsers();
    }

    public Optional<User> getUserById(int id) {
        return Library.getAllUsers().stream().filter(user -> user.getId() == id).findFirst();
    }
}
