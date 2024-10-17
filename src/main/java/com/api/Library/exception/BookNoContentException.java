package com.api.Library.exception;

public class BookNoContentException extends RuntimeException {
    public BookNoContentException(String message) {
        super(message);
    }

    public BookNoContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
