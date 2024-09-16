package com.api.Library.Business.service;

import com.api.Library.Business.model.Book;
import com.api.Library.LibraryApplication;

import java.util.List;

public class BookService {

    public List<Book> getBooks() {
        return LibraryApplication.db.getBooks();
    }

    public List<Book> getAvailableBooks() {
        return LibraryApplication.db.getAvailableBooks();
    }
    public int findBookIdByName(String bookName) {
        return LibraryApplication.db.findBookIdByName(bookName);
    }

    public List<Book> getPendingBooks(int user_id) {
        return LibraryApplication.db.getPendingBooks(user_id);
    }

    public List<Book> getUserReservedBooks(int user_id) {
        return LibraryApplication.db.getReservedBooks(user_id);
    }

    public void addBook(Book b) {
        LibraryApplication.db.addBook(b);
    }

    public Book findBookById(int id){ return LibraryApplication.db.findBookById(id); }
}
