package com.api.Library.Business.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class Book {
    private static int id_counter = 0;
    @Getter
    private final int id;
    @Setter
    @Getter
    private String title;
    @Setter
    @Getter
    private String author;
    @Setter
    private boolean available; // false means reserved
    @Setter
    @Getter
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
}
