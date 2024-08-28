package com.api.Library;

import com.api.Library.model.Library;

public class HTTPclient {
    public static void main(String[] args) {
        new Library();
    }
}




//package com.api.Library;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Scanner;
//
//public class HTTPclient {
//    private static final String API_BASE_URL = "http://localhost:8080";
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter your user ID: ");
//        int userId = scanner.nextInt();
//        scanner.nextLine(); // consume newline
//
//        boolean continueMenu = true;
//        while (continueMenu) {
//            System.out.println("--- Menu ---\nEnter command number:" +
//                    "\n1) View All Books" +
//                    "\n2) Add a Book" +
//                    "\n3) Remove a Book" +
//                    "\n4) View All Users" +
//                    "\n5) Exit");
//
//            int command = scanner.nextInt();
//            scanner.nextLine(); // consume newline
//
//            switch (command) {
//                case 1:
//                    viewAllBooks(userId);
//                    break;
//                case 2:
//                    System.out.println("Enter book title: ");
//                    String title = scanner.nextLine();
//                    System.out.println("Enter book author: ");
//                    String author = scanner.nextLine();
//                    addBook(userId, title, author);
//                    break;
//                case 3:
//                    System.out.println("Enter book ID to remove: ");
//                    int bookId = scanner.nextInt();
//                    scanner.nextLine(); // consume newline
//                    removeBook(userId, bookId);
//                    break;
//                case 4:
//                    viewAllUsers(userId);
//                    break;
//                case 5:
//                    continueMenu = false;
//                    break;
//                default:
//                    System.out.println("Invalid command.");
//            }
//        }
//
//        scanner.close();
//    }
//
//    private static void viewAllBooks(int userId) {
//        try {
//            URL url = new URL(API_BASE_URL + "/admins/" + userId + "/books");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            int status = con.getResponseCode();
//            if (status == 200) {
//                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuilder content = new StringBuilder();
//                while ((inputLine = in.readLine()) != null) {
//                    content.append(inputLine);
//                }
//                System.out.println("Books: " + content.toString());
//                in.close();
//            } else {
//                System.out.println("Failed to retrieve books. HTTP status: " + status);
//            }
//
//            con.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void addBook(int userId, String title, String author) {
//        try {
//            URL url = new URL(API_BASE_URL + "/admins/" + userId + "/add-book");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("Content-Type", "application/json; utf-8");
//            con.setDoOutput(true);
//
//            String jsonInputString = "{\"title\": \"" + title + "\", \"author\": \"" + author + "\"}";
//
//            try (OutputStream os = con.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            int status = con.getResponseCode();
//            if (status == 201) {
//                System.out.println("Book added successfully.");
//            } else {
//                System.out.println("Failed to add book. HTTP status: " + status);
//            }
//
//            con.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void removeBook(int userId, int bookId) {
//        try {
//            URL url = new URL(API_BASE_URL + "/admins/" + userId + "/remove-book/" + bookId);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("DELETE");
//
//            int status = con.getResponseCode();
//            if (status == 200) {
//                System.out.println("Book removed successfully.");
//            } else if (status == 404) {
//                System.out.println("Book not found.");
//            } else {
//                System.out.println("Failed to remove book. HTTP status: " + status);
//            }
//
//            con.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void viewAllUsers(int userId) {
//        try {
//            URL url = new URL(API_BASE_URL + "/admins/" + userId + "/users");
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            int status = con.getResponseCode();
//            if (status == 200) {
//                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuilder content = new StringBuilder();
//                while ((inputLine = in.readLine()) != null) {
//                    content.append(inputLine);
//                }
//                System.out.println("Users: " + content.toString());
//                in.close();
//            } else {
//                System.out.println("Failed to retrieve users. HTTP status: " + status);
//            }
//
//            con.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
