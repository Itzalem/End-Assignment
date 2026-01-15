package com.example.endassigment.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileParsingTest {
    @TempDir
    Path tempDir;

    @Test
    void testWriteAndReadQuizResults() throws IOException {
        String playerName = "Tester";
        int totalQuestions = 5;
        int score = 5;

        PlayerResult result = new PlayerResult(playerName, totalQuestions, score, LocalDateTime.now());

        QuizResults resultsOverview = new QuizResults();
        resultsOverview.setQuizId("test-quiz");
        resultsOverview.setName("Quiz for testing");
        resultsOverview.setPlayersResults(new ArrayList<>());
        resultsOverview.getPlayersResults().add(result);

        File tempFile = tempDir.resolve("results_test.json").toFile();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mapper.writeValue(tempFile, resultsOverview);

        QuizResults readTable = mapper.readValue(tempFile, QuizResults.class);

        assertNotNull(readTable, "The object must not be null");
        assertEquals("test-quiz", readTable.getQuizId());
        assertEquals(1, readTable.getPlayersResults().size(), "It must be 1 result in the list");

        PlayerResult readResult = readTable.getPlayersResults().getFirst();
        assertEquals("Tester", readResult.getPlayerName());
        assertEquals(5, readResult.getCorrectQuestions());
    }

    @Test
    void testReadQuizFile() throws IOException {
        File quizFile = tempDir.resolve("the_quiz.json").toFile();
        ObjectMapper mapper = new ObjectMapper();

        Quiz originalQuiz = new Quiz();
        originalQuiz.setTitle("Final Test");
        originalQuiz.setDescription("Testing Java");

        mapper.writeValue(quizFile, originalQuiz);

        Quiz loadedQuiz = mapper.readValue(quizFile, Quiz.class);

        assertEquals("Final Test", loadedQuiz.getTitle());
        assertEquals("Testing Java", loadedQuiz.getDescription());
    }
}