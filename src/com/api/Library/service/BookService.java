package com.api.Library.service;

import com.api.Library.model.Book;
import com.api.Library.repository.DatabaseRepository;
import com.api.Library.model.Book;
import com.api.Library.repository.BookRepository;
import com.api.Library.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> getBooks() {
//        return database.getBooks();
        return bookRepository.findAll();
    }

    public List<Book> getAvailableBooks() {
//        return database.getAvailableBooks();
        return bookRepository.findByAvailable(true);
    }
    public int findBookIdByName(String bookName) {
//        return database.findBookIdByName(bookName);
        return bookRepository.findByTitle(bookName).getId();
    }

    public List<Book> getPendingBooks(int user_id) {
        return bookRepository.findPendingBooksByUserId(user_id);

    }

    public List<Book> getUserReservedBooks(int user_id) {
        return bookRepository.findReservedBooksByUserId(user_id);
    }

    public void addBook(Book b) {
        bookRepository.save(b);
    }

    public Book findBookById(int id){
//        return database.findBookById(id);
        return bookRepository.findById(id).orElse(null);
    }



    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

//    public Book saveBook(Book book) {
//        return bookRepository.save(book);
//    }

//    public Book getBookById(int id) {
//        return bookRepository.findById(id).orElse(null);
//    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }
}