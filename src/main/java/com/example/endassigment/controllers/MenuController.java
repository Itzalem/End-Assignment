package com.example.endassigment.controllers;

import com.example.endassigment.model.Element;
import com.example.endassigment.model.ElementDeserializer;
import com.example.endassigment.model.GameManager;
import com.example.endassigment.model.Quiz;
import com.example.endassigment.model.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.Collections.shuffle;

public class MenuController {
    @FXML
    public Label labelQuizLoaded;
    @FXML
    private Button btnStartQuiz;

    @FXML
    public void onLoadQuiz() {
        ///select file to load
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            ///existing but empty file
            if (selectedFile.length() == 0) {
                labelQuizLoaded.setText("Sorry, the file you selected is empty. Try a new one please.");
                labelQuizLoaded.setVisible(true);
                return;
            }

            try {
                ObjectMapper mapper = new ObjectMapper();

                ///for the factory deserializer
                SimpleModule module = new SimpleModule();
                module.addDeserializer(Element.class, new ElementDeserializer()); ///will use ElementFactory to create the right element type of object
                mapper.registerModule(module);

                Quiz quiz = mapper.readValue(selectedFile, Quiz.class);

                if (quiz.getPages() != null && quiz.getRandomQuestionCount() > 0 &&  ///if its not in the json file it will be 0
                        quiz.getRandomQuestionCount() <= quiz.getPages().size()) {

                    ///random selection of the questions
                    shuffle(quiz.getPages());

                    List<Page> randomQuestionSet = quiz.getPages().subList(0, quiz.getRandomQuestionCount());

                    ///only the random questions are going in the quiz now
                    quiz.setPages(new java.util.ArrayList<>(randomQuestionSet));
                }

                GameManager.getInstance().setCurrentQuiz(quiz);

                ///view updates and user can play
                btnStartQuiz.setDisable(false);
                labelQuizLoaded.setText("Ready to Play: " + GameManager.getInstance().getCurrentQuiz().getTitle());
                labelQuizLoaded.setVisible(true);
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
                labelQuizLoaded.setText("Error while loading the quiz file, please try again.");
                labelQuizLoaded.setVisible(true);
            }
        }
    }

    @FXML
    public void onStartQuiz(ActionEvent event) throws IOException {
        GameManager.getInstance().setQuizPlaying(true); ///to start the quiz

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/endassigment/Game.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}


