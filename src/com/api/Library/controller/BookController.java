package com.api.Library.controller;

import com.api.Library.model.Book;
import com.api.Library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        System.out.println("getAllBooks called");
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        return new ResponseEntity<>(bookService.updateBook(id, updatedBook), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/pending/{userId}")
    public List<Book> getPendingBooks(@PathVariable int userId) {

        return bookService.getPendingBooks(userId);
    }

    @GetMapping("/reserved/{userId}")
    public List<Book> getReservedBooks(@PathVariable int userId) {

        return bookService.getUserReservedBooks(userId);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        bookService.addBook(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

}
