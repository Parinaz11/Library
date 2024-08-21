import java.util.List;

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

    public void showTheListOfAllBooks (List<Book>books) {
        for (Book book : books ) {
            System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() +
                    ", Author: " + book.getAuthor() + ", Pages: " + book.getPages() +
                    ", Available: " + (book.getAvailable() ? "Yes" : "No"));
        }
    }

    public void addBookToList (List<Book>books, Book book) {
        books.add(new Book(book.getTitle(), book.getAuthor(), book.getAvailable(), book.getPages()));
    }

    public void deleteBookFromList (List<Book>books, int idOfBookToDelete) {
        books.remove(idOfBookToDelete);
    }
}
