package com.api.Library.controller;

import com.api.Library.model.Book;
import com.api.Library.model.User;
import com.api.Library.service.BookService;
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
        userService.checkRoleOfUser(id, "admin", "Only admins can get all books.");
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @PostMapping("/{id}/add-book")
    public ResponseEntity<String> addBook(@PathVariable int id, @RequestBody Book book) {
        userService.checkRoleOfUser(id, "admin", "Only admins can add a book.");
        bookService.addBook(book);
        return new ResponseEntity<>("Book added successfully.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/remove-book/{bookId}")
    public ResponseEntity<String> removeBook(@PathVariable int id, @PathVariable int bookId) {
        userService.checkRoleOfUser(id, "admin", "Only admins can remove a book.");
        bookService.deleteBook(bookId);
        return new ResponseEntity<>("Book removed successfully.",HttpStatus.OK);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable int id) {
        userService.checkRoleOfUser(id, "admin", "Only admins see list of users.");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}
