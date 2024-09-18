package com.api.Library.Presentation.controller;

import com.api.Library.Business.model.Book;
import com.api.Library.Business.model.Reservation;
import com.api.Library.Business.model.User;
import com.api.Library.Business.service.BookService;
import com.api.Library.Business.service.ReservationService;
import com.api.Library.Business.service.UserService;
import com.api.Library.LibraryApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService = new UserService(LibraryApplication.db);
    private final BookService bookService = new BookService(LibraryApplication.db);
    private final ReservationService reservationService = new ReservationService(LibraryApplication.db);

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.addUser(user);
        System.out.println("User " + user.getName() + " added.");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Updated path for getting user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Path for getting user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // New endpoint to show available books
    @GetMapping("/{id}/available-books")
    public ResponseEntity<List<Book>> showAvailableBooks(@PathVariable int id) {
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookService.getAvailableBooks(), HttpStatus.OK);
    }

    // New endpoint to request a book reservation
    @PostMapping("/{id}/reserve-book")
    public ResponseEntity<String> reserveBook(@PathVariable int id, @RequestParam String bookName) {
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        int bookId = bookService.findBookIdByName(bookName);
        if (bookId == -1) {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
        boolean reserveStatus = reservationService.reserve(bookId, id);
        if (reserveStatus) {
            return new ResponseEntity<>("Reservation request successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reservation request failed. The book is already reserved.", HttpStatus.BAD_REQUEST);
        }
    }

    // New endpoint to view pending reservation books
    @GetMapping("/{id}/pending-reservations")
    public ResponseEntity<List<Book>> viewPendingReservations(@PathVariable int id) {
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Book> pendingBooks = bookService.getPendingBooks(id);
        return new ResponseEntity<>(pendingBooks, HttpStatus.OK);
    }

    // New endpoint to delete a reservation request
    @DeleteMapping("/{id}/delete-reservation")
    public ResponseEntity<String> deleteReservationRequest(@PathVariable int id, @RequestParam String bookName) {
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        Reservation reservationToDelete = reservationService.findReservationByName(bookName);
        if (reservationToDelete != null && reservationToDelete.getUserId() == id) {
            reservationService.removeReservation(reservationToDelete);
            return new ResponseEntity<>("Reservation deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Reservation not found or you don't have permission to delete it.", HttpStatus.NOT_FOUND);
    }

    // New endpoint to show reserved books
    @GetMapping("/{id}/reserved-books")
    public ResponseEntity<List<Book>> showReservedBooks(@PathVariable int id) {
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookService.getUserReservedBooks(id), HttpStatus.OK);
    }
}
