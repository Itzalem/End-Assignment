package com.example.endassigment.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PlayerResultTest {

    @Test
    void testPlayerResultData() {
        String name = "Maria";
        int totalQuestions = 10;
        int correctQuestions = 8;
        LocalDateTime dateTime = LocalDateTime.now();

        PlayerResult result = new PlayerResult(name, totalQuestions, correctQuestions, dateTime);

        assertEquals("Maria", result.getPlayerName());
        assertEquals(10, result.getTotalQuestions());
        assertEquals(8, result.getCorrectQuestions());
        assertEquals(dateTime, result.getDate(), "Saved date must be the same");
    }
}