public class Admin extends User {
    public Admin(String first_name, String last_name, String email, String password) {
        super(first_name, last_name, email, password);
        this.role = "admin";
    }
    public Admin() {
        super();
        this.role = "admin";
    }

}
