public class User extends Person {
    protected String role; // "user", "admin", "manager"

    public User(String first_name, String last_name, String email, String password) {
        super(first_name, last_name, email, password);
        this.role = "user";
    }

    public User() {
        super();
        this.role = "user";
    }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Role: " + role);
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

    public void reservationRequest(int bookId) {
        System.out.println("--- Reservation Request ---");
        boolean reserve_status = Reservation.reserve(bookId, this.id);
        if (reserve_status) {
            System.out.println("Reservation successful.");
            return;
        }
        System.out.println("Reservation failed. The book might be already reserved.");
    }

    public void pendingReserveBooks() {
        System.out.println("--- Pending Reservation Books ---");
        for (Reservation reservation : Library.getReservations()) {
            if (reservation.getUserId() == this.getId() && reservation.getStatus().equals("pending")) {
                Book reservedBook = Library.findBookById(reservation.getBookId());
                if (reservedBook != null) {
                    System.out.println("Reservation ID: " + reservation.getReservationId() +
                            ", Book ID: " + reservedBook.getId() +
                            ", Title: " + reservedBook.getTitle());
                }
            }
        }
    }

    public void deleteReserveRequest(int reservationId) {
        System.out.println("--- Delete Reservation Request ---");
        Reservation reservationToDelete = Library.findReservationById(reservationId);
        if (reservationToDelete != null && reservationToDelete.getUserId() == this.getId()) {
            Library.removeReservation(reservationToDelete); // Remove the reservation from the list
            System.out.println("Reservation deleted successfully.");
        } else {
            System.out.println("Reservation not found or you don't have permission to delete it.");
        }
    }

    public void showReservedBooks() {
        System.out.println("--- Reserved Books ---");
        for (Reservation reservation : Library.getReservations()) {
            if (reservation.getUserId() == this.getId() && reservation.getStatus().equals("approved")) {
                Book reservedBook = Library.findBookById(reservation.getBookId());
                if (reservedBook != null) {
                    System.out.println("Reservation ID: " + reservation.getReservationId() +
                            ", Book ID: " + reservedBook.getId() +
                            ", Title: " + reservedBook.getTitle());
                }
            }
        }
    }
}

