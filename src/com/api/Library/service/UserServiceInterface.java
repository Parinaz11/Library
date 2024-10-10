package com.api.Library.service;

import com.api.Library.model.User;
import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    Optional<User> getUserById(int id);
    User getUserByUsername(String user_name);
    void updateUser(User u);
    List<User> getAllUsers();
    User saveUser(User user);
    void deleteUserById(int id);
    String generateSaltString();
    String hashPassword(String password, byte[] salt);
}
