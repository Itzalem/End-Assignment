package com.example.endassigment.model;

import java.util.List;

public class QuizResults {

    private String quizId;
    private String name;
    private List<PlayerResult> playersResults;

    public QuizResults(String quizId, String name, List<PlayerResult> playersResults) {
        this.quizId = quizId;
        this.name = name;
        this.playersResults = playersResults;
    }

    public QuizResults() {
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerResult> getPlayersResults() {
        return playersResults;
    }

    public void setPlayersResults(List<PlayerResult> playersResults) {
        this.playersResults = playersResults;
    }

}
