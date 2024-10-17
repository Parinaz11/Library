package com.api.Library.service;

import com.api.Library.model.Reservation;

import java.util.List;

public interface ReservationServiceInterface {
    Reservation findReservationByName(int book_id);
    void removeReservation(int res_id, int book_id );
    List<Reservation> getReservations();
    void addReservation(Reservation res);
    void updateReservation(Reservation res);
    Reservation findReservationById(int res_id);
    String reserve(int bookId, int user_id);
    List<Reservation> findPendingReservationsByUserId(int user_id);
}
