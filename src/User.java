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

    public void showAvailableBooks() {
        System.out.println("--- Available Books ---");
        for (Book book : Library.getBooks()) {
            if (book.getAvailable()) {
                System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() +
                        ", Author: " + book.getAuthor() + ", Pages: " + book.getPages());
            }
        }
    }

    public void reservationRequest() {
        System.out.println("--- Reservation Request ---");
        String bookName = getBookNameFromUser();
        int bookId = Library.findBookIdByName(bookName);
        if (bookId == -1) {
            System.out.println("Book not found.");
            return;
        }
        boolean reserve_status = Reservation.reserve(bookId, getId());
        if (reserve_status) {
            System.out.println("Reservation successful.");
            return;
        }
        System.out.println("Reservation failed. The book might be already reserved.");
    }

    public void pendingReserveBooks() {
        System.out.println("--- Pending Reservation Books ---");
        showFilteredBooks("pending");
    }

    public void deleteReserveRequest() {
        System.out.println("--- Delete Reservation Request ---");
        String bookName = getBookNameFromUser();
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
        for (Reservation reservation : Library.getReservations()) {
            if (reservation.getUserId() == this.getId() && reservation.getStatus().equals(stat)) {
                Book reservedBook = Library.findBookById(reservation.getBookId());
                if (reservedBook != null) {
                    System.out.println("Reservation ID: " + reservation.getReservationId() +
                            ", Book ID: " + reservedBook.getId() +
                            ", Title: " + reservedBook.getTitle());
                }
            }
        }
    }

    // to get book name
    public String getBookNameFromUser() {
        System.out.println("Enter book name: ");
        Scanner scanner = new Scanner(System.in);
        String bookName = scanner.nextLine();
        scanner.close();
        return bookName;
    }
}

