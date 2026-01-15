package com.example.endassigment.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BooleanElementTest {

    @Test
    void testCreateBooleanQuestion() {
        String labelTrue = "Yes";
        String labelFalse = "No";
        boolean correctAnswer = true;

        BooleanElement question = new BooleanElement(labelTrue, labelFalse, correctAnswer);

        question.setTitle("Is the sky blue?");

        assertEquals("Yes", question.getLabelTrue());
        assertEquals("No", question.getLabelFalse());
        assertTrue(question.isCorrectAnswer(), "Correct answer must be true");
        assertEquals("Is the sky blue?", question.getTitle(), "The title must be the same");
    }
}