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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class ResultsController {
    @FXML
    private TextFlow resultsFlow;

    @FXML
    public void initialize() {
        try {
            String quizId = GameManager.getInstance().getCurrentQuizId();
            File file = new File(quizId + "-results.json");

            resultsFlow.getChildren().clear();

            if (file.exists()) {
                if (file.length() == 0) {
                    resultsFlow.getChildren().add(new Text("Sorry, the results file is empty. Please restart the quiz"));
                    return;
                }
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                QuizResults quizResults = mapper.readValue(file, QuizResults.class);

                for (PlayerResult player : quizResults.getPlayersResults()) {
                    Text playerName = new Text("Player: " + player.getPlayerName() + "\n");
                    playerName.setStyle("-fx-font-weight: bold; -fx-fill: red; -fx-font-size: 15px;");

                    String resultsOverview = "Total Questions: " + player.getTotalQuestions() + "\n" +
                            "Correct Questions: " + player.getCorrectQuestions() + "\n" +
                            "Date: " + player.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n";

                    Text playerInfo = new Text(resultsOverview);

                    Text separator = new Text("-------------\n");
                    //separator.setStyle("-fx-fill: grey;");

                    resultsFlow.getChildren().addAll(playerName, playerInfo, separator);
                }
            }
            else {
                resultsFlow.getChildren().add(new Text("No file found"));
            }

        }
        catch (IOException ioe) {
            resultsFlow.getChildren().add(new Text("Error loading results, the file could be damaged or corrupted"));
            ioe.printStackTrace();
        }
    }

    public void onPlayAgainClicked(ActionEvent actionEvent) {
        try {
            GameManager.getInstance().resetGame();

            Parent root = FXMLLoader.load(getClass().getResource("/com/example/endassigment/Menu.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
