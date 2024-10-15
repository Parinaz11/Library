package com.api.Library.service;

import com.api.Library.exception.ResourceNotFoundException;
import com.api.Library.repository.ReservationRepository;
import com.api.Library.model.Book;
import com.api.Library.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookService bookService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BookService bookService) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
    }


    public Reservation findReservationByName(int book_id) {
//        return database.findReservationByName(res_name);
        return reservationRepository.findReservationByBookId(book_id);
    }
    public void removeReservation(int res_id) {
//        reservationRepository.delete(res);
        reservationRepository.deleteById(res_id);
    }
    public List<Reservation> getReservations() {

        return reservationRepository.findAll();
    }
    public void addReservation(Reservation res) {

        reservationRepository.save(res);
    }
    public void removeReservations(Reservation res) {

        reservationRepository.delete(res);
    }
    public void updateReservation(Reservation res) {
        reservationRepository.save(res);
    }

    public Optional<Reservation> findReservationById(int res_id) {
        return reservationRepository.findById(res_id);
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

    public List<Reservation> findPendingReservationsByUserId(int user_id) {
        return reservationRepository.findPendingReservationByUserId(user_id);
    }

    public String handleReservationRequest(int res_id, boolean approve) {
        for (Reservation reservation : getReservations()) {
            if (reservation.getReservationId() == res_id && "pending".equalsIgnoreCase(reservation.getStatus())) {
                if (approve) {
                    reservation.setStatus("approved");
                    updateReservation(reservation);
                    return " has been approved.";
                } else {
                    reservation.setStatus("Declined");
                    updateReservation(reservation);
                    return " has been declined.";
                }
            }
        }
        throw new ResourceNotFoundException("Reservation ID " + res_id + " reservation not found or is not pending.");
    }
}
