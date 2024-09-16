package com.api.Library.Business.model;

import com.api.Library.Data.ArraylistDatabase;
import com.api.Library.Data.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Library {

    private static Database<String> db;

    // Initialize the ArrayList with sample books
    public Library() {
        db = new ArraylistDatabase<>();

        Scanner scanner = new Scanner(System.in);

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



    public static void displayBooks() {
        System.out.println("--- All Books ---");
        for (Book book : db.getBooks()) {
            System.out.println("▫️ID: " + book.getId() + ", Title: " + book.getTitle() +
                    ", Author: " + book.getAuthor() + ", Pages: " + book.getPages() +
                    ", Available: " + (book.getAvailable() ? "Yes" : "No"));
        }
    }




    public static void displayUsers() {
        List<User> user_list = db.getUsers();
        for (User user : user_list) {
            System.out.println("👤 Name: " + user.getFirstName() + " " + user.getLastName() +
                    ", Email: " + user.getEmail() + ", Role: " + user.getRole());
        }
    }

    public static void addBook(Book book) {
        db.addBook(book);
        System.out.println("com.api.Library.model.Book added: " + book.getTitle() + " by " + book.getAuthor());
    }

    public static boolean removeBook(int bookId) {
        Book bookToRemove = null;
        List<Book> books_list = db.getBooks();
        for (Book book : books_list) {
            if (book.getId() == bookId) {
                bookToRemove = book;
                break;
            }
        }
        if (bookToRemove != null) {
            db.removeBook(bookToRemove);
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
        List<User> user_list = db.getUsers();
        for (User user : user_list) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                System.out.println(username + ", Welcome to the library! 🙂");
                return user;
            }
        }
        System.out.println("❗ Authorization failed.");
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
        db.addUser(user);
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



}
