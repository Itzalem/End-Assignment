package com.example.endassigment.controllers;

import com.example.endassigment.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
    @FXML
    public VBox answersBox;

    private ToggleGroup toggleGroup;
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

        answersBox.getChildren().clear(); //to delete previous answers

        toggleGroup = new ToggleGroup(); //if you select one the other gets deselected

        //radiogorup question
        if (question instanceof RadiogroupElement) {
            RadiogroupElement radiogroupQuestion = (RadiogroupElement) question;

            for (String textOption : radiogroupQuestion.getChoices()) {
                RadioButton button = new RadioButton(textOption);
                button.setToggleGroup(toggleGroup);

                answersBox.getChildren().add(button);
            }
        }
        //boolean question
        else if (question instanceof BooleanElement) {
            BooleanElement boolQuestion = (BooleanElement) question;

            String textTrue = boolQuestion.getLabelTrue();
            if (textTrue == null) textTrue = "True";

            String textFalse = boolQuestion.getLabelFalse();
            if (textFalse == null) textFalse = "False";

            RadioButton buttonTrue = new RadioButton(textTrue);
            buttonTrue.setToggleGroup(toggleGroup);

            answersBox.getChildren().add(buttonTrue);

            RadioButton buttonFalse = new RadioButton(textFalse);
            buttonFalse.setToggleGroup(toggleGroup);

            answersBox.getChildren().add(buttonFalse);
        }
    }
}

