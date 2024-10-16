package com.api.Library.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Manager extends User {

    public Manager(String username, String firstName, String lastName, String email, String hashedPassword, String salt) {
        super(username, firstName, lastName, email, hashedPassword, salt);
        this.role = "manager";
    }

    public Manager(String username, String firstName, String lastName, String email, String password) {
        super(username, firstName, lastName, email, password);
        this.role = "manager";
    }

    public Manager() {
        super();
        this.role = "manager";
    }

    @Override
    public boolean showMenu(Scanner in) {
        System.out.println("--- Menu ---\nEnter command number:" +
                "\n1) Book Reserve Requests" +
                "\n2) Approve/Decline a Request");

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
                showPendingRequests();
                break;
            case 2:
                handleReservationRequest(in);
                break;
            default:
                System.out.println("Not a valid command.");
        }
    }

    // Methods to show request results which were sent to the API
    public void showPendingRequests() {
        System.out.println("--- Pending Reservations ---");
        try {
            URL url = new URL("http://localhost:8080/reservations/pending");
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
                System.out.println("Failed to retrieve pending reservations. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleReservationRequest(Scanner in) {
        System.out.println("--- Handle Reservation Request ---");
        System.out.println("Enter the reservation ID: ");
        int reservationId = in.nextInt();
        in.nextLine();
        System.out.println("Do you wish to approve the reservation? (y/n)");
        boolean approve = in.next().equalsIgnoreCase("y");

        try {
            URL url = new URL("http://localhost:8080/reservations/" + reservationId + "?approve=" + approve);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");

            int status = con.getResponseCode();
            if (status == 200) {
                System.out.println("Reservation ID " + reservationId + " has been " + (approve ? "approved." : "declined."));
            } else {
                System.out.println("Failed to update reservation. HTTP status: " + status);
            }

            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
