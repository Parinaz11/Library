package com.api.Library.Business.model;


import jakarta.persistence.*;

@Entity
@Table(name = "RESERVATIONS")
public class Reservation {
//    private static int id_counter = 0;
//    private final int reservationId;
//    private final int bookId;
//    private final int userId;
//    private String status; // pending, approved, rejected

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int bookId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private String status; // pending, approved, rejected

//    public int getUserId() { return userId; }
//    public String getStatus() { return status; }
//    public int getBookId() { return bookId; }
//    public int getReservationId() { return reservationId; }
//    public void setStatus(String s) { status = s; }

    public Reservation(int bookId, int userId, String status) {
        id++;
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.status = status;
    }

    public Reservation() {}

//    public Reservation(int bookId, int userId, String status) {
//        this.bookId = bookId;
//        this.userId = userId;
//        this.status = status;
//    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getReservationId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}