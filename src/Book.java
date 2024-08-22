import java.util.Random;

public class Book {
    private static int id_counter = 0;
    private final int id;
    private final String title;
    private final String author;
    private boolean available; // false means reserved
    private int pages;
    Random rand  = new Random();

    public Book(String title, String author, boolean available, int pages) {
        id_counter++;
        this.id = id_counter;
        this.title = title;
        this.author = author;
        this.available = available;
        this.pages = pages;
    }
    public Book(String title, String author) {
        id_counter++;
        this.id = id_counter;
        this.title = title;
        this.author = author;
        setAvailable(true);
        setPages(rand.nextInt(2000) + 1);
    }
    public Book(int bId, String title, String author) {
        this.id = bId;
        this.title = title;
        this.author = author;
        setAvailable(true);
        setPages(rand.nextInt(2000) + 1);
    }

    // getters and setters
    public boolean getAvailable(){ return available; }
    public void setAvailable(boolean available){ this.available = available; }
    public int getId() { return id; }
    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }
    public String getAuthor() { return author; }
    public String getTitle() { return title; }
}
