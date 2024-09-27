package com.api.Library.Business.model;

import lombok.Getter;

public class Person {
    protected static int id_counter = 0;
    // Getters and Setters
    @Getter
    protected final int id;
    @Getter
    protected String first_name;
    @Getter
    protected String last_name;
    @Getter
    protected String email;
    @Getter
    protected String username;

    public Person(String username, String first_name, String last_name, String email) {

        this.id = ++id_counter;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
    }
    public Person() {
        this.id = ++id_counter;
        this.first_name = "Unknown";
        this.last_name = "Unknown";
        this.email = "Unknown";
        this.username = "Unknown";

    }

    public String getName() { return this.first_name.concat(" ").concat(this.last_name); }
    public void setName(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public void displayInfo() {
        System.out.println("ID: " + id + ", Name: " + getName());
    }
    public String getFirstName() {
        return this.first_name;
    }
    public String getLastName() {
        return this.last_name;
    }
}

