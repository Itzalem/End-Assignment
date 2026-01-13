package com.example.endassigment.model;

public class GameManager {
private static GameManager instance;

private Quiz currentQuiz;
        private String playerName;
        private int score;
        private int currentQuestionIndex;

        private GameManager() {
            this.score = 0;
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
        public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }
}
