package com.api.Library.controller;

import com.api.Library.model.Admin;
import com.api.Library.model.Book;
import com.api.Library.model.Library;
import com.api.Library.model.User;
import com.api.Library.service.BookService;
import com.api.Library.service.ReservationService;
import com.api.Library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final UserService userService;
    private final BookService bookService;

    @Autowired
    public AdminController(UserService us, BookService bs) {
        this.userService = us;
        this.bookService = bs;}

    @GetMapping("/{id}/books")
    public ResponseEntity<List<Book>> getAllBooks(@PathVariable int id) {
//        Admin admin = (Admin) userService.getUserById(id).orElse(null);
//        User admin = userService.getUserById(id).orElse(null);
//        User admin = userService.getUserById(id);
//        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
        userService.checkRoleOfUser(id, "admin", "Only admins can get all books.");
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @PostMapping("/{id}/add-book")
    public ResponseEntity<String> addBook(@PathVariable int id, @RequestBody Book book) {
//        Admin admin = (Admin) userService.getUserById(id).orElse(null);
//        User admin = userService.getUserById(id).orElse(null);
//        User admin = userService.getUserById(id);
//        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
//            return new ResponseEntity<>("Only admins can add books.", HttpStatus.FORBIDDEN);
//        }
        userService.checkRoleOfUser(id, "admin", "Only admins can add a book.");
        bookService.addBook(book);
        return new ResponseEntity<>("Book added successfully.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/remove-book/{bookId}")
    public ResponseEntity<String> removeBook(@PathVariable int id, @PathVariable int bookId) {
//        Admin admin = (Admin) userService.getUserById(id).orElse(null);
//        User admin = userService.getUserById(id).orElse(null);
//        User admin = userService.getUserById(id);
//        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
//            return new ResponseEntity<>("Only admins can remove books.", HttpStatus.FORBIDDEN);
//        }
//        boolean removed = bookService.deleteBook(bookId);
//        if (removed) {
//            return new ResponseEntity<>("Book removed successfully.", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
//        }
        userService.checkRoleOfUser(id, "admin", "Only admins can remove a book.");
        bookService.deleteBook(bookId);
        return new ResponseEntity<>("Book removed successfully.",HttpStatus.OK);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable int id) {
//        Admin admin = (Admin) userService.getUserById(id).orElse(null);
//        User admin = userService.getUserById(id).orElse(null);
//        User admin = userService.getUserById(id);
//        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
        userService.checkRoleOfUser(id, "admin", "Only admins see list of users.");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}
