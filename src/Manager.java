import java.util.List;

public class Manager extends User {
    public Manager(String username, String first_name, String last_name, String email, String hashedPassword, String salt) {
        super(username, first_name, last_name, email, hashedPassword, salt);
        this.role = "manager";
    }
    public Manager() {
        super();
        this.role = "manager";
    }

    public void approveReservation(Reservation reservation) {

        System.out.println("Approving reservation...");
        reservation.setStatus("approved");
    }

    public void rejectReservation(Reservation reservation) {
        System.out.println("Rejecting reservation...");
        reservation.setStatus("rejected");
    }
    public void viewBookReservation(List<Reservation>reservations) {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

}
