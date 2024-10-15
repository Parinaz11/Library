package com.api.Library.controller;

import com.api.Library.model.Reservation;
import jakarta.validation.Valid; // Correct import
import org.springframework.beans.factory.annotation.Autowired;
import com.api.Library.model.Book;
import com.api.Library.model.User;
import com.api.Library.service.BookService;
import com.api.Library.service.ReservationService;
import com.api.Library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BookService bookService;
    private final ReservationService reservationService;

    @Autowired
    public UserController(UserService us, BookService bs, ReservationService rs) {
        this.userService = us;
        this.bookService = bs;
        this.reservationService = rs;
    }

    // Get user by username, accessible to the authenticated user
    @GetMapping("/username/{username}")
    @Secured("ROLE_USER")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Show available books, accessible to authenticated users
    @GetMapping("/{id}/available-books")
    @Secured("ROLE_USER")
    public ResponseEntity<List<Book>> showAvailableBooks(@PathVariable int id) {
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookService.getAvailableBooks(), HttpStatus.OK);
    }

    // Reserve a book, accessible only to the user themselves
    @PostMapping("/{id}/reserve-book")
    @Secured("ROLE_USER")
    public ResponseEntity<String> reserveBook(@PathVariable int id, @RequestParam String bookName) {
        // Ensure the user is making the request for their own account
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        User currentUser = userService.getUserByUsername(currentUsername);

        if (currentUser == null || currentUser.getId() != id) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
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

    // View pending reservations, accessible only to the user themselves
    @Secured("ROLE_USER")
    @GetMapping("/{id}/pending-reservations")
    public ResponseEntity<List<Reservation>> viewPendingReservations(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        User currentUser = userService.getUserByUsername(currentUsername);

        if (currentUser == null || currentUser.getId() != id) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Reservation> pendingBooks = reservationService.findPendingReservationsByUserId(id);
        return new ResponseEntity<>(pendingBooks, HttpStatus.OK);
    }

    // Delete a reservation request, accessible only to the user who made the reservation
    @DeleteMapping("/{id}/delete-reservation")
    @Secured("ROLE_USER")
    public ResponseEntity<String> deleteReservationRequest(@PathVariable int id, @RequestParam String bookName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        User currentUser = userService.getUserByUsername(currentUsername);

        if (currentUser == null || currentUser.getId() != id) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Reservation reservationToDelete = reservationService.findReservationByName(bookService.findBookIdByName(bookName));
        if (reservationToDelete != null && reservationToDelete.getUserId() == id) {
            reservationService.removeReservation(reservationToDelete.getReservationId());
            return new ResponseEntity<>("Reservation deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Reservation not found or you don't have permission to delete it.", HttpStatus.NOT_FOUND);
    }

    // Show reserved books, accessible only to the user themselves
    @GetMapping("/{id}/reserved-books")
    @Secured("ROLE_USER")
    public ResponseEntity<List<Book>> showReservedBooks(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        User currentUser = userService.getUserByUsername(currentUsername);

        if (currentUser == null || currentUser.getId() != id) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(bookService.getUserReservedBooks(id), HttpStatus.OK);
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    // Get user by ID, accessible to admins only
    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create new user, accessible to admins only
    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Update user information, accessible to admins only
    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody @Valid User userDetails) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setUsername(userDetails.getUsername());
            updatedUser.setFirstName(userDetails.getFirstName());
            updatedUser.setLastName(userDetails.getLastName());
            updatedUser.setEmail(userDetails.getEmail());
            updatedUser.setRole(userDetails.getRole());
            userService.updateUser(updatedUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a user, accessible to admins only
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
