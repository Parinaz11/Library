package com.api.Library.service;

import com.api.Library.exception.ResourceNotFoundException;
import com.api.Library.exception.UserForbiddenException;
import com.api.Library.repository.UserRepository;
import com.api.Library.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class UserService implements UserServiceInterface{

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        if (userRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Requested User does not exist");
        return userRepository.findById(id).get();
    }

    public User getUserByUsername(String user_name) {
        if (userRepository.findByUsername(user_name) == null)
            throw new ResourceNotFoundException("Requested User does not exist");
        return userRepository.findByUsername(user_name);
    }

    @Transactional
    public void updateUser(User u) {
        entityManager.merge(u);
        entityManager.flush();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        user.setSalt(generateSaltString());
        user.setHashedPassword(hashPassword(user.getHashedPassword(), Base64.getDecoder().decode(user.getSalt())));

        if (user.getId() == 0) { // Check if it's a new user (id == 0 or null)
            entityManager.persist(user); // For new users, use persist
        } else {
            user = entityManager.merge(user); // For existing users, use merge
        }
        entityManager.flush();
        return user;
    }

    @Transactional
    public void deleteUserById(int id) {
        User user =  entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
            entityManager.flush();
        }
    }

    public void getUserRole(int id) {
        User user = userRepository.findById(id).get();
    }

    public String generateSaltString() {
        byte[] salt = new byte[16];
        new java.security.SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String hashPassword(String password, byte[] salt) {
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
