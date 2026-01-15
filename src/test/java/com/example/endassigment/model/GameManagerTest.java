package com.example.endassigment.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private GameManager gameManager;

    @BeforeEach
    void setUp() {
        gameManager = GameManager.getInstance();
        gameManager.resetGame();
    }

    @Test
    void testInitialScoreIsZero() {
        assertEquals(0, gameManager.getScore(), "Score must be 0");
    }

    @Test
    void testAddScore() {
        gameManager.addScore(1);

        assertEquals(1, gameManager.getScore(), "Score must be 1 after addition");
    }

    @Test
    void testResetGame() {
        gameManager.setPlayerName("Maria");
        gameManager.setScore(4);
        gameManager.setCurrentQuestionIndex(5);
        gameManager.resetGame();

        assertEquals(0, gameManager.getScore(), "Score must go back to 0");
        assertEquals(0, gameManager.getCurrentQuestionIndex(), "Index must go back to 0");
        assertNull(gameManager.getPlayerName(), "Player name must be deleted");
    }
}