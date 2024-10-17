package com.api.Library.service;

import com.api.Library.model.Book;

import java.util.List;

public interface BookServiceInterface {
    List<Book> getBooks();
    List<Book> getAvailableBooks();
    int findBookIdByName(String bookName);
    List<Book> getPendingBooks(int user_id);
    List<Book> getUserReservedBooks(int user_id);
    void addBook(Book b);
    Book findBookById(int id);
    List<Book> getAllBooks();
    void deleteBook(int id);
}
