package com.registration.service;

import com.registration.exception.InvalidEmailException;
import com.registration.exception.UnderageException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for {@link RegistrationService}.
 * Validates successful registrations and verifies that custom exceptions
 * are thrown under the correct invalid-input conditions.
 */
class RegistrationServiceTest {

    private RegistrationService registrationService;

    /**
     * Sets up a fresh RegistrationService instance before each test case.
     */
    @BeforeEach
    void setUp() {
        registrationService = new RegistrationService();
    }

    // ==================== Successful Registration Tests ====================

    @Test
    @DisplayName("Should successfully register a user with a valid email and age >= 18")
    void testRegisterUser_ValidEmailAndAge_ReturnsTrue() throws InvalidEmailException {
        boolean result = registrationService.registerUser("john.doe@example.com", 25);
        assertTrue(result, "Registration should return true for valid inputs");
    }

    @Test
    @DisplayName("Should successfully register a user at the exact minimum age of 18")
    void testRegisterUser_ExactMinimumAge_ReturnsTrue() throws InvalidEmailException {
        boolean result = registrationService.registerUser("alice@university.edu", 18);
        assertTrue(result, "Registration should succeed for age exactly 18");
    }

    @Test
    @DisplayName("Should successfully register with an email containing special characters in local part")
    void testRegisterUser_EmailWithSpecialChars_ReturnsTrue() throws InvalidEmailException {
        boolean result = registrationService.registerUser("user.name+tag@domain.co", 30);
        assertTrue(result, "Registration should accept emails with dots, plus signs in local part");
    }

    @Test
    @DisplayName("Should successfully register with a subdomain email")
    void testRegisterUser_SubdomainEmail_ReturnsTrue() throws InvalidEmailException {
        boolean result = registrationService.registerUser("admin@mail.server.org", 21);
        assertTrue(result, "Registration should accept emails with subdomains");
    }

    // ==================== InvalidEmailException Tests ====================

    @Test
    @DisplayName("Should throw InvalidEmailException when email is null")
    void testRegisterUser_NullEmail_ThrowsInvalidEmailException() {
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            registrationService.registerUser(null, 25);
        });
        assertTrue(exception.getMessage().contains("null or empty"),
                "Exception message should mention null or empty");
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email is an empty string")
    void testRegisterUser_EmptyEmail_ThrowsInvalidEmailException() {
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            registrationService.registerUser("", 25);
        });
        assertTrue(exception.getMessage().contains("null or empty"),
                "Exception message should mention null or empty");
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email contains only whitespace")
    void testRegisterUser_WhitespaceEmail_ThrowsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () -> {
            registrationService.registerUser("   ", 20);
        });
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email is missing @ symbol")
    void testRegisterUser_NoAtSymbol_ThrowsInvalidEmailException() {
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> {
            registrationService.registerUser("johndoeexample.com", 25);
        });
        assertTrue(exception.getMessage().contains("Invalid email format"),
                "Exception message should indicate invalid format");
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email has no domain")
    void testRegisterUser_NoDomain_ThrowsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () -> {
            registrationService.registerUser("john@", 25);
        });
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email has no local part")
    void testRegisterUser_NoLocalPart_ThrowsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () -> {
            registrationService.registerUser("@example.com", 22);
        });
    }

    @Test
    @DisplayName("Should throw InvalidEmailException when email has multiple @ symbols")
    void testRegisterUser_MultipleAtSymbols_ThrowsInvalidEmailException() {
        assertThrows(InvalidEmailException.class, () -> {
            registrationService.registerUser("user@@example.com", 25);
        });
    }

    // ==================== UnderageException Tests ====================

    @Test
    @DisplayName("Should throw UnderageException when age is below 18")
    void testRegisterUser_Underage_ThrowsUnderageException() {
        UnderageException exception = assertThrows(UnderageException.class, () -> {
            registrationService.registerUser("A22g@33.c3", 16);
        });
        assertTrue(exception.getMessage().contains("18"),
                "Exception message should reference the minimum age of 18");
    }

    @Test
    @DisplayName("Should throw UnderageException when age is exactly 17")
    void testRegisterUser_AgeSeventeen_ThrowsUnderageException() {
        assertThrows(UnderageException.class, () -> {
            registrationService.registerUser("teen@example.com", 17);
        });
    }

    @Test
    @DisplayName("Should throw UnderageException when age is zero")
    void testRegisterUser_AgeZero_ThrowsUnderageException() {
        assertThrows(UnderageException.class, () -> {
            registrationService.registerUser("baby@example.com", 0);
        });
    }

    @Test
    @DisplayName("Should throw UnderageException when age is negative")
    void testRegisterUser_NegativeAge_ThrowsUnderageException() {
        assertThrows(UnderageException.class, () -> {
            registrationService.registerUser("invalid@example.com", -5);
        });
    }

    // ==================== Exception Hierarchy Verification ====================

    @Test
    @DisplayName("InvalidEmailException should be a checked exception (extends Exception)")
    void testInvalidEmailException_IsCheckedException() {
        InvalidEmailException ex = new InvalidEmailException("test");
        assertInstanceOf(Exception.class, ex,
                "InvalidEmailException must extend Exception (checked)");
        assertFalse(RuntimeException.class.isAssignableFrom(ex.getClass()),
                "InvalidEmailException must NOT be a RuntimeException");
    }

    @Test
    @DisplayName("UnderageException should be an unchecked exception (extends RuntimeException)")
    void testUnderageException_IsUncheckedException() {
        UnderageException ex = new UnderageException("test");
        assertInstanceOf(RuntimeException.class, ex,
                "UnderageException must extend RuntimeException (unchecked)");
    }
}
