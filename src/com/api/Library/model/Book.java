package com.api.Library.model;

import java.util.Random;

public class Book {
    private static int id_counter = 0;
    private final int id;
    private String title;
    private String author;
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
    public boolean getAvailable() { return available; }
    public void setAvailable(boolean state) {
        this.available = state;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPages() { return pages; }
}

