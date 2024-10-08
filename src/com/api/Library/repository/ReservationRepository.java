package com.api.Library.repository;

import com.api.Library.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    // Find all reservations for a specific user
    @Query("SELECT r FROM Reservation r WHERE r.userId = :userId")
    List<Reservation> findByUserId(int userId);

    // Find all reservations for a specific book
    @Query("SELECT r FROM Reservation r WHERE r.bookId = :bookId")
    List<Reservation> findByBookId(int bookId);

    // Find all reservations with a specific status
    @Query("SELECT r from Reservation r where r.status = :status")
    List<Reservation> findByStatus(String status);

    @Query("SELECT r from Reservation r where r.bookId = :bookId")
    Reservation findReservationByBookId(int book_id);
}
