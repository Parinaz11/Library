package com.api.Library.exception;

public class ReservationBadRequestException extends RuntimeException {

    public ReservationBadRequestException(String message) {
        super(message);
    }

    public ReservationBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
