package com.api.Library.repository;

import com.api.Library.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    // Find all reservations for a specific user
    List<Reservation> findByUserId(int userId);

    // Find all reservations for a specific book
    List<Reservation> findByBookId(int bookId);

    // Find all reservations with a specific status
    List<Reservation> findByStatus(String status);

    Reservation findReservationById(int res_id);
    com.api.Library.model.Reservation findReservationByBookId(int book_id);
}
