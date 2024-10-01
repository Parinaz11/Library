package com.api.Library.service;

import com.api.Library.Business.model.Book;
import com.api.Library.Business.model.Reservation;
import com.api.Library.Data.BookRepository;
import com.api.Library.Data.DatabaseRepository;
import com.api.Library.Data.ReservationRepository;
import com.api.Library.LibraryApplication;
import com.api.Library.model.Book;
import com.api.Library.model.Reservation;
import com.api.Library.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

//    private final DatabaseRepository database;

    @Autowired
    private ReservationRepository reservationRepository;

//    @Autowired
//    public ReservationService(DatabaseRepositorydb) {
//        database = db;
//    }
    private BookService bookService;

//    public ReservationService(DatabaseRepository db, BookService bookService) {
//        this.database = db;
//        this.bookService = bookService;
//    }

//    private final static BookService bookService = new BookService(LibraryApplication.db);

//    private final static ReservationService reservationService = new ReservationService(LibraryApplication.db);

    public Reservation findReservationByName(int book_id) {
//        return database.findReservationByName(res_name);
        return reservationRepository.findReservationByBookId(book_id);
    }
    public void removeReservation(Reservation res) {

//        database.removeReservation(res);
        reservationRepository.delete(res);
    }
    public List<Reservation> getReservations() {

//        return database.getReservations();
        return reservationRepository.findAll();
    }
    public void addReservation(Reservation res) {

//        database.addReservation(res);
        reservationRepository.save(res);
    }
    public void removeReservations(Reservation res) {

//        database.removeReservation(res);
        reservationRepository.delete(res);
    }

    public Reservation findReservationById(int res_id) {
//        return database.findReservationById(res_id);
        return reservationRepository.findReservationById(res_id);
    }

    public boolean reserve(int bookId, int user_id){
        // checks bookID in order to see if the book is reserved or not
        // If it was reserved, returns false
        // else, changes the status of book to reserved and updates the reserve array

        Book bookToReserve = bookService.findBookById(bookId);
//        Book bookToReserve = bookRepository.findBookById(bookId);
        if (bookToReserve != null && bookToReserve.getAvailable()) {
            bookToReserve.setAvailable(false);
            // Mark the book as reserved
//            reservationService.addReservation(new Reservation(bookId, user_id, "pending")); // Add reservation to the library's list
            reservationRepository.save(new Reservation(bookId, user_id, "pending"));
            return true;
        } else if (bookToReserve != null && !bookToReserve.getAvailable()) {
            return false;
        }
        System.out.println("Book does not exist.");
        return false;
    }
}
