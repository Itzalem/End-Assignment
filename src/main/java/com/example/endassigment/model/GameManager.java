package com.example.endassigment.model;

public class GameManager {
        private static GameManager instance;

        private Quiz currentQuiz;

        private GameManager() {
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
}
