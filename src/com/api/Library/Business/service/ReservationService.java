package com.api.Library.Business.service;

import com.api.Library.Business.model.Book;
import com.api.Library.Business.model.Library;
import com.api.Library.Business.model.Reservation;
import com.api.Library.LibraryApplication;

import java.util.List;

public class ReservationService {

    private final static BookService bookService = new BookService();
    private final static ReservationService reservationService = new ReservationService();

    public Reservation findReservationByName(String res_name) {
        return LibraryApplication.db.findReservationByName(res_name);
    }
    public void removeReservation(Reservation res) {
        LibraryApplication.db.removeReservation(res);
    }
    public List<Reservation> getReservations() {
        return LibraryApplication.db.getReservations();
    }
    public void addReservation(Reservation res) {
        LibraryApplication.db.addReservation(res);
    }
    public void removeReservations(Reservation res) {
        LibraryApplication.db.removeReservation(res);
    }

    public Reservation findReservationById(int res_id) {
        return LibraryApplication.db.findReservationById(res_id);
    }

    public static boolean reserve(int bookId, int user_id){
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
        System.out.println("com.api.Library.model.Book does not exist.");
        return false;
    }
}
