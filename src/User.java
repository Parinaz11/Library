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
}
