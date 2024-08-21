import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    private static List<Book> books;
    private static List<User> users;
    private static List<Reservation> reservations;

    // Methods for book CRUD, user management, and reservation handling

    public Library() {
        System.out.println("Enter your choice:\n1) Reserve a Book\n2) Book Lists\n3) Reserve Status");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                reserveBook();
                break;
            case 2:
                showBookList();
                break;
            case 3:
                showReserveStatus();
        }
    }

    // Method to populate the ArrayList with 10 sample books
    private void populateBooks() {
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
        for (Book book : books) {
            System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() +
                    ", Author: " + book.getAuthor() + ", Pages: " + book.getPages() +
                    ", Available: " + (book.getAvailable() ? "Yes" : "No"));
        }
    }

    // populate the ArrayList with 10 sample users
    private void populateUsers() {
        users.add(new User("John", "Doe", "john.doe@example.com", "password123"));
        users.add(new User("Jane", "Smith", "jane.smith@example.com", "password456"));
        users.add(new User("Alice", "Johnson", "alice.johnson@example.com", "password789"));
        users.add(new User("Bob", "Brown", "bob.brown@example.com", "password101"));
        users.add(new User("Charlie", "Davis", "charlie.davis@example.com", "password102"));
        users.add(new User("Diana", "Miller", "diana.miller@example.com", "password103"));
        users.add(new User("Eve", "Wilson", "eve.wilson@example.com", "password104"));
        users.add(new User("Frank", "Moore", "frank.moore@example.com", "password105"));
        users.add(new User("Grace", "Taylor", "grace.taylor@example.com", "password106"));
        users.add(new User("Henry", "Anderson", "henry.anderson@example.com", "password107"));
    }

    public static void displayUsers() {
        for (User user : users) {
            System.out.println("Name: " + user.getFirstName() + " " + user.getLastName() +
                    ", Email: " + user.getEmail() + ", Role: " + user.getRole());
        }
    }

    public static void reserveBook() {
        System.out.println("--- Reserve ---");
        System.out.println("Enter the name of the book:");
        Scanner scan = new Scanner(System.in);
        String bookName = scan.nextLine();
        System.out.println("Sorry, the book " + bookName + " is reserved.");
    }

    public static void showBookList() {
        System.out.println("--- Our Books ---");
    }

    public static void showReserveStatus() {
        System.out.println("--- Reserve Status ---");
    }
}