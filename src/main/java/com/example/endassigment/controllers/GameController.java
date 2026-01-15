package com.example.endassigment.controllers;

import com.example.endassigment.model.Element;
import com.example.endassigment.model.GameManager;
import com.example.endassigment.model.Page;
import com.example.endassigment.model.Quiz;
import com.example.endassigment.model.PlayerResult;
import com.example.endassigment.model.QuizResults;
import com.example.endassigment.model.RadiogroupElement;
import com.example.endassigment.model.BooleanElement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GameController {
    @FXML
    public TextField playerName;
    @FXML
    public VBox nameBox;
    @FXML
    public Pane questionBox;
    @FXML
    public Label labelTitle;
    @FXML
    public VBox answersBox;
    @FXML
    public Button buttonNext;
    @FXML
    public Label labelScore;
    @FXML
    public Label labelTimer;
    @FXML
    public Label labelQuizName;
    @FXML
    public Label labelProgress;

    private ToggleGroup toggleGroup;
    private Timeline timeline;
    private int timeSeconds;

    @FXML
    public void initialize() {
        nameBox.setVisible(true);
        questionBox.setVisible(false);

        Quiz quiz = GameManager.getInstance().getCurrentQuiz();

        if (labelQuizName != null) {
            labelQuizName.setText(quiz.getTitle());
        }

        ///to bind the score label to the observable in Game manager, updates the view automatically
        labelScore.textProperty().bind(
                GameManager.getInstance().scoreProperty().asString("Score: %d")
        );

        updateProgressLabel();
    }

    @FXML
    public void onNameEntered() {
        String name = playerName.getText();

        if (name != null && !name.trim().isEmpty()) {
            GameManager.getInstance().setPlayerName(name);
            GameManager.getInstance().setScore(0);
            GameManager.getInstance().setCurrentQuestionIndex(0); ///reset question index to avoid conflicts

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
           ///if its the last question the button changes to finish quiz
            if (currentQuestionIndex == quiz.getPages().size() - 1) {
                buttonNext.setText("Finish Quiz");
            }
            else {
                buttonNext.setText("Next");
            }

            /// load the question, timer and answers
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
        labelTimer.setText("Time Left: " + timeSeconds);

        /// reduce seconds one by one while updating the label
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeSeconds--;
            labelTimer.setText("Time Left: " + timeSeconds);

            ///if time is 0 go to next question using the same method as the "next" button.
            if (timeSeconds <= 0) {
                timeline.stop();

                onNextClicked(new ActionEvent(buttonNext, null)); ///a fake event to be able to call the method
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void onNextClicked(ActionEvent event) {
        checkAnswer();

        if (timeline != null) {
            timeline.stop();
        }

        Quiz quiz = GameManager.getInstance().getCurrentQuiz();
        int currentQuestionIndex = GameManager.getInstance().getCurrentQuestionIndex();

        ///if last question save the results, if not go to next question
        if (currentQuestionIndex == quiz.getPages().size() - 1) {
            saveResults(event);
        }
        else {
            GameManager.getInstance().setCurrentQuestionIndex(currentQuestionIndex + 1);
            loadQuestion();
        }

    }

    ///loads existing results, and calls other method to append new result and write them back to the file
    private void saveResults(ActionEvent event) {
        try {
            String playerName = GameManager.getInstance().getPlayerName();

            int score = GameManager.getInstance().getScore();
            int totalQuestions = GameManager.getInstance().getCurrentQuiz().getPages().size();

            PlayerResult playerResult = new PlayerResult(playerName, totalQuestions, score, LocalDateTime.now());

            writeResultsToJson(playerResult); ///here the new resuslt is written to the file
            changeResultsScreen(); ///here changes to the final results screen
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            labelTitle.setText("Sorry, an error happened trying to save your results. Restart the quiz please.");
        }
    }

    private void writeResultsToJson(PlayerResult newResult) throws IOException {
        String quizId = GameManager.getInstance().getCurrentQuizId();

        File file = new File(quizId + "-results.json");

        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        QuizResults quizResults;

        if (file.exists()) {
            quizResults = mapper.readValue(file, QuizResults.class);
        }
        else { ///if the file does not exist will create a new Quiz results object
            quizResults = new QuizResults();
            quizResults.setQuizId(quizId);
            quizResults.setName(GameManager.getInstance().getCurrentQuiz().getTitle());
            quizResults.setPlayersResults(new ArrayList<>());
        }

        ///where it actually writes the new result
        quizResults.getPlayersResults().add(newResult);
        mapper.writeValue(file, quizResults);
    }

    private void changeResultsScreen() throws IOException {
        if (getClass().getResource("/com/example/endassigment/Results.fxml") == null) {
            throw new IOException("Results file not found");
        }

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/endassigment/Results.fxml"));
        Stage stage = (Stage) buttonNext.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void displayQuestion(Element question) {
        labelTitle.setText(question.getTitle());

        answersBox.getChildren().clear(); ///to delete previous answers

        toggleGroup = new ToggleGroup(); ///if you select one the other gets deselected

        if (question instanceof RadiogroupElement) {
            createRadiogroupQuestion(question);
        }
        else if (question instanceof BooleanElement) {
            createBooleanQuestion(question);
        }

        updateProgressLabel(); ///question n/total label updates
    }

    private void createRadiogroupQuestion(Element question) {
        RadiogroupElement radiogroupQuestion = (RadiogroupElement) question;

        if (radiogroupQuestion.getChoices() != null) {
            for (String textOption : radiogroupQuestion.getChoices()) {
                RadioButton button = new RadioButton(textOption);
                button.setToggleGroup(toggleGroup);

                answersBox.getChildren().add(button);
            }
        }
        else {
            Label labelError = new Label("Ups, no options for this question. Continue to the next one please.");
            answersBox.getChildren().add(labelError);
        }
    }

    private void createBooleanQuestion(Element question) {
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

    private void updateProgressLabel() {
        GameManager gameManager = GameManager.getInstance();

        int totalQuestions = gameManager.getCurrentQuiz().getPages().size();
        int currentQuestionIndex = gameManager.getCurrentQuestionIndex() + 1;

        labelProgress.setText("Question " + currentQuestionIndex + "/" + totalQuestions);
    }

    private void checkAnswer() {
        boolean isCorrect = false;

        if (toggleGroup.getSelectedToggle() == null) {
            return;
        }

        ///get selected answer by the user
        RadioButton selectedRadioAnswer = (RadioButton) toggleGroup.getSelectedToggle();
        String questionAnswer = selectedRadioAnswer.getText();

        Element currentQuestion = GameManager.getInstance().getCurrentQuiz()
                .getPages().get(GameManager.getInstance().getCurrentQuestionIndex())
                .getElements().getFirst();

        ///check if its correct depending on the type of question
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

        ///add to the score if correct
        if (isCorrect) {
            GameManager.getInstance().addScore(1);
        }
    }
}

