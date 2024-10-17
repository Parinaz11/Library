package com.api.Library.service;

import com.api.Library.exception.ReservationBadRequestException;
import com.api.Library.exception.ResourceNotFoundException;
import com.api.Library.repository.ReservationRepository;
import com.api.Library.model.Book;
import com.api.Library.model.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationService implements ReservationServiceInterface{

    private final ReservationRepository reservationRepository;
    private final BookService bookService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BookService bookService) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
    }

    public Reservation findReservationByName(int book_id) {
        if (reservationRepository.findReservationByBookId(book_id) == null) {
            throw new ResourceNotFoundException("Reservation not found");
        }
        return reservationRepository.findReservationByBookId(book_id);
    }

    @Transactional
    public void removeReservation(int res_id, int user_id) {
        Reservation res = entityManager.find(Reservation.class, res_id);
        if (res != null) {
            entityManager.remove(res);
            entityManager.flush();
        }
        else {
            throw new ResourceNotFoundException("Reservation not found or you  don't have permission to delete it.");
        }
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    @Transactional
    public void addReservation(Reservation res) {
        entityManager.persist(res);
        entityManager.flush();
    }

    @Transactional
    public void updateReservation(Reservation res) {
        entityManager.merge(res);
        entityManager.flush();
    }

    public Reservation findReservationById(int res_id) {
        if (reservationRepository.findById(res_id).isEmpty()) {
            throw new ResourceNotFoundException("reservation not found");
        }
        return reservationRepository.findById(res_id).get();
    }

    public String reserve(int bookId, int user_id){
        // checks bookID in order to see if the book is reserved or not
        // If it was reserved, returns false
        // else, changes the status of book to reserved and updates the reserve array
        Book bookToReserve = bookService.findBookById(bookId);
        if (bookToReserve != null && bookToReserve.getAvailable()) {
            bookToReserve.setAvailable(false);
            // Mark the book as reserved
            reservationRepository.save(new Reservation(bookId, user_id, "pending"));
            return "Reservation request successful";
        } else if (bookToReserve != null && !bookToReserve.getAvailable()) {
            throw new ReservationBadRequestException("Reservation request failed. The book is already reserved.");
        }
        else {
            System.out.println("Book does not exist.");
            throw new ResourceNotFoundException("book not found");
        }
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
