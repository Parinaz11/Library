import com.mongodb.client.model.Filters;
import org.bson.Document;

public class Reservation {
    private static int id_counter = 0;
    private final int reservationId;
    private final int bookId;
    private final int userId;
    private String status; // pending, approved, declined

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

    public Reservation(int resId, int bookId, int userId, String status) {
        this.reservationId = resId;
        this.bookId = bookId;
        this.userId = userId;
        this.status = status;
    }

    public static boolean reserve(int bookId, int userId) {
        // Find the book by bookId
        Document bookDoc = Library.getBooks().find(Filters.eq("bookId", bookId)).first();

        if (bookDoc != null) {
            boolean isAvailable = bookDoc.getBoolean("available");

            if (isAvailable) {
                // Update the book's availability to false
                Library.getBooks().updateOne(
                        Filters.eq("bookId", bookId),
                        new Document("$set", new Document("available", false))
                );

                Document reservation = new Document("bookId", bookId)
                        .append("userId", userId)
                        .append("status", "pending");

                Library.getReservations().insertOne(reservation);

                System.out.println("Book reserved successfully.");
                return true;
            } else {
                System.out.println("Book is already reserved.");
                return false;
            }
        } else {
            System.out.println("Book does not exist.");
            return false;
        }
    }


}