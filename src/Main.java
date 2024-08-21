import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Library ---");
        new Library();
        // Initialize the library
        Library library = new Library();

        // Create an Admin instance
        Admin admin = new Admin("John", "Doe", "admin@example.com", "Dotin");

        // Scanner to get input from the user
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

//        while (!exit) {
//            System.out.println("\n--- Library Admin Panel ---");
//            System.out.println("1. View All Books");
//            System.out.println("2. Add a Book");
//            System.out.println("3. Remove a Book");
//            System.out.println("4. Exit");
//            System.out.print("Choose an option: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Consume the newline
//
//            switch (choice) {
//                case 1:
//                    admin.viewBooks();
//                    break;
//                case 2:
//                    admin.addBook(library);
//                    break;
//                case 3:
//                    admin.removeBook(library);
//                    break;
//                case 4:
//                    exit = true;
//                    break;
//                default:
//                    System.out.println("Invalid option. Please try again.");
//            }
//        }

        System.out.println("Exiting the Library Admin Panel.");
    }
}
