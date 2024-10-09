package com.api.Library.repository;

import com.api.Library.model.*;
import com.api.Library.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@Repository
@Repository
public class ArraylistDatabase<T> implements DatabaseRepository {

    private static List<Book> books = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }
    public List<Reservation> getReservations() {
        return reservations;
    }
    public List<User> getUsers() {
        return users;
    }

    public ArraylistDatabase() {
        populate_BooksUsersReserves();
    }

    public void populate_BooksUsersReserves(){
        books = new ArrayList<>();
        users = new ArrayList<>();
        reservations = new ArrayList<>();
        populateBooks();
        populateUsers();
        populateReservations();
    }

    // Method to populate the ArrayList with 10 sample books
    private static void populateBooks() {
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", true, 281));
        books.add(new Book("1984", "George Orwell", true, 328));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", true, 180));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", true, 214));
        books.add(new Book("Moby-Dick", "Herman Melville", true, 635));
        books.add(new Book("Pride and Prejudice", "Jane Austen", true, 432));
        books.add(new Book("War and Peace", "Leo Tolstoy", true, 1225));
        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", true, 1178));
        books.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", true, 309));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", true, 310));
    }

    // populate the ArrayList with 10 sample users
    private static void populateUsers() {
        // Create an com.api.Library.model.Admin instance
        Admin admin = new Admin("admin", "Jack", "Smith", "admin@gmail.com", "dotin123");
        users.add(admin);
        // Create a com.api.Library.model.Manager instance
        Manager manager = new Manager("manager", "Jane", "Smith", "manager@gmail.com", "dotin123");
        users.add(manager);
        users.add(new User("johnny", "John", "Doe", "john.doe@example.com", "password123"));
        users.add(new User("jane_smith", "Jane", "Smith", "jane.smith@example.com", "password456"));
        users.add(new User("alice_j", "Alice", "Johnson", "alice.johnson@example.com", "password789"));
        users.add(new User("bob_b", "Bob", "Brown", "bob.brown@example.com", "password101"));
        users.add(new User("charlie_d", "Charlie", "Davis", "charlie.davis@example.com", "password102"));
        users.add(new User("diana_m", "Diana", "Miller", "diana.miller@example.com", "password103"));
        users.add(new User("eve_w", "Eve", "Wilson", "eve.wilson@example.com", "password104"));
        users.add(new User("frank_m", "Frank", "Moore", "frank.moore@example.com", "password105"));
        users.add(new User("grace_t", "Grace", "Taylor", "grace.taylor@example.com", "password106"));
        users.add(new User("henry_a", "Henry", "Anderson", "henry.anderson@example.com", "password107"));
    }

    private void populateReservations() {
        Reservation reservation;

        // Sample reservation 1
        reservation = new Reservation(1, 1001, "pending");
        addReservation(reservation);

        // Sample reservation 2
        reservation = new Reservation(2, 1002, "approved");
        addReservation(reservation);

        // Sample reservation 3
        reservation = new Reservation(3, 1003, "declined");
        addReservation(reservation);

        // Sample reservation 4
        reservation = new Reservation(4, 1004, "pending");
        addReservation(reservation);

        // Sample reservation 5
        reservation = new Reservation(5, 1005, "approved");
        addReservation(reservation);

        // Sample reservation 6
        reservation = new Reservation(6, 1006, "declined");
        addReservation(reservation);

        // Sample reservation 7
        reservation = new Reservation(7, 1007, "pending");
        addReservation(reservation);

        // Sample reservation 8
        reservation = new Reservation(8, 1008, "approved");
        addReservation(reservation);

        // Sample reservation 9
        reservation = new Reservation(9, 1009, "declined");
        addReservation(reservation);

        // Sample reservation 10
        reservation = new Reservation(10, 1010, "pending");
        addReservation(reservation);
    }

    public void removeReservation(Reservation r) {
        reservations.remove(r);
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book;
        }
        return null;
    }

    public int findBookIdByName(String name) {
        for (Book book : books) {
            if (book.getTitle().equals(name)) return book.getId();
        }
        return -1;
    }

    public Reservation findReservationByName(String name) {
        for (Reservation reservation : reservations) {
            int bookID = reservation.getBookId();
            String book_name = findBookNameFromID(bookID);
            if (book_name != null && book_name.equals(name)) {
                return reservation;
            }
        }
        return null;
    }

    public static String findBookNameFromID(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book.getTitle();
        }
        return null;
    }

    public void addUser(User u) {
        users.add(u);
    }
    public void removeUser(User user) {
        users.remove(user);
    }

    public void removeBook(Book b) {
        books.remove(b);
    }
    public void addBook(Book b) {
        books.add(b);
    }

    public User findUserByUsername(String user_name) {
        return users.stream()
                .filter(u -> u.getUsername().equals(user_name))
                .findFirst()
                .orElse(null);
    }

    public Optional<User> getUserById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }
    public List<Book> getAvailableBooks() {
        return books.stream().filter(Book::getAvailable).toList();
    }
    public List<Book> getPendingBooks(int Userid) {
        return reservations.stream()
                .filter(reservation -> reservation.getUserId() == Userid && reservation.getStatus().equals("pending"))
                .map(reservation -> findBookById(reservation.getBookId()))
                .toList();
    }

    public List<Book> getReservedBooks(int userId) {
        return reservations.stream()
                .filter(reservation -> reservation.getUserId() == userId && reservation.getStatus().equals("approved"))
                .map(reservation -> findBookById(reservation.getBookId()))
                .toList();
    }

    public Reservation findReservationById(int id) {
        for (Reservation res: reservations) {
            if (res.getReservationId() == id) return res;
        }
        return null;
    }




}

