package com.api.Library.controller;

import com.api.Library.model.Book;
import com.api.Library.model.Library;
import com.api.Library.model.User;
import com.api.Library.service.BookService;
import com.api.Library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final UserService userService;
    private final BookService bookService;

    // Modify the constructor to include @Lazy for UserService
    @Autowired
    public AdminController(@Lazy UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/books")
    public ResponseEntity<List<Book>> getAllBooks(@PathVariable int id) {
        User admin = userService.getUserById(id).orElse(null);
        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/add-book")
    public ResponseEntity<String> addBook(@PathVariable int id, @RequestBody Book book) {
        User admin = userService.getUserById(id).orElse(null);
        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
            return new ResponseEntity<>("Only admins can add books.", HttpStatus.FORBIDDEN);
        }
        Library.addBook(book);
        return new ResponseEntity<>("Book added successfully.", HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/remove-book/{bookId}")
    public ResponseEntity<String> removeBook(@PathVariable int id, @PathVariable int bookId) {
        User admin = userService.getUserById(id).orElse(null);
        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
            return new ResponseEntity<>("Only admins can remove books.", HttpStatus.FORBIDDEN);
        }
        boolean removed = Library.removeBook(bookId);
        if (removed) {
            return new ResponseEntity<>("Book removed successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/users")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable int id) {
        User admin = userService.getUserById(id).orElse(null);
        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}
