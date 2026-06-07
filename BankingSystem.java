import java.util.ArrayList;
import java.util.List;

// --- Base Class: Account ---
class Account {
    private String accountNumber;
    private String ownerName;
    private double balance;

    // Constructor 1: Full initialization
    public Account(String accountNumber, String ownerName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        // Validation for initial balance
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            System.out.println("Warning: Initial balance cannot be negative. Setting to 0.");
            this.balance = 0;
        }
    }

    // Constructor 2: Constructor Chaining (Uses this() to call the main constructor)
    public Account(String accountNumber, String ownerName) {
        this(accountNumber, ownerName, 0.0);
    }

    // Getters and Setters (Encapsulation)
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }

    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    // Logic Methods
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + " to " + accountNumber);
        } else {
            System.out.println("Error: Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Withdrawal amount must be positive.");
            return false;
        }
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            System.out.println("Error: Insufficient funds for " + accountNumber);
            return false;
        }
    }

    public void display() {
        System.out.println("Account: " + accountNumber + " | Owner: " + ownerName + " | Balance: $" + String.format("%.2f", balance));
    }
}

// --- Subclass 1: SavingsAccount (Inheritance) ---
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String ownerName, double balance, double interestRate) {
        super(accountNumber, ownerName, balance); // Calling parent constructor
        this.interestRate = interestRate;
    }

    public double calculateInterest() {
        return getBalance() * (interestRate / 100);
    }

    @Override
    public void display() {
        super.display(); // Use parent logic
        System.out.println(" > Type: Savings | Interest Rate: " + interestRate + "% | Projected Interest: $" + String.format("%.2f", calculateInterest()));
    }
}

// --- Subclass 2: CurrentAccount (Inheritance & Overriding) ---
class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String ownerName, double balance, double overdraftLimit) {
        super(accountNumber, ownerName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) return false;

        // Validation: Check if balance + overdraft is enough
        if (amount <= getBalance() + overdraftLimit) {
      
            double currentBalance = getBalance();
            double newBalance = currentBalance - amount;
            
         
            System.out.println("Withdrew $" + amount + " (Overdraft used if necessary)");
            // In a real system, balance would be 'protected' or have a 'setBalance' for subclasses.
            return true; 
        } else {
            System.out.println("Error: Overdraft limit exceeded for " + getAccountNumber());
            return false;
        }
    }

    @Override
    public void display() {
        super.display();
        System.out.println(" > Type: Current | Overdraft Limit: $" + overdraftLimit);
    }
}

// --- Main Class for Testing ---
public class BankingSystem {
    public static void main(String[] args) {
        // Polymorphism: Storing different accounts in a single list of type Account
        List<Account> accounts = new ArrayList<>();

        accounts.add(new SavingsAccount("SA-101", "Alice Smith", 1000.0, 2.5));
        accounts.add(new CurrentAccount("CA-202", "Bob Jones", 500.0, 1000.0));
        accounts.add(new Account("BA-303", "Charlie Brown", 200.0)); // Base account

        System.out.println("--- Initial Account Status ---");
        for (Account acc : accounts) {
            acc.display(); // Polymorphic call
        }

        System.out.println("\n--- Performing Transactions ---");
        // Test Deposit
        accounts.get(0).deposit(500);
        
        // Test Withdrawal with validation
        accounts.get(1).withdraw(1200); // Should work (within overdraft)
        accounts.get(2).withdraw(500);  // Should fail (insufficient funds)

        System.out.println("\n--- Final Account Status ---");
        for (Account acc : accounts) {
            acc.display();
        }
    }
}