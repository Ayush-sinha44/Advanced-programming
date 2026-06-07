package com.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test suite for ScoreProcessor.
 * Verifies the happy path (valid score file) and error paths
 * (missing file, invalid data format).
 */
class ScoreProcessorTest {

    private ScoreProcessor processor;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        processor = new ScoreProcessor();
    }

    // ========== Happy Path Tests ==========

    @Test
    void testProcessScoreFile_validScore_returnsMultipliedValue() throws IOException {
        // Arrange: create a temp file with a valid integer score
        Path scoreFile = tempDir.resolve("valid_score.txt");
        Files.writeString(scoreFile, "8");

        // Act
        int result = processor.processScoreFile(scoreFile.toString());

        // Assert: 8 * 10 = 80
        assertEquals(80, result, "Score of 8 should return 80 after multiplication by 10");
    }

    @Test
    void testProcessScoreFile_zeroScore_returnsZero() throws IOException {
        // Arrange
        Path scoreFile = tempDir.resolve("zero_score.txt");
        Files.writeString(scoreFile, "0");

        // Act
        int result = processor.processScoreFile(scoreFile.toString());

        // Assert: 0 * 10 = 0
        assertEquals(0, result, "Score of 0 should return 0");
    }

    @Test
    void testProcessScoreFile_negativeScore_returnsNegativeResult() throws IOException {
        // Arrange
        Path scoreFile = tempDir.resolve("negative_score.txt");
        Files.writeString(scoreFile, "-5");

        // Act
        int result = processor.processScoreFile(scoreFile.toString());

        // Assert: -5 * 10 = -50
        assertEquals(-50, result, "Score of -5 should return -50");
    }

    @Test
    void testProcessScoreFile_scoreWithWhitespace_trimmedAndParsed() throws IOException {
        // Arrange: score with leading/trailing whitespace
        Path scoreFile = tempDir.resolve("whitespace_score.txt");
        Files.writeString(scoreFile, "  15  ");

        // Act
        int result = processor.processScoreFile(scoreFile.toString());

        // Assert: 15 * 10 = 150
        assertEquals(150, result, "Whitespace-padded score of 15 should return 150");
    }

    // ========== Error Path Tests ==========

    @Test
    void testProcessScoreFile_missingFile_throwsFileNotFoundException() {
        // Arrange: a path that definitely does not exist
        String missingPath = tempDir.resolve("nonexistent_file.txt").toString();

        // Act & Assert
        assertThrows(FileNotFoundException.class,
                () -> processor.processScoreFile(missingPath),
                "Processing a missing file should throw FileNotFoundException");
    }

    @Test
    void testProcessScoreFile_invalidData_throwsNumberFormatException() throws IOException {
        // Arrange: file contains letters instead of a number
        Path invalidFile = tempDir.resolve("invalid_data.txt");
        Files.writeString(invalidFile, "abc");

        // Act & Assert
        assertThrows(NumberFormatException.class,
                () -> processor.processScoreFile(invalidFile.toString()),
                "File with non-numeric content should throw NumberFormatException");
    }

    @Test
    void testProcessScoreFile_decimalData_throwsNumberFormatException() throws IOException {
        // Arrange: file contains a decimal, which is not a valid integer
        Path decimalFile = tempDir.resolve("decimal_data.txt");
        Files.writeString(decimalFile, "7.5");

        // Act & Assert
        assertThrows(NumberFormatException.class,
                () -> processor.processScoreFile(decimalFile.toString()),
                "File with decimal content should throw NumberFormatException");
    }
}
