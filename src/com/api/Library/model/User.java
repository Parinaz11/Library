package com.api.Library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected int id;

    @Column(nullable = false, unique = true)
    protected String username;

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(name = "hashed_password", nullable = false)
    protected String hashedPassword;

    @Column(nullable = false)
    protected String salt;

    @Column(nullable = false)
    protected String role;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    protected Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Date updatedAt;

    private static final Logger logInfo = LoggerFactory.getLogger(User.class);

    // Default constructor for Hibernate
    public User() {
    }

    public User(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = generateSaltString();
        this.hashedPassword = hashPassword(password, this.salt.getBytes());
    }

    public User(String username, String firstName, String lastName, String email, String hashedPassword, String salt) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private String generateSaltString() {
        return Base64.getEncoder().encodeToString(generateSalt());
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("❗ Error hashing password.", e);
        }
    }

    public boolean showMenu(Scanner in) {
        return false;
    }

    // UserDetails interface implementation for Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.hashedPassword;
    }

    @Override
    public String getUsername() {
        return this.username;  // or email if needed
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Fluent setters
    public User setFullName(String fullName) {
        String[] nameParts = fullName.split(" ", 2);
        if (nameParts.length >= 2) {
            this.firstName = nameParts[0];
            this.lastName = nameParts[1];
        } else if (nameParts.length == 1) {
            this.firstName = nameParts[0];
            this.lastName = "";  // Default to an empty last name
        }
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.salt = generateSaltString();
        this.hashedPassword = hashPassword(password, this.salt.getBytes());
        return this;
    }

    protected void runFuncForCommand(int choice, Scanner in) {
        // Default or empty implementation
        System.out.println("This user does not have command options.");
    }

    public boolean verifyPassword(String password) {
        return false;
    }
}
