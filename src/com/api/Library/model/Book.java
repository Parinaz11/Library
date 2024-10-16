package com.api.Library.model;

import jakarta.persistence.*;
import java.util.Random;

@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private int pages;

    @Transient // This field is not persisted in the database
    private final static Random rand = new Random();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user; // The user who reserved this book (nullable)

    public Book() {}

    public Book(String title, String author, boolean available, int pages) {
        this.title = title;
        this.author = author;
        this.available = available;
        this.pages = pages;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;  // By default, books are available
        this.pages = rand.nextInt(2000) + 1; // Random page count
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public boolean getAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public int getPages() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}

