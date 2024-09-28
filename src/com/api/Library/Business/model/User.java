package com.api.Library.Business.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class User extends Person {
    protected String role; // "user", "admin", "manager"
    protected String hashedPassword;
    protected String salt;

    public User(String username, String firstName, String lastName, String email, String password) {
        super(username, firstName, lastName, email);
        this.salt = generateSaltString();
        this.hashedPassword = hashPassword(password, Base64.getDecoder().decode(this.salt));
        this.role = "user";
    }

    public User(String username, String first_name, String last_name, String email, String hashedPassword, String salt) {
        super(username, first_name, last_name, email);
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.role = "user";
    }

    public User() {
        super();
        this.role = "user";
    }

    public String getRole() {
        return role;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Role: " + getRole());
    }

    protected boolean showMenu(Scanner in) {
        System.out.println("--- Menu ---\nEnter command number:" +
                "\n1) Available Books to Reserve" +
                "\n2) Reserve a Book" +
                "\n3) My Pending Reservations" +
                "\n4) Delete Reservation Request" +
                "\n5) My Reserved Books");

        int answer = in.nextInt();
        in.nextLine();
        runFuncForCommand(answer, in);
        System.out.println("Do you wish to continue? (y/n)");
        String answer2 = in.next();
        return answer2.equalsIgnoreCase("y");
    }

    protected void runFuncForCommand(int choice, Scanner in) {
        switch (choice) {
            case 1:
                showAvailableBooks();
                break;
            case 2:
                reservationRequest(in);
                break;
            case 3:
                pendingReserveBooks();
                break;
            case 4:
                deleteReserveRequest(in);
                break;
            case 5:
                showReservedBooks();
                break;
            default:
                System.out.println("Not a valid command.");
        }
    }

    public int getId() {
        return id;
    }

    public String getEmail() { return email; }
    public String getUsername() { return username; }

    // Method to show available books by making a GET request to the API
    public void showAvailableBooks() {
        System.out.println("--- Available Books ---");
        try {
            URL url = new URL("http://localhost:8080/users/" + getId() + "/available-books");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                System.out.println(content);
                in.close();
            } else {
                System.out.println("Failed to retrieve books. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to send a book reservation request by making a POST request to the API
    public void reservationRequest(Scanner in) {
        System.out.println("--- Reservation Request ---");
        String bookName = getBookNameFromUser(in);
        try {
            URL url = new URL("http://localhost:8080/users/" + getId() + "/reserve-book?bookName=" + bookName);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            int status = con.getResponseCode();
            if (status == 200) {
                System.out.println("✅ Reservation request successful.");
            } else {
                System.out.println("Reservation request failed. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to view pending reservations by making a GET request to the API
    public void pendingReserveBooks() {
        System.out.println("--- Pending Reservation Books ---");
        try {
            URL url = new URL("http://localhost:8080/users/" + getId() + "/pending-reservations");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                System.out.println(content.toString());
                in.close();
            } else {
                System.out.println("Failed to retrieve pending reservations. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to delete a reservation request by making a DELETE request to the API
    public void deleteReserveRequest(Scanner in) {
        System.out.println("--- Delete Reservation Request ---");
        String bookName = getBookNameFromUser(in);
        try {
            URL url = new URL("http://localhost:8080/users/" + getId() + "/delete-reservation?bookName=" + bookName);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");

            int status = con.getResponseCode();
            if (status == 200) {
                System.out.println("Reservation deleted successfully.");
            } else {
                System.out.println("Failed to delete reservation. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to view reserved books by making a GET request to the API
    public void showReservedBooks() {
        System.out.println("--- Reserved Books ---");
        try {
            URL url = new URL("http://localhost:8080/users/" + getId() + "/reserved-books");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                System.out.println(content.toString());
                in.close();
            } else {
                System.out.println("Failed to retrieve reserved books. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to get the book name from the user
    public String getBookNameFromUser(Scanner in) {
        System.out.println("Enter book name: ");
        return in.nextLine();
    }

    // Authentication and security
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private String generateSaltString() {
        return Base64.getEncoder().encodeToString(generateSalt());
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("❗ Error hashing password.", e);
        }
    }

    public boolean verifyPassword(String password) {
        String hashedAttempt = hashPassword(password, Base64.getDecoder().decode(this.salt));
        return hashedAttempt.equals(this.hashedPassword);
    }
}





//package com.api.Library.model;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.util.Base64;
//
//import java.util.Scanner;
//
//public class User extends Person {
//    protected String role; // "user", "admin", "manager"
//    protected String hashedPassword;
//    protected String salt;
//
//    public User(String username, String firstName, String lastName, String email, String password) {
//        super(username, firstName, lastName, email);
//        this.salt = generateSaltString();
//        this.hashedPassword = hashPassword(password, Base64.getDecoder().decode(this.salt));
//        this.role = "user";
//    }
//    public User(String username, String first_name, String last_name, String email, String hashedPassword, String salt) {
//        super(username, first_name, last_name, email);
//        this.salt = salt;
//        this.hashedPassword = hashedPassword;
//        this.role = "user";
//    }
//    public User() {
//        super();
//        setRole("user");
//    }
//
//    public String getRole() { return role; }
//    public void setRole(String role) { this.role = role; }
//
//    @Override
//    public void displayInfo() {
//        super.displayInfo();
//        System.out.println("Role: " + getRole());
//    }
//
//    protected boolean showMenu(Scanner in) {
//        System.out.println("--- Menu ---\nEnter command number:" +
//                "\n1) Available Books to Reserve" +
//                "\n2) Reserve a Book" +
//                "\n3) My Pending Reservations" +
//                "\n4) Delete Reservation Request" +
//                "\n5) My Reserved Books");
//
//        int answer = in.nextInt();
//        in.nextLine();
//        runFuncForCommand(answer, in);
//        System.out.println("Do you wish to continue? (y/n)");
//        String answer2 = in.next();
//        return answer2.equalsIgnoreCase("y");
//    }
//
//    protected void runFuncForCommand(int choice, Scanner in) {
//        switch (choice) {
//            case 1:
//                showAvailableBooks();
//                break;
//            case 2:
//                reservationRequest(in);
//                break;
//            case 3:
//                pendingReserveBooks();
//                break;
//            case 4:
//                deleteReserveRequest(in);
//                break;
//            case 5:
//                showReservedBooks();
//                break;
//            default:
//                System.out.println("Not a valid command.");
//        }
//    }
//
//    public void showAvailableBooks() {
//        System.out.println("--- Available Books ---");
//        for (Book book : Library.getBooks()) {
//            if (book.getAvailable()) {
//                System.out.println("📘 ID: " + book.getId() + ", Title: " + book.getTitle() +
//                        ", Author: " + book.getAuthor() + ", Pages: " + book.getPages());
//            }
//        }
//    }
//
//    // Authentication and security
//    private byte[] generateSalt() {
//        SecureRandom random = new SecureRandom();
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        return salt;
//    }
//
//    private String generateSaltString() {
//        return Base64.getEncoder().encodeToString(generateSalt());
//    }
//
//    private String hashPassword(String password, byte[] salt) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            md.update(salt);
//            byte[] hashedPassword = md.digest(password.getBytes());
//            return Base64.getEncoder().encodeToString(hashedPassword);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("❗ Error hashing password.", e);
//        }
//    }
//
//    public boolean verifyPassword(String password) {
//        String hashedAttempt = hashPassword(password, Base64.getDecoder().decode(this.salt));
//        return hashedAttempt.equals(this.hashedPassword);
//    }
//
//    public void reservationRequest(Scanner in) {
//        System.out.println("--- com.api.Library.model.Reservation Request ---");
//        String bookName = getBookNameFromUser(in);
//        int bookId = Library.findBookIdByName(bookName);
//        if (bookId == -1) {
//            System.out.println("❌ com.api.Library.model.Book not found.");
//            return;
//        }
//        boolean reserve_status = Reservation.reserve(bookId, getId());
//        if (reserve_status) {
//            System.out.println("✅ com.api.Library.model.Reservation request successful.");
//            return;
//        }
//        System.out.println("com.api.Library.model.Reservation request failed. The book is reserved.");
//    }
//
//    public void pendingReserveBooks() {
//        System.out.println("--- Pending com.api.Library.model.Reservation Books ---");
//        showFilteredBooks("pending");
//    }
//
//    public void deleteReserveRequest(Scanner in) {
//        System.out.println("--- Delete com.api.Library.model.Reservation Request ---");
//        String bookName = getBookNameFromUser(in);
//        Reservation reservationToDelete = Library.findReservationByName(bookName);
//        if (reservationToDelete != null && reservationToDelete.getUserId() == this.getId()) {
//            Library.removeReservation(reservationToDelete); // Remove the reservation from the list
//            System.out.println("com.api.Library.model.Reservation deleted successfully.");
//            return;
//        }
//        System.out.println("com.api.Library.model.Reservation not found or you don't have permission to delete it.");
//    }
//
//    public void showReservedBooks() {
//        System.out.println("--- Reserved Books ---");
//        showFilteredBooks("approved");
//    }
//
//    // --- My additional functions ---
//
//    // to make the code less
//    private void showFilteredBooks(String stat) {
//        boolean exists = false;
//        for (Reservation reservation : Library.getReservations()) {
//            if (reservation.getUserId() == this.getId() && reservation.getStatus().equals(stat)) {
//                Book reservedBook = Library.findBookById(reservation.getBookId());
//                if (reservedBook != null) {
//                    exists = true;
//                    System.out.println("🤝 com.api.Library.model.Reservation ID: " + reservation.getReservationId() +
//                            ", com.api.Library.model.Book ID: " + reservedBook.getId() +
//                            ", Title: " + reservedBook.getTitle());
//                }
//            }
//        }
//        if (!exists) {
//            System.out.println("* empty *");
//        }
//    }
//
//    // to get book name
//    public String getBookNameFromUser(Scanner in) {
//        System.out.println("Enter book name: ");
//        return in.nextLine();
//    }
//
//}
//
