package com.api.Library.service;

import com.api.Library.model.User;
import com.api.Library.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Spring Security password encoder
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inject password encoder

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder; // Initialize password encoder
    }

    // Fetch user by ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Fetch user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Update user details
    public void updateUser(User user) {
        userRepository.save(user);
    }

    // Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Create a new user (secured by hashing the password)
    @Transactional
    public User saveUser(User user) {
        user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword())); // Use Spring Security to hash password
        return userRepository.save(user);
    }

    // Delete a user by ID
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    // Additional methods for role and permission handling can be added here if necessary
}
