package com.registration.exception;

/**
 * Checked exception thrown when a user provides an invalid email address
 * during registration. This includes null, empty, or malformed email strings
 * that do not conform to the standard email format.
 */
public class InvalidEmailException extends Exception {

    /**
     * Constructs an InvalidEmailException with a descriptive error message.
     *
     * @param message the detail message describing why the email is invalid
     */
    public InvalidEmailException(String message) {
        super(message);
    }
}
