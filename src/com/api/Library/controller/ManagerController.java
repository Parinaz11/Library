package com.api.Library.controller;

import com.api.Library.model.Reservation;
import com.api.Library.model.User;
import com.api.Library.service.ReservationService;
import com.api.Library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    private final ReservationService reservationService;
    private final UserService userService;

    @Autowired
    public ManagerController(UserService us, ReservationService rs) {
        this.userService = us;
        this.reservationService = rs;
    }

    @GetMapping("/{id}/pending-requests")
    public ResponseEntity<List<Reservation>> showPendingRequests(@PathVariable int id) {
        User user = userService.getUserById(id);
        userService.checkRoleOfUser(id, "manager", "Only manager can see pending requests");
        List<Reservation> pendingRequests = reservationService.getReservations().stream()
                .filter(reservation -> "pending".equalsIgnoreCase(reservation.getStatus()))
                .toList();
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @PostMapping("/{id}/handle-request")
    public ResponseEntity<String> handleReservationRequest(@PathVariable int id, @RequestParam int reservationId, @RequestParam boolean approve) {
        User user = userService.getUserById(id);
        userService.checkRoleOfUser(id, "manager", "Only manager can  pending requests");
        return new ResponseEntity<>("Reservation ID " + reservationId + reservationService.handleReservationRequest(reservationId, approve), HttpStatus.OK);
    }
}

