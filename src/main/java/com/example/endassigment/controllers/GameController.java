package com.example.endassigment.controllers;

import com.example.endassigment.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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

            if (currentQuestionIndex == quiz.getPages().size() - 1) {
                buttonNext.setText("Finish Quiz");
            } else {
                buttonNext.setText("Next");
            }

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

                onNextClicked(new ActionEvent(buttonNext, null)); //because now this method needs an event
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void onNextClicked(ActionEvent event) {
        checkAnswer();

        if (timeline != null){
            timeline.stop();
        }

        Quiz quiz = GameManager.getInstance().getCurrentQuiz();
        int currentQuestionIndex = GameManager.getInstance().getCurrentQuestionIndex();

        if (currentQuestionIndex == quiz.getPages().size() - 1) {
            saveResults(event);
        }
        else {
            GameManager.getInstance().setCurrentQuestionIndex(currentQuestionIndex + 1);
            loadQuestion();
        }

    }

    private void saveResults(ActionEvent event) {
        try {
            String playerName = GameManager.getInstance().getPlayerName();

            int score = GameManager.getInstance().getScore();
            int totalQuestions = GameManager.getInstance().getCurrentQuiz().getPages().size();

            PlayerResult playerResult = new PlayerResult(playerName, totalQuestions, score, LocalDateTime.now());

            writeResultsToJson(playerResult);
            changeResultsScreen();
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
        else {
            quizResults = new QuizResults();
            quizResults.setQuizId(quizId);
            quizResults.setName(GameManager.getInstance().getCurrentQuiz().getTitle());
            quizResults.setPlayersResults(new ArrayList<>());
        }

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

