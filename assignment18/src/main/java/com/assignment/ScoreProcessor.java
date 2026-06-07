package com.assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * ScoreProcessor reads a numeric score from a text file, multiplies it by 10,
 * and returns the result. It handles missing files and invalid data gracefully
 * using a try-catch-finally structure.
 */
public class ScoreProcessor {

    /**
     * Reads an integer score from the specified file, multiplies it by 10,
     * and returns the result.
     *
     * @param filePath the path to the text file containing a single integer score
     * @return the score multiplied by 10
     * @throws FileNotFoundException   if the file does not exist at the given path
     * @throws NumberFormatException   if the file content cannot be parsed as an integer
     */
    public int processScoreFile(String filePath) throws FileNotFoundException {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            int score = Integer.parseInt(line.trim());
            return score * 10;

        } catch (FileNotFoundException e) {
            System.out.println("Error: The file '" + filePath + "' was not found. "
                    + "Please check the file path and try again.");
            throw e;

        } catch (NumberFormatException e) {
            System.out.println("Error: The file contains invalid data. "
                    + "Expected a numeric integer value but found non-numeric content.");
            throw e;

        } catch (IOException e) {
            System.out.println("Error: An I/O error occurred while reading the file: " + e.getMessage());
            throw new RuntimeException("Failed to read file", e);

        } finally {
            // Cleanup: close the reader if it was opened
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Warning: Failed to close the file reader: " + e.getMessage());
                }
            }
            System.out.println("File cleanup completed");
        }
    }

    /**
     * Main method for manual/demo testing of ScoreProcessor.
     */
    public static void main(String[] args) {
        ScoreProcessor processor = new ScoreProcessor();

        // Demo: attempt to process a sample file
        String testFile = "score.txt";
        System.out.println("=== ScoreProcessor Demo ===");
        System.out.println("Attempting to process file: " + testFile);
        System.out.println();

        try {
            int result = processor.processScoreFile(testFile);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Processing failed with: " + e.getClass().getSimpleName());
        }
    }
}
