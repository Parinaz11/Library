package com.api.Library.Data;

import com.api.Library.Business.model.Book;
import com.api.Library.Business.model.Reservation;
import com.api.Library.Business.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DatabaseRepository {
    List<Book> getBooks();
    List<User> getUsers();
    List<Reservation> getReservations();
    void addUser(User user);
    void removeBook(Book book);
    void addBook(Book book);
    User findUserByUsername(String user_name);
    Optional<User> getUserById(int id);
    List<Book> getAvailableBooks();
    int findBookIdByName(String bookName);
    List<Book> getPendingBooks(int userId);
    Reservation findReservationByName(String name);
    void removeReservation(Reservation res);
    List<Book> getReservedBooks(int userId);
    void addReservation(Reservation r);
    Reservation findReservationById(int res_id);
    Book findBookById(int id);
}

