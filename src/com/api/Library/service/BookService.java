package com.api.Library.service;

import com.api.Library.exception.ResourceNotFoundException;
import com.api.Library.model.Book;
import com.api.Library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class BookService implements BookServiceInterface {

    private final BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

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
        Book book = bookRepository.findByTitle(bookName);
        if (book == null) {
            throw new ResourceNotFoundException("Book not found");
        }
        return book.getId();
    }

    public List<Book> getPendingBooks(int user_id) {
        return bookRepository.findPendingBooksByUserId(user_id);
    }

    public List<Book> getUserReservedBooks(int user_id) {
        return bookRepository.findReservedBooksByUserId(user_id);
    }

    @Transactional
    public void addBook(Book b) {
        // Use EntityManager to persist the book entity
        entityManager.persist(b);
        // Flush changes to the database
        entityManager.flush();
    }

    public Book findBookById(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void deleteBook(int id) {
        Book book = findBookById(id);
        // Use EntityManager to remove the book entity
        entityManager.remove(book);
        // Optionally flush the session if needed
        entityManager.flush();
    }

    @Transactional
    public Book updateBook(int id, Book updatedBook) {
        Book existingBook = findBookById(id);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setPages(updatedBook.getPages());
        existingBook.setAvailable(updatedBook.getAvailable());

        // Use EntityManager to merge the updated entity and flush changes
        entityManager.merge(existingBook);
        entityManager.flush();
        return existingBook;
    }
}











//package com.api.Library.service;
//
//import com.api.Library.exception.ResourceNotFoundException;
//import com.api.Library.model.Book;
//import com.api.Library.repository.BookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//
//@Service
//public class BookService implements BookServiceInterface{
//
//    private final BookRepository bookRepository;
//
//    @PersistenceContext
//
//    @Autowired
//    public BookService(BookRepository bookRepository) {
//        this.bookRepository = bookRepository;
//    }
//
//    public List<Book> getBooks() {
//        return bookRepository.findAll();
//    }
//
//    public List<Book> getAvailableBooks() {
//        return bookRepository.findByAvailable(true);
//    }
//    public int findBookIdByName(String bookName) {
//        if (bookRepository.findByTitle(bookName) == null)
//            throw new ResourceNotFoundException("Book not found");
////        return userRepository.findById(id);
//        return bookRepository.findByTitle(bookName).getId();
//    }
//
//    public List<Book> getPendingBooks(int user_id) {
//        return bookRepository.findPendingBooksByUserId(user_id);
//
//    }
//
//    public List<Book> getUserReservedBooks(int user_id) {
//        return bookRepository.findReservedBooksByUserId(user_id);
//    }
//
//    @Transactional
//    public void addBook(Book b) {
//        bookRepository.save(b);
//    }
//
//    public Book findBookById(int id){
//        if (bookRepository.findById(id).isEmpty())
//            throw new ResourceNotFoundException("Book not found");
//        return bookRepository.findById(id).orElse(null);
//    }
//
//    public List<Book> getAllBooks() {
//        return bookRepository.findAll();
//    }
//
//    @Transactional
//    public void deleteBook(int id) {
//        if (bookRepository.findById(id).isEmpty()) {
//            throw new ResourceNotFoundException("Book not found");
//        }
//
//        bookRepository.deleteById(id);
//    }
//
//    @Transactional
//    public Book updateBook(int id, Book updatedBook) {
//        Book existingBook = findBookById(id);
//        existingBook.setTitle(updatedBook.getTitle());
//        existingBook.setAuthor(updatedBook.getAuthor());
//        existingBook.setPages(updatedBook.getPages());
//        existingBook.setAvailable(updatedBook.getAvailable());
//        return existingBook;
//    }
//}
