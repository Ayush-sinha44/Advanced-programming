import java.util.ArrayList;
import java.util.List;

// --- Abstraction: The Base Blueprint ---
abstract class LibraryItem {
    private String title;
    private int year;
    
    // Class-level static counter (equivalent to total_items in Python)
    public static int totalItems = 0;

    public LibraryItem(String title, int year) {
        this.title = title;
        this.year = year;
        totalItems++; // Increment every time a new item is created
    }

    // Getters for encapsulation
    public String getTitle() { return title; }
    public int getYear() { return year; }

    // Abstract method: Every subclass MUST implement this
    public abstract void displayInfo();
}

// --- Inheritance: Book Subclass ---
class Book extends LibraryItem {
    private String author;

    public Book(String title, int year, String author) {
        super(title, year); // Call the parent constructor
        this.author = author;
    }

    @Override
    public void displayInfo() {
        System.out.println("[BOOK] Title: " + getTitle() + " | Year: " + getYear() + " | Author: " + author);
    }
}

// --- Inheritance: DVD Subclass ---
class DVD extends LibraryItem {
    private int duration;
    private String genre;

    // Constructor overloading: Version 1 (Full)
    public DVD(String title, int year, int duration, String genre) {
        super(title, year);
        this.duration = duration;
        this.genre = genre;
    }

    // Constructor overloading: Version 2 (Default genre "General")
    public DVD(String title, int year, int duration) {
        this(title, year, duration, "General"); // Chaining to the first constructor
    }

    @Override
    public void displayInfo() {
        System.out.println("[DVD]  Title: " + getTitle() + " | Year: " + getYear() + " | Duration: " + duration + "m | Genre: " + genre);
    }
}

// --- Main Class for Testing ---
public class project11 {
    public static void main(String[] args) {
        // Polymorphism: A List that can hold any LibraryItem
        List<LibraryItem> catalog = new ArrayList<>();

        catalog.add(new Book("The Great Gatsby", 1925, "F. Scott Fitzgerald"));
        catalog.add(new DVD("Inception", 2010, 148, "Sci-Fi"));
        catalog.add(new Book("1984", 1949, "George Orwell"));
        catalog.add(new DVD("The Matrix", 1999, 136)); // Uses the overloaded constructor

        System.out.println("Total Items Created: " + LibraryItem.totalItems + "\n");
        System.out.println("--- Library Catalog ---");

        // Polymorphism in action
        for (LibraryItem item : catalog) {
            item.displayInfo();
        }
    }
}