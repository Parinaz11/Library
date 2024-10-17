package com.api.Library.model;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;
@Entity
@Table(name = "USERS")
public class User{
//    protected String role; // "user", "admin", "manager"
//    protected String hashedPassword;
//    protected String salt;

    private static final Logger logInfo = LoggerFactory.getLogger(User.class);


    protected static int id_counter = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(nullable = false, unique = true)
    protected String username;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(name = "hashed_password", nullable = false)
    protected String hashedPassword;

    @Column(nullable = false)
    protected String salt;

    @Column(nullable = false)
    protected String role;

    // Getters and Setters


    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() { return this.firstName.concat(" ").concat(this.lastName); }
    public void setName(String first_name, String last_name) {
        this.firstName = first_name;
        this.lastName = last_name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public void setRole(String role) {
        this.role = role;
    }



    public User(String username, String firstName, String lastName, String email, String password) {
//        this.id = ++id_counter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;

        this.salt = generateSaltString();
        this.hashedPassword = hashPassword(password, Base64.getDecoder().decode(this.salt));
        this.role = "user";
    }

    public User(String username, String firstName, String lastName, String email, String hashedPassword, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;

        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.role = "user";
    }

    public User() {
        this.id = ++id_counter;
        this.firstName = "Unknown";
        this.lastName = "Unknown";
        this.email = "Unknown";
        this.username = "Unknown";

        this.role = "user";
    }

    public String getRole() {
        return role;
    }

    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + getName() + "Role: " + getRole());
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

