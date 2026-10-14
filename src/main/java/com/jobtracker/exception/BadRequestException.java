package com.jobtracker.exception;

/**
 * Exception thrown when a business rule is violated.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
