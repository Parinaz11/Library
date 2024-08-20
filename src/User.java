import java.util.Scanner;

public class User extends Person {
    protected String role; // "user", "admin", "manager"

    public User(String first_name, String last_name, String email, String password) {
        super(first_name, last_name, email, password);
        setRole("user");
    }

    public User() {
        super();
        setRole("user");
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Role: " + getRole());
    }

    public boolean showMenu(Scanner in) {
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

    private void runFuncForCommand(int choice, Scanner in) {
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

    public void showAvailableBooks() {
        System.out.println("--- Available Books ---");
        for (Book book : Library.getBooks()) {
            if (book.getAvailable()) {
                System.out.println("📘 ID: " + book.getId() + ", Title: " + book.getTitle() +
                        ", Author: " + book.getAuthor() + ", Pages: " + book.getPages());
            }
        }
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
            Library.removeReservation(reservationToDelete); // Remove the reservation from the list
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
    private void showFilteredBooks(String stat) {
        boolean exists = false;
        for (Reservation reservation : Library.getReservations()) {
            if (reservation.getUserId() == this.getId() && reservation.getStatus().equals(stat)) {
                Book reservedBook = Library.findBookById(reservation.getBookId());
                if (reservedBook != null) {
                    exists = true;
                    System.out.println("🤝 Reservation ID: " + reservation.getReservationId() +
                            ", Book ID: " + reservedBook.getId() +
                            ", Title: " + reservedBook.getTitle());
                }
            }
        }
        if (!exists) {
            System.out.println("* empty *");
        }
    }

    // to get book name
    public String getBookNameFromUser(Scanner in) {
        System.out.println("Enter book name: ");
        return in.nextLine();
    }
}

