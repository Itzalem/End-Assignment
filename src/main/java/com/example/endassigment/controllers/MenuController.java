package com.example.endassigment.controllers;

import com.example.endassigment.model.GameManager;
import com.example.endassigment.model.Quiz;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MenuController {
    @FXML
    private Button btnStartQuiz;

    @FXML
    public void onLoadQuiz() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Quiz quiz = mapper.readValue(selectedFile, Quiz.class);

                GameManager.getInstance().setCurrentQuiz(quiz);

                btnStartQuiz.setDisable(false);
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
                System.out.println("Error while loading JSON file.");
            }
        }
    }

    @FXML
    public void onStartQuiz(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/endassigment/Game.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}


