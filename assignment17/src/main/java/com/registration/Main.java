package com.registration;

import com.registration.exception.InvalidEmailException;
import com.registration.exception.UnderageException;
import com.registration.service.RegistrationService;

/**
 * Main demonstration class to show the validation module in action.
 */
public class Main {
    public static void main(String[] args) {
        RegistrationService service = new RegistrationService();

        System.out.println("==================================================");
        System.out.println("   USER ONBOARDING VALIDATION MODULE DEMO         ");
        System.out.println("==================================================");

        // Case 1: Valid Registration
        tryRegistration(service, "john.doe@example.com", 25);

        // Case 2: Invalid Email Registration (Null/Empty)
        tryRegistration(service, "", 20);

        // Case 3: Invalid Email Registration (Malformed Format)
        tryRegistration(service, "invalid-email-no-domain", 22);

        // Case 4: Underage Registration (UnderageException)
        tryRegistration(service, "young.user@example.com", 16);

        System.out.println("==================================================");
    }

    private static void tryRegistration(RegistrationService service, String email, int age) {
        System.out.printf("Attempting registration for -> Email: '%s', Age: %d%n", email, age);
        try {
            boolean success = service.registerUser(email, age);
            if (success) {
                System.out.println("  [SUCCESS] Registration completed successfully!\n");
            }
        } catch (InvalidEmailException e) {
            System.err.println("  [ERROR] Checked Exception: " + e.getMessage() + "\n");
        } catch (UnderageException e) {
            System.err.println("  [ERROR] Unchecked Exception: " + e.getMessage() + "\n");
        } catch (Exception e) {
            System.err.println("  [ERROR] Unexpected error: " + e.getMessage() + "\n");
        }
    }
}
