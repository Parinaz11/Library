public class Manager extends User {
    public Manager(String first_name, String last_name, String email, String password) {
        super(first_name, last_name, email, password);
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
