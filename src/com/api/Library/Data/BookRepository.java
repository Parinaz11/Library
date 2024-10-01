package com.api.Library.Data;

import com.api.Library.Business.model.Book;
import com.api.Library.Business.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Book findByTitle(String title);

    // Query to fetch pending books (available = true)
    @Query("SELECT b FROM Book b WHERE b.user.id = :userId AND b.available = true")
    List<Book> findPendingBooksByUserId(int userId);

    // Query to fetch reserved books (available = false)
    @Query("SELECT b FROM Book b WHERE b.user.id = :userId AND b.available = false")
    List<Book> findReservedBooksByUserId(int userId);

    List<Book> findByAvailable(boolean available);

}
