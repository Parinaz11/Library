import java.util.List;
import java.util.Scanner;

public class Library {
    private List<Book> books;
    private List<User> users;
    private List<Reservation> reservations;

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