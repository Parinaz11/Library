public class Manager extends User {
    public Manager(String username, String first_name, String last_name, String email, String hashedPassword, String salt) {
        super(username, first_name, last_name, email, hashedPassword, salt);
        this.role = "manager";
    }
    public Manager() {
        super();
        this.role = "manager";
    }

    public void approveReservation() {
        System.out.println("Approving reservation...");
    }
}
