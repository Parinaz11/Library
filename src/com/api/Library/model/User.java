package com.api.Library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    private static final Logger logInfo = LoggerFactory.getLogger(User.class);

    protected static int id_counter = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    protected int id;

    @Column(nullable = false, unique = true)
    protected String username;

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(name = "hashed_password", nullable = false)
    protected String hashedPassword;

    @Column(nullable = false)
    protected String salt;

    @Column(nullable = false)
    protected String role;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    protected Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Date updatedAt;

    // Constructor with password hashing
    public User(String username, String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;

        this.salt = generateSaltString();
        this.hashedPassword = hashPassword(password, Base64.getDecoder().decode(this.salt));
        this.role = "user";  // default role
    }

    // Constructor for cases when password is already hashed
    public User(String username, String firstName, String lastName, String email, String hashedPassword, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.role = "user";  // default role
    }

    // Default constructor
    public User() {
        this.id = ++id_counter;
        this.firstName = "Unknown";
        this.lastName = "Unknown";
        this.email = "Unknown";
        this.username = "Unknown";
        this.role = "user";
    }

    // Method for password verification
    public boolean verifyPassword(String password) {
        String hashedAttempt = hashPassword(password, Base64.getDecoder().decode(this.salt));
        return hashedAttempt.equals(this.hashedPassword);
    }

    // Password security methods
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

    // UserDetails interface implementation for Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.hashedPassword;
    }

    @Override
    public String getUsername() {
        return this.email;  // or username if needed
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Menu-related functionality (unchanged)
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
            case 1 -> showAvailableBooks();
            case 2 -> reservationRequest(in);
            case 3 -> pendingReserveBooks();
            case 4 -> deleteReserveRequest(in);
            case 5 -> showReservedBooks();
            default -> System.out.println("Not a valid command.");
        }
    }


    public void showReservedBooks() {
        System.out.println("--- Reserved Books ---");
        try {
            // URL to fetch the reserved books for the user
            URL url = new URL("http://localhost:8080/users/" + getId() + "/reserved-books");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Get the response code
            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Print the reserved books
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


    public String getName() {
        return this.firstName.concat(" ").concat(this.lastName);
    }

    // REST API-related methods (unchanged)
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

    private String getBookNameFromUser(Scanner in) {
        System.out.print("Enter the book name: ");  // Prompt for user input
        return in.nextLine();  // Read and return the user's input
    }


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
}


