package com.api.Library.model;

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
                "\n1) com.api.Library.model.Book Reserve Requests" +
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
                handleReservationRequest();
                break;
            default:
                System.out.println("Not a valid command.");
        }
    }

    public void showPendingRequests() {
        System.out.println("--- Pending Reservations ---");
        for (Reservation reservation : Library.getReservations()) {
            if ("pending".equalsIgnoreCase(reservation.getStatus())) {
                System.out.println("🟡 com.api.Library.model.Reservation ID: " + reservation.getReservationId() +
                        ", com.api.Library.model.Book ID: " + reservation.getBookId() +
                        ", com.api.Library.model.User ID: " + reservation.getUserId() +
                        ", Status: " + reservation.getStatus());
            }
        }
    }

    public void handleReservationRequest() {
        System.out.println("--- com.api.Library.model.Reservation Request ---\nEnter the reservation ID: ");
        Scanner scanner = new Scanner(System.in);
        int reservationId = scanner.nextInt();
        scanner.nextLine();
        for (Reservation reservation : Library.getReservations()) {
            if (!reservation.getStatus().equalsIgnoreCase("Pending")) {
                System.out.println("The book request is not pending!");
                return;
            }
            else if (reservation.getReservationId() == reservationId) {
                System.out.println("Do you wish to approve the reservation?(y/n)");
                boolean approve = scanner.next().equalsIgnoreCase("y");
                if (approve) {
                    reservation.setStatus("Approved");
                    System.out.println("com.api.Library.model.Reservation ID " + reservationId + " has been approved.");
                } else {
                    reservation.setStatus("Declined");
                    System.out.println("com.api.Library.model.Reservation ID " + reservationId + " has been declined.");
                }
                return;
            }
        }
        System.out.println("com.api.Library.model.Reservation ID " + reservationId + " not found.");
    }

}
