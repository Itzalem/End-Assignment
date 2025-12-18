package com.example.endassigment.model;

import java.time.LocalDateTime;

public class PlayerResult {
    private String playerName;
    private int totalQuestions;
    private int correctQuestions;
    private LocalDateTime date;

    public PlayerResult(String playerName, int totalQuestions, int correctQuestions, LocalDateTime date) {
        this.playerName = playerName;
        this.totalQuestions = totalQuestions;
        this.correctQuestions = correctQuestions;
        this.date = date;
    }

    public PlayerResult() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectQuestions() {
        return correctQuestions;
    }

    public void setCorrectQuestions(int correctQuestions) {
        this.correctQuestions = correctQuestions;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}
