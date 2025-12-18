package com.example.endassigment.controllers;

import com.example.endassigment.model.Quiz;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class MenuController {

    @FXML
    public void onLoadQuiz() {
        // 1. Abrir el selector de archivos (Lesson 02 - Challenge)
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // 2. Usar Jackson para leer (Lesson 03 - Solution)
            ObjectMapper mapper = new ObjectMapper();
            try {
                // En lugar de getResourceAsStream (que es para archivos internos),
                // usas el archivo que el usuario eligió.
                Quiz quiz = mapper.readValue(selectedFile, Quiz.class);

                System.out.println("Quiz cargado: " + quiz.getTitle());
                // TODO: Guardar este objeto 'quiz' en tu GameManager

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }



}
