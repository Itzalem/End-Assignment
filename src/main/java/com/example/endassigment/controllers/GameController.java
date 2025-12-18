package com.example.endassigment.controllers;

import com.example.endassigment.model.GameManager;
import com.example.endassigment.model.Quiz;
import javafx.fxml.FXML;

public class GameController {

    @FXML
    public void initialize() {
        Quiz quiz = GameManager.getInstance().getCurrentQuiz();

        if (quiz != null) {
            System.out.println("Game on" + quiz.getTitle());
        }
    }
}
