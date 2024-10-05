package com.api.Library.controller;

import com.api.Library.model.Admin;
import com.api.Library.model.Book;
import com.api.Library.service.BookService;
import com.api.Library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks_AdminExists() {
        // Arrange
        Admin admin = new Admin();
        admin.setId(1); // Assuming there's a method to set ID in the Admin class
        admin.setRole("admin");

        List<Book> books = new ArrayList<>();
        books.add(new Book("Book Title 1", "Author 1", true, 150)); // Create a Book instance
        books.add(new Book("Book Title 2", "Author 2", true, 200));

        when(userService.getUserById(1)).thenReturn(Optional.of(admin));
        when(bookService.getBooks()).thenReturn(books);

        // Act
        ResponseEntity<List<Book>> response = adminController.getAllBooks(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    @Test
    public void testGetAllBooks_AdminDoesNotExist() {
        // Arrange
        when(userService.getUserById(1)).thenReturn(Optional.empty());

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
        Book book = new Book("New Book", "New Author", true, 300);

        when(userService.getUserById(1)).thenReturn(Optional.of(admin));

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
        when(userService.getUserById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = adminController.addBook(1, book);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Only admins can add books.", response.getBody());
    }

    // Additional tests for removeBook and getAllUsers would follow a similar pattern...
}
