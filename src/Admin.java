public class Admin extends User {
    public Admin(String username, String first_name, String last_name, String email, String hashedPassword, String salt) {
        super(username, first_name, last_name, email, hashedPassword, salt);
        this.role = "admin";
    }
    public Admin() {
        super();
        this.role = "admin";
    }

}
