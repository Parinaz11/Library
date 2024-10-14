package com.api.Library.service;

import com.api.Library.exception.ResourceNotFoundException;
import com.api.Library.exception.UserForbiddenException;
import com.api.Library.repository.UserRepository;
import com.api.Library.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getUserById(int id) {
        if (userRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Requested User does not exist");
//        return userRepository.findById(id);
        return userRepository.findById(id).get();
    }

    public User getUserByUsername(String user_name) {
        if (userRepository.findByUsername(user_name) == null) {
            throw new ResourceNotFoundException("Requested User does not exist");
        }
        return userRepository.findByUsername(user_name);
    }

    public void updateUser(User u) {
        userRepository.save(u);
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        user.setSalt(generateSaltString());
        user.setHashedPassword(hashPassword(user.getHashedPassword(), Base64.getDecoder().decode(user.getSalt())));
        return userRepository.save(user);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public void getUserRole(int id) {
        User user = userRepository.findById(id).get();
    }

    private String generateSaltString() {
        byte[] salt = new byte[16];
        new java.security.SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            return Base64.getEncoder().encodeToString(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("❗ Error hashing password.", e);
        }
    }

    public void checkRoleOfUser(int id, String role,  String errorMessage) {
        User user = userRepository.findById(id).get();
        if (userRepository.findById(id).isEmpty() || !user.getRole().equalsIgnoreCase(role))
            throw new UserForbiddenException(errorMessage);
    }




}
