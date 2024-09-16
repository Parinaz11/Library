package com.api.Library.Business.service;

import com.api.Library.Business.model.Admin;
import com.api.Library.Business.model.Library;

import com.api.Library.Business.model.Manager;
import com.api.Library.Business.model.User;
import com.api.Library.LibraryApplication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

//    public List<User> getUsers() {
//        return populateUsers();
//    }
//
    public Optional<User> getUserById(int id) {
        return LibraryApplication.db.getUserById(id);
    }

    public User getUserByUsername(String user_name) {
        return LibraryApplication.db.findUserByUsername(user_name);
    }

    public List<User> getUsers() {
        return LibraryApplication.db.getUsers();
    }

    public void addUser(User u) {
        LibraryApplication.db.addUser(u);
    }


}
