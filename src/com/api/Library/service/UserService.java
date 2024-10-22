package com.api.Library.service;

import com.api.Library.exception.ResourceNotFoundException;
import com.api.Library.exception.UserForbiddenException;
import com.api.Library.model.User;
import com.api.Library.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface, UserDetailsService {

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Using BCrypt

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requested User does not exist"));
    }

    public User getUserByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .orElseThrow(() -> new ResourceNotFoundException("Requested User does not exist"));
    }

    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUser(User user) {
        user.setSalt(generateSaltString());
        user.setHashedPassword(hashPassword(user.getHashedPassword(), Base64.getDecoder().decode(user.getSalt())));

        if (user.getId() == 0) {
            entityManager.persist(user);
        } else {
            user = entityManager.merge(user);
        }
        entityManager.flush();
        return user;
    }

    @Transactional
    public void deleteUserById(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
            entityManager.flush();
        }
    }

    public String generateSaltString() {
        byte[] salt = new byte[16];
        new java.security.SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String hashPassword(String password, byte[] salt) {
        return passwordEncoder.encode(password); // Use BCrypt for hashing
    }

    public void checkRoleOfUser(int id, String role, String errorMessage) {
        User user = getUserById(id);
        if (!user.getRole().equalsIgnoreCase(role)) {
            throw new UserForbiddenException(errorMessage);
        }
    }

    // Implementation of UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getHashedPassword(), // BCrypt encoded password
                user.getRole().equalsIgnoreCase("admin") ?
                        List.of(new SimpleGrantedAuthority("ROLE_ADMIN")) :
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
