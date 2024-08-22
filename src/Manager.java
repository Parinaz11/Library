import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Scanner;


public class Manager extends User {

    public Manager(String username, String firstName, String lastName, String email, String hashedPassword, String salt) {
        super(username, firstName, lastName, email, hashedPassword, salt);
        this.role = "manager";
    }

    public Manager(String username, String firstName, String lastName, String email, String password) {
        super(username, firstName, lastName, email, password);
        this.role = "manager";
    }

    public Manager() {
        super();
        this.role = "manager";
    }

    public Manager(int managerId, String username, String firstName, String lastName, String email, String hashedPassword, String salt) {
        super(managerId, username, firstName, lastName, email, hashedPassword, salt);
        this.role = "manager";
    }

    @Override
    public boolean showMenu(Scanner in) {
        System.out.println("--- Menu ---\nEnter command number:" +
                "\n1) Book Reserve Requests" +
                "\n2) Approve/Decline a Request");

        int answer = in.nextInt();
        in.nextLine();
        runFuncForCommand(answer, in);
        System.out.println("Do you wish to continue? (y/n)");
        String answer2 = in.next();
        return answer2.equalsIgnoreCase("y");
    }

    @Override
    protected void runFuncForCommand(int choice, Scanner in) {
        switch (choice) {
            case 1:
                showPendingRequests();
                break;
            case 2:
                handleReservationRequest();
                break;
            default:
                System.out.println("Not a valid command.");
        }
    }

    public void showPendingRequests() {
        System.out.println("--- Pending Reservations ---");
        // Query the database for reservations with a status of "pending"
        FindIterable<Document> reservations = Library.getReservations().find(Filters.eq("status", "pending"));

        for (Document reservationDoc : reservations) {
            String reservationId = reservationDoc.getString("reservationId");
            String bookId = reservationDoc.getString("bookId");
            String userId = reservationDoc.getString("userId");
            String status = reservationDoc.getString("status");

            System.out.println("🟡 Reservation ID: " + reservationId +
                    ", Book ID: " + bookId +
                    ", User ID: " + userId +
                    ", Status: " + status);
        }
    }


    public void handleReservationRequest() {
        System.out.println("--- Reservation Request ---\nEnter the reservation ID: ");
        Scanner scanner = new Scanner(System.in);
        String reservationId = scanner.nextLine();

        // Find the reservation in the database
        Document reservationDoc = Library.getReservations().find(Filters.eq("reservationId", reservationId)).first();

        if (reservationDoc != null) {
            String status = reservationDoc.getString("status");

            if (!"pending".equalsIgnoreCase(status)) {
                System.out.println("The book request is not pending!");
                return;
            }

            System.out.println("Do you wish to approve the reservation? (y/n)");
            boolean approve = scanner.nextLine().equalsIgnoreCase("y");

            // Update the reservation status in the database
            String newStatus = approve ? "Approved" : "Declined";
            Library.getReservations().updateOne(Filters.eq("reservationId", reservationId),
                    new Document("$set", new Document("status", newStatus)));

            System.out.println("Reservation ID " + reservationId + " has been " + newStatus.toLowerCase() + ".");
        } else {
            System.out.println("Reservation ID " + reservationId + " not found.");
        }
    }


}
