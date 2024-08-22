import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import java.util.Scanner;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

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
        setRole("user");
    }
    public User(int userId, String username, String first_name, String last_name, String email, String hashedPassword, String salt) {
        super(userId, username, first_name, last_name, email);
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.role = "user";
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getHashedPassword() { return hashedPassword; }
    public String getSalt() { return salt; }

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

    // Assuming Library has a method to get the MongoCollection<Book>
    public void showAvailableBooks() {
        System.out.println("--- Available Books ---");

        // Get the MongoCollection for books
        MongoCollection<Document> booksCollection = Library.getBooks();
        // Retrieve all documents from the collection
        try (MongoCursor<Document> cursor = booksCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                // Create a Book object from the document
                Book book = new Book(
                        doc.getString("title"),
                        doc.getString("author"),
                        doc.getBoolean("available"),
                        doc.getInteger("pages")
                );

                // Check if the book is available
                if (book.getAvailable()) {
                    System.out.println("📘 ID: " + book.getId() +
                            ", Title: " + book.getTitle() +
                            ", Author: " + book.getAuthor() +
                            ", Pages: " + book.getPages());
                }
            }
        }
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

    private static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("❗ Error hashing password.", e);
        }
    }

    // Verify password using the salt from the database
    public boolean verifyPassword(String password) {
        if (this.salt == null || this.hashedPassword == null) {
            throw new IllegalStateException("Salt or hashed password is not set.");
        }
        String hashedAttempt = hashPassword(password, Base64.getDecoder().decode(this.salt));
        return hashedAttempt.equals(this.hashedPassword);
    }

    public void reservationRequest(Scanner in) {
        System.out.println("--- Reservation Request ---");
        String bookName = getBookNameFromUser(in);
        int bookId = Library.findBookIdByName(bookName);
        if (bookId == -1) {
            System.out.println("❌ Book not found.");
            return;
        }
        boolean reserve_status = Reservation.reserve(bookId, getId());
        if (reserve_status) {
            System.out.println("✅ Reservation request successful.");
            return;
        }
        System.out.println("Reservation request failed. The book is reserved.");
    }

    public void pendingReserveBooks() {
        System.out.println("--- Pending Reservation Books ---");
        showFilteredBooks("pending");
    }

    public void deleteReserveRequest(Scanner in) {
        System.out.println("--- Delete Reservation Request ---");
        String bookName = getBookNameFromUser(in);
        Reservation reservationToDelete = Library.findReservationByName(bookName);
        if (reservationToDelete != null && reservationToDelete.getUserId() == this.getId()) {
            Library.removeReservation(reservationToDelete.getReservationId()); // Remove the reservation from the list
            System.out.println("Reservation deleted successfully.");
            return;
        }
        System.out.println("Reservation not found or you don't have permission to delete it.");
    }

    public void showReservedBooks() {
        System.out.println("--- Reserved Books ---");
        showFilteredBooks("approved");
    }

    // --- My additional functions ---

    // to make the code less
    private void showFilteredBooks(String status) {
        MongoCollection<Document> reservationsCollection = Library.getReservations();
        MongoCollection<Document> booksCollection = Library.getBooks();

        boolean exists = false;

        // Find reservations with the given status for the current user
        MongoCursor<Document> reservationCursor = reservationsCollection.find(
                Filters.and(
                        Filters.eq("userId", this.getId()),
                        Filters.eq("status", status)
                )
        ).iterator();

        try {
            while (reservationCursor.hasNext()) {
                Document reservationDoc = reservationCursor.next();
                int bookId = reservationDoc.getInteger("bookId");

                // Find the book corresponding to the reservation
                Document bookDoc = booksCollection.find(Filters.eq("bookId", bookId)).first();

                if (bookDoc != null) {
                    exists = true;
                    System.out.println("🤝 Reservation ID: " + reservationDoc.getInteger("reservationId") +
                            ", Book ID: " + bookDoc.getInteger("bookId") +
                            ", Title: " + bookDoc.getString("title"));
                }
            }
        } finally {
            reservationCursor.close(); // Ensure the cursor is closed
        }

        if (!exists) {
            System.out.println("* empty *");
        }
    }

    // to get book name
    public static String getBookNameFromUser(Scanner in) {
        System.out.println("Enter book name: ");
        return in.nextLine();
    }

}

