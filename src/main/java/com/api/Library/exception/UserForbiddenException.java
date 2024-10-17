package com.api.Library.exception;

public class UserForbiddenException extends RuntimeException{
    public UserForbiddenException(String message) {
        super(message);
    }

    public UserForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
