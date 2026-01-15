package com.example.endassigment.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameManager {
    private static GameManager instance;
    private Quiz currentQuiz;
    private String playerName;
    private final IntegerProperty score = new SimpleIntegerProperty(0); //observable
    private int currentQuestionIndex;

    private GameManager() {
        this.currentQuestionIndex = 0;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public void setCurrentQuiz(Quiz currentQuiz) {
        this.currentQuiz = currentQuiz;
    }

    public String getCurrentQuizId() {
        if (currentQuiz != null && currentQuiz.getQuizId() != null) {
            return currentQuiz.getQuizId();
        }
        return "Unknown Quiz";
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score.get();
    }

    public void setScore(int score) {
        this.score.set(score);
    }


    public IntegerProperty scoreProperty() {
        return score;
    }

    public void addScore(int points) {
        this.score.set(this.score.get() + points);
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public void resetGame() {
        this.score.set(0);
        this.currentQuestionIndex = 0;
        this.playerName = null;
    }
}
