package com.api.Library.repository;

import com.api.Library.model.User;
import com.api.Library.repository.ArraylistDatabase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManagementService {

    private final ArraylistDatabase<User> userRepository;

    public UserManagementService(ArraylistDatabase<User> userRepository) {
        this.userRepository = userRepository;
    }

    // Find a user by username, returning an Optional
    public Optional<User> findUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(userRepository.findUserByUsername(username));
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }

    // Add a new user, checking if they already exist
    public void addUser(User user) {
        if (user == null || findUserByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists or invalid user");
        }
        userRepository.addUser(user);
    }

    // Remove a user, ensuring they exist first
    public void removeUser(User user) {
        if (user == null || !findUserByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User does not exist or invalid user");
        }
        userRepository.removeUser(user);
    }
}
