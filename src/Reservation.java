public class Reservation {
    private static int id_counter = 0;
    private final int reservationId;
    private final int bookId;
    private final int userId;
    private String status; // pending, approved, rejected

    public int getUserId() { return userId; }
    public String getStatus() { return status; }
    public int getBookId() { return bookId; }
    public int getReservationId() { return reservationId; }
    public void setStatus(String s) { status = s; }

    public Reservation(int bookId, int userId, String status) {
        id_counter++;
        this.reservationId = id_counter;
        this.bookId = bookId;
        this.userId = userId;
        this.status = status;
    }

    public static boolean reserve(int bookId, int user_id){
        // checks bookID in order to see if the book is reserved or not
        // If it was reserved, returns false
        // else, changes the status of book to reserved and updates the reserve array

        Book bookToReserve = Library.findBookById(bookId);
        if (bookToReserve != null && bookToReserve.getAvailable()) {
            bookToReserve.setAvailable(false); // Mark the book as reserved
            Library.addReservation(new Reservation(bookId, user_id, "pending")); // Add reservation to the library's list
            System.out.println("Reservation successful.");
            return true;
        } else if (bookToReserve != null && !bookToReserve.getAvailable()) {
            System.out.println("Reservation failed. The book is reserved.");
            return false;
        }
        System.out.println("Book does not exist.");
        return false;
    }

    }
}