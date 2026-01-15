package com.example.endassigment.model;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class RadiogroupElementTest {

    @Test
    void testCreateQuestion() {
        String question = "Which is a prime number?";
        List<String> options = Arrays.asList("4", "6", "2");
        String rightAnswer = "2";

        RadiogroupElement element = new RadiogroupElement();
        element.setTitle(question);
        element.setChoices(options);
        element.setCorrectAnswer(rightAnswer);

        assertEquals("Which is a prime number?", element.getTitle());
        assertEquals(3, element.getChoices().size(), "Must be 3 options");
        assertEquals("2", element.getCorrectAnswer(), "Right answer must be the same");
    }
}