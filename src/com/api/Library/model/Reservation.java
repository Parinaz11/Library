package com.api.Library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "RESERVATIONS")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int bookId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private String status; // pending, approved, rejected

    public Reservation(int bookId, int userId, String status) {
        this.bookId = bookId;
        this.userId = userId;
        this.status = status;
    }

    public Reservation() {}

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