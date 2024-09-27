package com.api.Library.model;

public class Reservation {
    private static int id_counter = 0;
    private final int reservationId;
    private final int bookId;
    private final int userId;
    private String status; // pending, approved, rejected

    public int getUserId() { return userId; }
    public String getStatus() { return status; }
    public int getBookId() { return bookId; }
    public int getReservationId() { return reservationId; }
    public void setStatus(String s) { status = s; }

    public Reservation(int bookId, int userId, String status) {
        id_counter++;
        this.reservationId = id_counter;
        this.bookId = bookId;
        this.userId = userId;
        this.status = status;
    }

}