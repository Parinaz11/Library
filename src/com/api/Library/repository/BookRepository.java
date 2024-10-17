package com.api.Library.repository;

import com.api.Library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByTitle(String title);

    @Query("SELECT b FROM Book b WHERE b.user.id = :userId AND b.available = true")
    List<Book> findPendingBooksByUserId(int userId);

    @Query("SELECT b FROM Book b WHERE b.user.id = :userId AND b.available = false")
    List<Book> findReservedBooksByUserId(int userId);

    @Query("SELECT b FROM Book b WHERE b.available = true")
    List<Book> findByAvailable(boolean available);

}
