package com.api.Library.Business.service;

import com.api.Library.Business.model.Admin;
import com.api.Library.Business.model.Library;

import com.api.Library.Business.model.Manager;
import com.api.Library.Business.model.User;
import com.api.Library.Data.DatabaseRepository;
import com.api.Library.LibraryApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final DatabaseRepository database;

    @Autowired
    public UserService(DatabaseRepository database) {
        this.database = database;
    }

    public Optional<User> getUserById(int id) {
        return database.getUserById(id);
    }

    public User getUserByUsername(String user_name) {
        return database.findUserByUsername(user_name);
    }

    public List<User> getUsers() {
        return database.getUsers();
    }

    public void addUser(User u) {
        database.addUser(u);
    }


}
