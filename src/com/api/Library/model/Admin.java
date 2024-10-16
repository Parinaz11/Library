package com.api.Library.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Admin extends User {

    public Admin(String username, String firstName, String lastName, String email, String hashedPassword, String salt) {
        super(username, firstName, lastName, email, hashedPassword, salt);
        this.role = "admin";
    }

    public Admin(String username, String firstName, String lastName, String email, String password) {
        super(username, firstName, lastName, email, password);
        this.role = "admin";
    }

    public Admin() {
        super();
        this.role = "admin";
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
                displayAllBooks();
                break;
            case 2:
                addBook(in);
                break;
            case 3:
                removeBook(in);
                break;
            case 4:
                displayAllUsers();
                break;
            default:
                System.out.println("Not a valid command.");
        }
    }

    // Methods to display request results which were sent to the API
    public void displayAllBooks() {
        System.out.println("--- All Books ---");
        try {
            URL url = new URL("http://localhost:8080/books");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                System.out.println(content.toString());
                in.close();
            } else {
                System.out.println("Failed to retrieve books. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBook(Scanner in) {
        System.out.println("--- Add Book ---");
        System.out.println("Enter book title: ");
        String title = in.nextLine();
        System.out.println("Enter book author: ");
        String author = in.nextLine();

        try {
            URL url = new URL("http://localhost:8080/books");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonInputString = String.format("{\"title\": \"%s\", \"author\": \"%s\"}", title, author);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int status = con.getResponseCode();
            if (status == 201) {
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Failed to add book. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeBook(Scanner in) {
        System.out.println("--- Remove Book ---");
        System.out.println("Enter book ID to remove: ");
        int bookId = in.nextInt();

        try {
            URL url = new URL("http://localhost:8080/books/" + bookId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");

            int status = con.getResponseCode();
            if (status == 200) {
                System.out.println("Book removed successfully.");
            } else {
                System.out.println("Failed to remove book. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayAllUsers() {
        System.out.println("--- All Users ---");
        try {
            URL url = new URL("http://localhost:8080/users");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                System.out.println(content.toString());
                in.close();
            } else {
                System.out.println("Failed to retrieve users. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

