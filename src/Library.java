import java.util.Scanner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Library {

    private static MongoCollection<Document> booksCollection;
    private static MongoCollection<Document> usersCollection;
    private static MongoCollection<Document> reservationsCollection;

    public Library(MongoCollection<Document> b, MongoCollection<Document> u, MongoCollection<Document> r) {
        // Initialize collections
        booksCollection = b;
        usersCollection = u;
        reservationsCollection = r;

        // ------------------ Run once to populate ------------------
//        populateBooks();
//        populateUsers();
        // ----------------------------------------------------------

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
                    ;
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

    private void populateBooks() {
        Book book;

        // Add "To Kill a Mockingbird" with bookId
        book = new Book("To Kill a Mockingbird", "Harper Lee", true, 281);
        booksCollection.insertOne(new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages()));

        // Add "1984" with bookId
        book = new Book("1984", "George Orwell", true, 328);
        booksCollection.insertOne(new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages()));

        // Add "The Great Gatsby" with bookId
        book = new Book("The Great Gatsby", "F. Scott Fitzgerald", true, 180);
        booksCollection.insertOne(new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages()));

        // Add "The Catcher in the Rye" with bookId
        book = new Book("The Catcher in the Rye", "J.D. Salinger", true, 214);
        booksCollection.insertOne(new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages()));

        // Add "Moby-Dick" with bookId
        book = new Book("Moby-Dick", "Herman Melville", true, 635);
        booksCollection.insertOne(new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages()));

        // Add "Pride and Prejudice" with bookId
        book = new Book("Pride and Prejudice", "Jane Austen", true, 432);
        booksCollection.insertOne(new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages()));

        // Add "War and Peace" with bookId
        book = new Book("War and Peace", "Leo Tolstoy", true, 1225);
        booksCollection.insertOne(new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages()));

        // Add "The Lord of the Rings" with bookId
        book = new Book("The Lord of the Rings", "J.R.R. Tolkien", true, 1178);
        booksCollection.insertOne(new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages()));

        // Add "Harry Potter and the Sorcerer's Stone" with bookId
        book = new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", true, 309);
        booksCollection.insertOne(new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages()));

        // Add "The Hobbit" with bookId
        book = new Book("The Hobbit", "J.R.R. Tolkien", true, 310);
        booksCollection.insertOne(new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages()));
    }


    public static void displayBooks() {
        for (Document doc : booksCollection.find()) {
            System.out.println("▫️ID: " + doc.getObjectId("_id") +
                    ", Title: " + doc.getString("title") +
                    ", Author: " + doc.getString("author") +
                    ", Pages: " + doc.getInteger("pages") +
                    ", Available: " + (doc.getBoolean("available") ? "Yes" : "No"));
        }
    }

    private void populateUsers() {
        User user;

        // Add "admin" user with userId
        user = new Admin("admin", "Jack", "Smith", "admin@gmail.com", "dotin123");
        usersCollection.insertOne(new Document("userId", user.getId())
                .append("username", user.getUsername())
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("email", user.getEmail())
                .append("password", user.getHashedPassword())
                .append("salt", user.getSalt())
                .append("role", user.getRole()));

        // Add other users with userId
        addUser("johnny", "John", "Doe", "john.doe@example.com", "password123");
        addUser("jane_smith", "Jane", "Smith", "jane.smith@example.com", "password456");
        addUser("alice_j", "Alice", "Johnson", "alice.johnson@example.com", "password789");
        addUser("bob_b", "Bob", "Brown", "bob.brown@example.com", "password101");
        addUser("charlie_d", "Charlie", "Davis", "charlie.davis@example.com", "password102");
        addUser("diana_m", "Diana", "Miller", "diana.miller@example.com", "password103");
        addUser("eve_w", "Eve", "Wilson", "eve.wilson@example.com", "password104");
        addUser("frank_m", "Frank", "Moore", "frank.moore@example.com", "password105");
        addUser("grace_t", "Grace", "Taylor", "grace.taylor@example.com", "password106");
        addUser("henry_a", "Henry", "Anderson", "henry.anderson@example.com", "password107");
    }

    private void addUser(String username, String firstName, String lastName, String email, String password) {
        User user = new User(username, firstName, lastName, email, password);
        usersCollection.insertOne(new Document("userId", user.getId())
                .append("username", user.getUsername())
                .append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("email", user.getEmail())
                .append("password", user.getHashedPassword())
                .append("salt", user.getSalt())
                .append("role", user.getRole()));
    }



    public static void displayUsers() {
        for (Document doc : usersCollection.find()) {
            System.out.println("👤 Name: " + doc.getString("firstName") + " " +
                    doc.getString("lastName") +
                    ", Email: " + doc.getString("email") +
                    ", Role: " + doc.getString("role"));
        }
    }

    public static void addBook(Book book) {
        Document newBook = new Document("bookId", book.getId())
                .append("title", book.getTitle())
                .append("author", book.getAuthor())
                .append("available", book.getAvailable())
                .append("pages", book.getPages());
        booksCollection.insertOne(newBook);
        System.out.println("Book added: " + book.getTitle() + " by " + book.getAuthor());
    }


    public static void removeBook(int bookId) {
        Document bookToRemove = booksCollection.findOneAndDelete(Filters.eq("bookId", bookId));
        if (bookToRemove != null) System.out.println("☑️ Book removed: " + bookToRemove.getString("title"));
         else System.out.println("❌ Book with ID " + bookId + " not found.");
    }

    public static void showBookList() {
        System.out.println("--- Our Books ---");
        displayBooks();
    }

    // Authentication
    private User login(Scanner scanner) {
        System.out.println("--- Login ---\nEnter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        Document userDoc = usersCollection.find(Filters.eq("username", username)).first();

        if (userDoc != null) {
            String storedHashedPassword = userDoc.getString("password");
            String saltBase64 = userDoc.getString("salt");

            // Create a User object with the retrieved salt and hashed password
            User user = new User();
            if (userDoc.getString("role").equalsIgnoreCase("user")){
                user = new User(
                        userDoc.getInteger("userId"),
                        userDoc.getString("username"),
                        userDoc.getString("firstName"),
                        userDoc.getString("lastName"),
                        userDoc.getString("email"),
                        storedHashedPassword,
                        saltBase64
                );
            } else if (userDoc.getString("role").equalsIgnoreCase("admin")) {
                user = new Admin(
                        userDoc.getInteger("userId"),
                        userDoc.getString("username"),
                        userDoc.getString("firstName"),
                        userDoc.getString("lastName"),
                        userDoc.getString("email"),
                        storedHashedPassword,
                        saltBase64
                );
            } else if (userDoc.getString("role").equalsIgnoreCase("manager")) {
                user = new Manager(
                        userDoc.getInteger("userId"),
                        userDoc.getString("username"),
                        userDoc.getString("firstName"),
                        userDoc.getString("lastName"),
                        userDoc.getString("email"),
                        storedHashedPassword,
                        saltBase64
                );
            }


            if (user.verifyPassword(password)) {
                System.out.println(username + ", Welcome to the library! 🙂");
                return user;
            }
        }

        System.out.println("❗ Authorization failed.");
        return null;
    }

    public static MongoCollection<Document> getBooks() {
        return booksCollection;
    }

    public static MongoCollection<Document> getReservations() {
        return reservationsCollection;
    }

    public static Book findBookById(int id) {
        Document doc = booksCollection.find(Filters.eq("bookId", id)).first();
        if (doc != null) {
            return new Book(
                    doc.getInteger("bookId"),
                    doc.getString("title"),
                    doc.getString("author")
            );
        }
        return null;
    }


    private boolean signUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Sign-up ---\nEnter username: ");
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

        // Hashing the password with salt
        byte[] salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        Document newUser = new Document("username", username)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("email", email)
                .append("password", hashedPassword)
                .append("salt", Base64.getEncoder().encodeToString(salt))
                .append("role", "user");

        usersCollection.insertOne(newUser);
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

    public Document findReservationById(ObjectId id) {
        return reservationsCollection.find(Filters.eq("_id", id)).first();
    }

    public static void removeReservation(int reservationId) {
        reservationsCollection.deleteOne(Filters.eq("reservationId", reservationId));
    }

    public void addReservation(Reservation reservation) {
        Document newReservation = new Document("reservationId", reservation.getReservationId())
                .append("bookId", reservation.getBookId())
                .append("userId", reservation.getUserId())
                .append("status", reservation.getStatus());

        reservationsCollection.insertOne(newReservation);
    }

    public static int findBookIdByName(String name) {
        Document doc = booksCollection.find(Filters.eq("title", name)).first();
        return doc != null ? doc.getInteger("bookId") : -1;
    }

    public static Reservation findReservationByName(String name) {
        int bookId = findBookIdByName(name);
        if (bookId != -1) {
            Document reservationDoc = reservationsCollection.find(Filters.eq("bookId", bookId)).first();
            if (reservationDoc != null) {
                int reservationId = reservationDoc.getInteger("reservationId");
                int userId = reservationDoc.getInteger("userId");
                String status = reservationDoc.getString("status");

                return new Reservation(reservationId, bookId, userId, status);
            }
        }
        return null;
    }


}
