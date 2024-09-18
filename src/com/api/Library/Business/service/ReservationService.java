package com.api.Library.Business.service;

import com.api.Library.Business.model.Book;
import com.api.Library.Business.model.Reservation;
import com.api.Library.Data.DatabaseRepository;
import com.api.Library.LibraryApplication;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReservationService {

    private final DatabaseRepository database;

    @Autowired
    public ReservationService(DatabaseRepository db) {
        database = db;
    }

    private final static BookService bookService = new BookService(LibraryApplication.db);
    private final static ReservationService reservationService = new ReservationService(LibraryApplication.db);

    public Reservation findReservationByName(String res_name) {
        return database.findReservationByName(res_name);
    }
    public void removeReservation(Reservation res) {
        database.removeReservation(res);
    }
    public List<Reservation> getReservations() {
        return database.getReservations();
    }
    public void addReservation(Reservation res) {
        database.addReservation(res);
    }
    public void removeReservations(Reservation res) {
        database.removeReservation(res);
    }

    public Reservation findReservationById(int res_id) {
        return database.findReservationById(res_id);
    }

    public boolean reserve(int bookId, int user_id){
        // checks bookID in order to see if the book is reserved or not
        // If it was reserved, returns false
        // else, changes the status of book to reserved and updates the reserve array

        Book bookToReserve = bookService.findBookById(bookId);
        if (bookToReserve != null && bookToReserve.getAvailable()) {
            bookToReserve.setAvailable(false); // Mark the book as reserved
            reservationService.addReservation(new Reservation(bookId, user_id, "pending")); // Add reservation to the library's list
            return true;
        } else if (bookToReserve != null && !bookToReserve.getAvailable()) {
            return false;
        }
        System.out.println("Book does not exist.");
        return false;
    }
}
