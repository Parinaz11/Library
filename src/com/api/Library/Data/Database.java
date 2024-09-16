package com.api.Library.Data;

import com.api.Library.Business.model.Book;
import com.api.Library.Business.model.Reservation;
import com.api.Library.Business.model.User;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public abstract class Database<T> {

    protected static List<Book> books;
    protected static List<User> users;
    protected static List<Reservation> reservations;

    public abstract List<Book> getBooks();
    public abstract List<User> getUsers();
    public abstract List<Reservation> getReservations();

    public abstract void addUser(User user);
    public abstract void removeBook(Book book);
    public abstract void addBook(Book book);

    // Logger for this database class
    private static final Logger logger = Logger.getLogger(Database.class.getName());

    protected void log(String message) {
        logger.info(message);
    }

    public abstract User findUserByUsername(String user_name);
    public abstract Optional<User> getUserById(int id);
    public abstract List<Book> getAvailableBooks();
    public abstract int findBookIdByName(String bookName);
    public abstract List<Book> getPendingBooks(int userId);
    public abstract Reservation findReservationByName(String name);
    public abstract void removeReservation(Reservation res);
    public abstract List<Book> getReservedBooks(int userId);
    public abstract void addReservation(Reservation r);
    public abstract Reservation findReservationById(int res_id);
    public abstract Book findBookById(int id);


    // Additional methods for connection handling, if needed
    protected void connect() {
        // Handle connection logic via connectionPool
        log("Connecting to the database...");
    }

    protected void disconnect() {
        // Handle disconnection logic via connectionPool
        log("Disconnecting from the database...");
    }
}