package com.api.Library.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Library {
    private static List<Book> books;
    private static List<User> users;
    private static List<Reservation> reservations;
    private static final Logger logInfo = LoggerFactory.getLogger(Library.class);

    public static void populate_BooksUsersReserves(){
        books = new ArrayList<>();
        users = new ArrayList<>();
        reservations = new ArrayList<>();
        populateBooks();
        populateUsers();
        populateReservations();
    }

    // Initialize the ArrayList with sample books
    public Library() {
        books = new ArrayList<Book>();
        users = new ArrayList<User>();
        reservations = new ArrayList<Reservation>();
        populateBooks(); // to add books to the list
        populateUsers();
        Scanner scanner = new Scanner(System.in);

        logInfo.info("Library Info Logging is enable");
        logInfo.debug("Library Debug Logging is enable");
        while (true) {
            System.out.println("Enter the number of your choice:\n1) Login\n2) Sign-up\n3) All Books\n4) Exit");
            int choice = scanner.nextInt();
            boolean status;
            switch (choice) {
                case 1:
                    scanner.nextLine();
                    User user = login(scanner);
                    if (user != null) loginUserOptions(scanner, user);
                    break;
                case 2:
                    scanner.nextLine();
                    status = signUp();
                    if (status) System.out.println("Ready to login.");
                    break;
                case 3:
                    showBookList();
                    break;
                case 4:
                    System.out.println("Bye, come back soon. 😊");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }

    }

    private void loginUserOptions(Scanner scan, User user) {
        boolean play_stat = true;
        while (play_stat) {
            play_stat = user.showMenu(scan);
        }
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

    public static void displayBooks() {
        System.out.println("--- All Books ---");
        for (Book book : books) {
            System.out.println("▫️ID: " + book.getId() + ", Title: " + book.getTitle() +
                    ", Author: " + book.getAuthor() + ", Pages: " + book.getPages() +
                    ", Available: " + (book.getAvailable() ? "Yes" : "No"));
        }
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

    private static void populateReservations() {
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


    public static void displayUsers() {
        for (User user : users) {
            System.out.println("👤 Name: " + user.getFirstName() + " " + user.getLastName() +
                    ", Email: " + user.getEmail() + ", Role: " + user.getRole());
        }
    }

    public static List<User> getAllUsers() {
        return users;
    }

    public static void addBook(Book book) {
        books.add(book);
        System.out.println("com.api.Library.model.Book added: " + book.getTitle() + " by " + book.getAuthor());
    }

    public static boolean removeBook(int bookId) {
        Book bookToRemove = null;
        for (Book book : books) {
            if (book.getId() == bookId) {
                bookToRemove = book;
                break;
            }
        }
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            System.out.println("com.api.Library.model.Book removed: " + bookToRemove.getTitle());
            return true;
        }
        System.out.println("com.api.Library.model.Book with ID " + bookId + " not found.");
        return false;
    }

    public static void showBookList() {
        displayBooks();
    }

    // Authentication
    private User login(Scanner scanner) {
        System.out.println("--- Login ---\nEnter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                System.out.println(username + ", Welcome to the library! 🙂");
                return user;
            }
        }
        System.out.println("❗ Authorization failed.");
        return null;
    }

    public static List<Book> getBooks() {
        return books;
    }

    public static List<Reservation> getReservations() {
        return reservations;
    }

    public static Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book;
        }
        return null;
    }

    private boolean signUp() {
        System.out.println("--- Sign-up ---\nEnter username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        System.out.println("Confirm password: ");
        String confirmPassword = scanner.nextLine();
        if (!confirmPassword.equals(password)) {
            System.out.println("❗ Passwords didn't match. Sign-up failed.");
            return false;
        }

        // hashing the password with salt
        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        User user = new User(username, firstName, lastName, email, hashedPassword, Base64.getEncoder().encodeToString(salt));
        users.add(user);
        System.out.println("✔️ Sign-up successful.");
        return true;
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password!", e);
        }
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static Reservation findReservationById(int id) {
        return reservations.get(id);
    }

    public static void removeReservation(Reservation r) {
        //delete from the reservation list
        reservations.remove(r);
    }

    public static void addReservation(Reservation r) {
        reservations.add(r);
    }

    public static int findBookIdByName(String name) {
        for (Book book : books) {
            if (book.getTitle().equals(name)) return book.getId();
        }
        return -1;
    }

    public static Reservation findReservationByName(String name) {
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

}
