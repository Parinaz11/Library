import java.util.Scanner;

public class Admin extends User {
    public Admin(String username, String first_name, String last_name, String email, String hashedPassword, String salt) {
        super(username, first_name, last_name, email, hashedPassword, salt);
    }
    public Admin(String user_name, String first_name, String last_name, String email, String password) {
        super(user_name, first_name, last_name, email, password);
        super.setRole("admin");
    }
    public Admin(int admin_id, String user_name, String first_name, String last_name, String email, String hashedPassword, String salt) {
        super(admin_id, user_name, first_name, last_name, email, hashedPassword, salt);
        super.setRole("admin");
    }

    public Admin() {
        super();
        super.setRole("admin");
    }

    public void addBook() {
        System.out.println("--- Add Book ---");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book title: ");
        String title = scanner.nextLine();
        System.out.println("Enter book author: ");
        String author = scanner.nextLine();
        Book newBook = new Book(title, author);
        Library.addBook(newBook);
    }

    public void removeBook() {
        System.out.println("--- Remove Book ---");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book ID to remove: ");
        int bookId = scanner.nextInt();
        Library.removeBook(bookId);
    }

    @Override
    public boolean showMenu(Scanner in) {
        System.out.println("--- Menu ---\nEnter command number:" +
                "\n1) All Books" +
                "\n2) Add a Book" +
                "\n3) Remove a Book" +
                "\n4) All Users");

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
                Library.displayBooks();
                break;
            case 2:
                addBook();
                break;
            case 3:
                removeBook();
                break;
            case 4:
                Library.displayUsers();
                break;
            default:
                System.out.println("Not a valid command.");
        }
    }
}
