package com.api.Library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LibraryExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException
            (ResourceNotFoundException resourceNotFoundException) {
        LibraryException libraryException = new LibraryException(
                resourceNotFoundException.getMessage(),
                resourceNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(libraryException, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> handleUserForbiddenException
            (UserForbiddenException userForbiddenException) {
        LibraryException libraryException = new LibraryException(
                userForbiddenException.getMessage(),
                userForbiddenException.getCause(),
                HttpStatus.FORBIDDEN
        );
        return new ResponseEntity<>(libraryException, HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<Object> handleReservationBadRequestException
            (ReservationBadRequestException reservationBadRequestException) {
        LibraryException libraryException = new LibraryException(
                reservationBadRequestException.getMessage(),
                reservationBadRequestException.getCause(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(libraryException, HttpStatus.BAD_REQUEST);
    }
}
