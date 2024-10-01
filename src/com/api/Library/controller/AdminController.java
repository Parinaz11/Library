package com.api.Library.controller;

import com.api.Library.model.Admin;
import com.api.Library.model.Book;
import com.api.Library.model.Library;
import com.api.Library.model.User;
import com.api.Library.service.BookService;
import com.api.Library.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

//    private final UserService userService = new UserService(LibraryApplication.db);

    @Autowired
    private UserService userService;
    private BookService bookService;
//    private final BookService bookService = new BookService(LibraryApplication.db);

    @GetMapping("/{id}/books")
    public ResponseEntity<List<Book>> getAllBooks(@PathVariable int id) {
        Admin admin = (Admin) userService.getUserById(id).orElse(null);
        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @PostMapping("/{id}/add-book")
    public ResponseEntity<String> addBook(@PathVariable int id, @RequestBody Book book) {
        Admin admin = (Admin) userService.getUserById(id).orElse(null);
        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
            return new ResponseEntity<>("Only admins can add books.", HttpStatus.FORBIDDEN);
        }
        Library.addBook(book);
        return new ResponseEntity<>("Book added successfully.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/remove-book/{bookId}")
    public ResponseEntity<String> removeBook(@PathVariable int id, @PathVariable int bookId) {
        Admin admin = (Admin) userService.getUserById(id).orElse(null);
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

    @GetMapping("/{id}/users")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable int id) {
        Admin admin = (Admin) userService.getUserById(id).orElse(null);
        if (admin == null || !admin.getRole().equalsIgnoreCase("admin")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
}
