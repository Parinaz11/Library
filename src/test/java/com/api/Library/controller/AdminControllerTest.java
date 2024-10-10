package com.api.Library.controller;

import com.api.Library.model.Admin;
import com.api.Library.model.Book;
import com.api.Library.service.BookService;
import com.api.Library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminControllerTest {

    @Autowired
    private AdminController adminController;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        // Optional: Clear any existing data in services to ensure a fresh state for each test
        userService.clearUsers(); // Implement a method to clear user data
        bookService.clearBooks(); // Implement a method to clear book data
    }

    @Test
    public void testGetAllBooks_AdminExists() {
        // Arrange
        Admin admin = new Admin();
        admin.setId(1);
        admin.setRole("admin");

        userService.saveUser(admin); // Ensure your UserService has a saveUser method

        List<Book> books = new ArrayList<>();
        books.add(new Book("Book Title 1", "Author 1", true, 150));
        books.add(new Book("Book Title 2", "Author 2", true, 200));

        for (Book book : books) {
            bookService.addBook(book); // Ensure your BookService has an addBook method
        }

        // Act
        ResponseEntity<List<Book>> response = adminController.getAllBooks(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    @Test
    public void testGetAllBooks_AdminDoesNotExist() {
        // Act
        ResponseEntity<List<Book>> response = adminController.getAllBooks(1);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testAddBook_AdminExists() {
        // Arrange
        Admin admin = new Admin();
        admin.setId(1);
        admin.setRole("admin");

        userService.saveUser(admin); // Ensure your UserService has a saveUser method
        Book book = new Book("New Book", "New Author", true, 300);

        // Act
        ResponseEntity<String> response = adminController.addBook(1, book);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Book added successfully.", response.getBody());
    }

    @Test
    public void testAddBook_AdminDoesNotExist() {
        // Arrange
        Book book = new Book("New Book", "New Author", true, 300);

        // Act
        ResponseEntity<String> response = adminController.addBook(1, book);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Only admins can add books.", response.getBody());
    }

    // Additional tests for removeBook and getAllUsers would follow a similar pattern...
}
