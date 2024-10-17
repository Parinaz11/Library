package com.api.Library.service;

import com.api.Library.exception.ResourceNotFoundException;
import com.api.Library.model.Book;
import com.api.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookService implements BookServiceInterface{

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {

        return bookRepository.findAll();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailable(true);
    }
    public int findBookIdByName(String bookName) {
        if (bookRepository.findByTitle(bookName) == null)
            throw new ResourceNotFoundException("Book not found");
//        return userRepository.findById(id);
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
        if (bookRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Book not found");
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }

    public void deleteBook(int id) {
        if (bookRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Book not found");
        }

        bookRepository.deleteById(id);
    }

    public Book updateBook(int id, Book updatedBook) {
        Book existingBook = findBookById(id);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPages(updatedBook.getPages());
        existingBook.setAvailable(updatedBook.getAvailable());
        return existingBook;
    }
}
