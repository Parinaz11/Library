package com.api.Library.service;

import com.api.Library.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationServiceInterface {
    Reservation findReservationByName(int book_id);
    void removeReservation(int res_id);
    List<Reservation> getReservations();
    void addReservation(Reservation res);
    void removeReservations(Reservation res);
    void updateReservation(Reservation res);
    Optional<Reservation> findReservationById(int res_id);
    boolean reserve(int bookId, int user_id);
    List<Reservation> findPendingReservationsByUserId(int user_id);
}
