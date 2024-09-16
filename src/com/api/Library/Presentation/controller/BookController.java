package com.api.Library.Presentation.controller;

import com.api.Library.Business.model.Book;
import com.api.Library.Business.model.Library;
import com.api.Library.Business.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService = new BookService();

//    @Autowired
//    public BookController(LibraryService libraryService) {
//        this.libraryService = libraryService;
//    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        System.out.println("getAllBooks called");
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = bookService.findBookById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Library.addBook(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        Book existingBook = bookService.findBookById(id);
        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setPages(updatedBook.getPages());
            existingBook.setAvailable(updatedBook.getAvailable());
            return new ResponseEntity<>(existingBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        Library.removeBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
