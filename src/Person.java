public class Person {
    protected int id_counter = 0;
    protected final int id;
    protected String first_name;
    protected String last_name;
    protected String email;
    protected String password;

    public Person(String first_name, String last_name, String email, String password) {
        this.id = ++id_counter;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }
    public Person() {
        this.id = ++id_counter;
        this.first_name = "Unknown";
        this.last_name = "Unknown";
        this.email = "Unknown";
        this.password = "123";
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return this.first_name.concat(" ").concat(this.last_name); }
    public void setName(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + getName());
    }

    public String getFirstName() {
        return first_name;
    }
    public String getLastName() {
        return last_name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
}

