package com.api.Library.Business.service;

import com.api.Library.Business.model.Book;
import com.api.Library.Data.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final DatabaseRepository database;

    @Autowired
    public BookService(DatabaseRepository database) {
        this.database = database;
    }

    public List<Book> getBooks() {
        return database.getBooks();
    }

    public List<Book> getAvailableBooks() {
        return database.getAvailableBooks();
    }
    public int findBookIdByName(String bookName) {
        return database.findBookIdByName(bookName);
    }

    public List<Book> getPendingBooks(int user_id) {
        return database.getPendingBooks(user_id);
    }

    public List<Book> getUserReservedBooks(int user_id) {
        return database.getReservedBooks(user_id);
    }

    public void addBook(Book b) {
        database.addBook(b);
    }

    public Book findBookById(int id){ return database.findBookById(id); }
}
