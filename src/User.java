import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class User extends Person {
    protected String role; // "user", "admin", "manager"
    protected String hashedPassword;
    protected String salt;

    public User(String username, String firstName, String lastName, String email, String password) {
        super(username, firstName, lastName, email);
        this.salt = generateSaltString();
        this.hashedPassword = hashPassword(password, Base64.getDecoder().decode(this.salt));
        this.role = "user";
    }
    public User(String username, String first_name, String last_name, String email, String hashedPassword, String salt) {
        super(username, first_name, last_name, email);
        this.salt = salt;
        this.hashedPassword = hashedPassword;
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

    // Authentication and security
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private String generateSaltString() {
        return Base64.getEncoder().encodeToString(generateSalt());
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("❗ Error hashing password.", e);
        }
    }

    public boolean verifyPassword(String password) {
        String hashedAttempt = hashPassword(password, Base64.getDecoder().decode(this.salt));
        return hashedAttempt.equals(this.hashedPassword);
    }
}
