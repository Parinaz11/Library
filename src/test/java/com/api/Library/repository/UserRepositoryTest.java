package com.api.Library.repository;

import com.api.Library.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    User user;

    @BeforeEach
    void setUp() {
        user = new User("maryamM","maryam","mah","maryam.mahdizadeh@gmail.com","password123m");
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        user = null;
        userRepository.deleteAll();
    }

    @Test
    void testGetUserByUsername_Found() {
        User foundUser = userRepository.findByUsername("maryamM");
        assertEquals(foundUser.getUsername(), user.getUsername());
    }
}
