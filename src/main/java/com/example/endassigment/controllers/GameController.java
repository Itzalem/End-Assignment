package com.example.endassigment.controllers;

import com.example.endassigment.model.Element;
import com.example.endassigment.model.GameManager;
import com.example.endassigment.model.Page;
import com.example.endassigment.model.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class GameController {
    @FXML
    public TextField playerName;
    @FXML
    public VBox nameBox;
    @FXML
    public VBox questionBox;
    @FXML
    public Label labelTitle;

    private Element currentQuestion;

    @FXML
    public void initialize() {
        nameBox.setVisible(true);
        questionBox.setVisible(false);

        Quiz quiz = GameManager.getInstance().getCurrentQuiz();

        if (quiz != null) {
            System.out.println("Game on" + quiz.getTitle());
        }
    }

    @FXML
    public void onNameEntered() {
        String name = playerName.getText();

        if (name != null && !name.trim().isEmpty()) {
            nameBox.setVisible(false);
            questionBox.setVisible(true);

            Quiz quiz = GameManager.getInstance().getCurrentQuiz();

            if (quiz != null && !quiz.getPages().isEmpty()) {
                Page firstPage = quiz.getPages().getFirst();

                if (!firstPage.getElements().isEmpty()) {
                    Element firstQuestion = firstPage.getElements().getFirst();

                    displayQuestion(firstQuestion);
                }
            }
        }
    }

    private void displayQuestion(Element question) {
        this.currentQuestion = question;

        labelTitle.setText(question.getTitle());

        //lógica extra
        //lblScore.setText("Points: " + GameManager.getInstance().getScore());
    }


}
