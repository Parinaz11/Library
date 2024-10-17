package com.api.Library.controller;

import com.api.Library.model.Reservation;
import com.api.Library.service.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.Library.model.Book;
import com.api.Library.model.User;
import com.api.Library.service.BookService;
import com.api.Library.service.ReservationService;
import com.api.Library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BookService bookService;
    private final ReservationService reservationService;
    private final GoogleCalendarService googleCalendarService;

    @Autowired
    public UserController(UserService us, BookService bs, ReservationService rs, GoogleCalendarService gcs) {
        this.userService = us;
        this.bookService = bs;
        this.reservationService = rs;
        this.googleCalendarService = gcs;
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/{id}/available-books")
    public ResponseEntity<List<Book>> showAvailableBooks(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(bookService.getAvailableBooks(), HttpStatus.OK);
    }

    @PostMapping("/{id}/reserve-book")
    public ResponseEntity<String> reserveBook(@PathVariable int id, @RequestParam String bookName) {
        User user = userService.getUserById(id);
        int bookId = bookService.findBookIdByName(bookName);

        String reservationResult = reservationService.reserve(bookId, id);

        try {
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = startTime.plusDays(7); // Let's assume a 7-day reservation period
            googleCalendarService.createReservationEvent(bookName, user.getUsername(), startTime, endTime);
        } catch (Exception e) {
            return new ResponseEntity<>("Reservation created, but failed to add event to Google Calendar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(reservationResult, HttpStatus.OK);
    }

    @GetMapping("/{id}/pending-reservations")
    public ResponseEntity<List<Reservation>> viewPendingReservations(@PathVariable int id) {
        User user = userService.getUserById(id);
        List<Reservation> pendingBooks = reservationService.findPendingReservationsByUserId(id);
        return new ResponseEntity<>(pendingBooks, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete-reservation")
    public ResponseEntity<String> deleteReservationRequest(@PathVariable int id, @RequestParam String bookName) {
        User user = userService.getUserById(id);
        Reservation reservationToDelete = reservationService.findReservationByName(bookService.findBookIdByName(bookName));
        reservationService.removeReservation(reservationToDelete.getReservationId(), id);
        return new ResponseEntity<>("Reservation deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/{id}/reserved-books")
    public ResponseEntity<List<Book>> showReservedBooks(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(bookService.getUserReservedBooks(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
            User updatedUser = userService.getUserById(id);
            updatedUser.setUsername(userDetails.getUsername());
            updatedUser.setFirstName(userDetails.getFirstName());
            updatedUser.setLastName(userDetails.getLastName());
            updatedUser.setEmail(userDetails.getEmail());
            updatedUser.setRole(userDetails.getRole());
            userService.updateUser(updatedUser);
            return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
