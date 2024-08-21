import java.util.Scanner;

public class Admin extends User {
    public Admin(String username, String first_name, String last_name, String email, String hashedPassword, String salt) {
        super(username, first_name, last_name, email, hashedPassword, salt);
    }
    public Admin(String first_name, String last_name, String email, String password) {
        super(first_name, last_name, email, password);
        this.role = "admin";
    }

    public Admin() {
        super();
        this.role = "admin";
    }

    // Method to view the list of all books
    public void viewBooks() {
        System.out.println("--- View All Books ---");
        Library.displayBooks();
    }

    // Method to add a new book
    public void addBook(Library library) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();
        System.out.println("Enter book author: ");
        String author = scanner.nextLine();
        Book newBook = new Book(title, author);
        library.addBook(newBook);
    }

    // Method to remove a book by ID
    public void removeBook(Library library) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book ID to remove: ");
        int bookId = scanner.nextInt();
        library.removeBook(bookId);
    }
}
