package com.example.endassigment.controllers;

import com.example.endassigment.model.GameManager;
import com.example.endassigment.model.PlayerResult;
import com.example.endassigment.model.QuizResults;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ResultsController {
    @FXML
    private TextArea resultsTextArea;

    @FXML
    public void initialize() {
        try {
            String quizId = GameManager.getInstance().getCurrentQuizId();
            File file = new File(quizId + "-results.json");

            if (file.exists()) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                QuizResults quizResults = mapper.readValue(file, QuizResults.class);

                resultsTextArea.setText("Results \n");

                for (PlayerResult player : quizResults.getPlayersResults()) {
                    String resultsOverview = "Palyer: " + player.getPlayerName() + "\n" +
                            "Total Questions: " + player.getTotalQuestions() + "\n" +
                            "Correct Questions: " + player.getCorrectQuestions() + "\n" +
                            "Date: " + player.getDate() + "\n";

                    resultsTextArea.appendText(resultsOverview);
                    resultsTextArea.appendText("-------------\n");
                }
            }
            else {
                resultsTextArea.setText("No results found");
            }

        } catch (Exception e) {
            resultsTextArea.setText("Error loading results");
            e.printStackTrace();
        }
    }

    public void onPlayAgainClicked(ActionEvent actionEvent) {
        try {
            GameManager.getInstance().resetGame();

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/endassigment/Menu.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
