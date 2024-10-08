package com.api.Library.service;

import com.api.Library.repository.ReservationRepository;
import com.api.Library.repository.UserRepository;
import com.api.Library.model.User;
import com.api.Library.repository.DatabaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Optional<User> getUserById(int id) {

//        return database.getUserById(id);
        return userRepository.findById(id);
    }

    public User getUserByUsername(String user_name) {

//        return database.findUserByUsername(user_name);
        return userRepository.findByUsername(user_name);
    }

    public List<User> getUsers() {

//        return database.getUsers();
        return userRepository.findAll();
    }

    public void addUser(User u) {

//        database.addUser(u);
        userRepository.save(u);
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


}
