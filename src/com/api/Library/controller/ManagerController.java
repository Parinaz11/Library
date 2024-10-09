package com.api.Library.controller;

import com.api.Library.model.Manager;
import com.api.Library.model.Reservation;
import com.api.Library.model.User;
import com.api.Library.service.BookService;
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
//        Manager manager = (Manager) userService.getUserById(id).orElse(null);
//        User user = userService.getUserById(id).orElse(null);
        User user = userService.getUserById(id);
//        if (manager == null || !manager.getRole().equalsIgnoreCase("manager")) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
        if (user == null || !user.getRole().equalsIgnoreCase("manager")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Reservation> pendingRequests = reservationService.getReservations().stream()
                .filter(reservation -> "pending".equalsIgnoreCase(reservation.getStatus()))
                .toList();
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @PostMapping("/{id}/handle-request")
    public ResponseEntity<String> handleReservationRequest(@PathVariable int id, @RequestParam int reservationId, @RequestParam boolean approve) {
//        Manager manager = (Manager) userService.getUserById(id).orElse(null);
//        User user = userService.getUserById(id).orElse(null);
        User user = userService.getUserById(id);
//        if (manager == null || !manager.getRole().equalsIgnoreCase("manager")) {
//            return new ResponseEntity<>("Only managers can handle reservation requests", HttpStatus.FORBIDDEN);
//        }
        if (user == null || !user.getRole().equalsIgnoreCase("manager")) {
            return new ResponseEntity<>("Only managers can handle reservation requests", HttpStatus.FORBIDDEN);
        }
        for (Reservation reservation : reservationService.getReservations()) {
            if (reservation.getReservationId() == reservationId && "pending".equalsIgnoreCase(reservation.getStatus())) {
                if (approve) {
                    reservation.setStatus("approved");
                    reservationService.updateReservation(reservation);
                    return new ResponseEntity<>("Reservation ID " + reservationId + " has been approved.", HttpStatus.OK);
                } else {
                    reservation.setStatus("Declined");
                    return new ResponseEntity<>("Reservation ID " + reservationId + " has been declined.", HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("Reservation ID " + reservationId + " not found or is not pending.", HttpStatus.NOT_FOUND);
    }
}

