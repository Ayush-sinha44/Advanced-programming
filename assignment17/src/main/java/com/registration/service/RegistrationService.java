package com.registration.service;

import com.registration.exception.InvalidEmailException;
import com.registration.exception.UnderageException;

import java.util.regex.Pattern;

/**
 * Core service responsible for validating and processing user registrations.
 * Enforces strict business constraints on email format and minimum age.
 */
public class RegistrationService {

    /** Minimum age required to register on the platform. */
    private static final int MINIMUM_AGE = 18;

    /**
     * Standard email regex pattern:
     *   - One or more word characters, dots, percent signs, plus signs, or hyphens (local part / identifier)
     *   - Followed by the @ symbol
     *   - Followed by one or more word characters, dots, or hyphens (domain name)
     *   - Followed by a dot and 2-to-6 letter top-level domain
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");

    /** Flag indicating whether the service has been properly initialized. */
    private boolean initialized;

    /**
     * Constructs a new RegistrationService and marks it as initialized.
     */
    public RegistrationService() {
        this.initialized = true;
    }

    /**
     * Validates and registers a user with the given email and age.
     *
     * <p>The method performs the following checks in order:
     * <ol>
     *   <li>An internal assertion verifies that the service is properly initialized.</li>
     *   <li>The email must not be null or empty, and must match the standard email regex.</li>
     *   <li>The age must be at least 18.</li>
     * </ol>
     *
     * @param email the user's email address
     * @param age   the user's age in years
     * @return {@code true} if the registration is successful
     * @throws InvalidEmailException if the email is null, empty, or does not match the required format
     * @throws UnderageException     if the age is below the minimum requirement of 18
     */
    public boolean registerUser(String email, int age) throws InvalidEmailException {
        // Internal assert to guarantee the service context is valid before processing
        assert initialized : "RegistrationService is not properly initialized";

        // --- Email validation ---
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidEmailException("Email address cannot be null or empty.");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException(
                    "Invalid email format: '" + email + "' does not match the required pattern.");
        }

        // --- Age validation ---
        if (age < MINIMUM_AGE) {
            throw new UnderageException(
                    "User must be at least " + MINIMUM_AGE + " years old to register. Provided age: " + age);
        }

        // All validations passed — registration is successful
        return true;
    }
}
