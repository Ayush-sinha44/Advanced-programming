package com.registration.exception;

/**
 * Unchecked (runtime) exception thrown when a user does not meet
 * the minimum age requirement of 18 years for registration.
 */
public class UnderageException extends RuntimeException {

    /**
     * Constructs an UnderageException with a descriptive error message.
     *
     * @param message the detail message describing the age violation
     */
    public UnderageException(String message) {
        super(message);
    }
}
