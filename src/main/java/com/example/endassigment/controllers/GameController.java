package com.example.endassigment.controllers;

import com.example.endassigment.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

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
    @FXML
    public Label labelScore;
    @FXML
    public Label labelTimer;

    private ToggleGroup toggleGroup;
    private Element currentQuestion;
    private Timeline timeline;
    private int timeSeconds;

    @FXML
    public void initialize() {
        nameBox.setVisible(true);
        questionBox.setVisible(false);

        Quiz quiz = GameManager.getInstance().getCurrentQuiz();

        if (quiz != null) {
            System.out.println("Game on" + quiz.getTitle());
        }

        labelScore.textProperty().bind(
                GameManager.getInstance().scoreProperty().asString("Score: %d")
        );
    }

    @FXML
    public void onNameEntered() {
        String name = playerName.getText();

        if (name != null && !name.trim().isEmpty()) {
            GameManager.getInstance().setPlayerName(name);
            GameManager.getInstance().setScore(0);
            GameManager.getInstance().setCurrentQuestionIndex(0); //reset question index to avoid conflicts

            nameBox.setVisible(false);
            questionBox.setVisible(true);

            loadQuestion();
        }
    }

    @FXML
    public void loadQuestion() {
        Quiz quiz = GameManager.getInstance().getCurrentQuiz();

        int currentQuestionIndex = GameManager.getInstance().getCurrentQuestionIndex();
        if (quiz != null && currentQuestionIndex < quiz.getPages().size()) {

            Page page = quiz.getPages().get(currentQuestionIndex);

            int limit = page.getTimeLimit();
            startTimer(limit);

            if (!page.getElements().isEmpty()) {
                Element question = page.getElements().getFirst();
                displayQuestion(question);
            }
        }
        else {
            timeline.stop();

            labelTitle.setText("Quiz Finished");
            answersBox.getChildren().clear();

            labelTimer.setText("");
        }
    }

    private void startTimer(int seconds) {
        if (timeline != null) {
            timeline.stop();
        }

        this.timeSeconds = seconds;
        labelTimer.setText("" + timeSeconds);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeSeconds--;
            labelTimer.setText("" + timeSeconds);

            //if 0 go to next question
            if (timeSeconds <= 0) {
                timeline.stop();

                onNextClicked();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void onNextClicked() {
        checkAnswer();

        timeline.stop();

        int currentQuestionIndex = GameManager.getInstance().getCurrentQuestionIndex();
        GameManager.getInstance().setCurrentQuestionIndex(currentQuestionIndex + 1);

        loadQuestion();
    }

    private void checkAnswer() {
        boolean isCorrect = false;

        if (toggleGroup.getSelectedToggle() == null) {
            return;
        }

        RadioButton selectedRadioAnswer = (RadioButton) toggleGroup.getSelectedToggle();
        String questionAnswer = selectedRadioAnswer.getText();

        Element currentQuestion = GameManager.getInstance().getCurrentQuiz()
                .getPages().get(GameManager.getInstance().getCurrentQuestionIndex())
                .getElements().getFirst();

        if (currentQuestion instanceof RadiogroupElement) {
            RadiogroupElement question = (RadiogroupElement) currentQuestion;

            if (questionAnswer.equals(question.getCorrectAnswer())) {
                isCorrect = true;
            }
        }
        else if (currentQuestion instanceof BooleanElement) {
            BooleanElement question = (BooleanElement) currentQuestion;

            if (question.isCorrectAnswer() && questionAnswer.equals(question.getLabelTrue())) {
                isCorrect = true;
            }

            else if (!question.isCorrectAnswer() && questionAnswer.equals(question.getLabelFalse())) {
                isCorrect = true;
            }
        }

        if (isCorrect) {
            GameManager.getInstance().addScore(1);
        }
    }

    @FXML
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

