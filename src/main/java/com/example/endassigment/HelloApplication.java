package com.example.endassigment;

import com.example.endassigment.model.GameManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Quizix Application");
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            if (!GameManager.getInstance().isQuizPlaying()) {
                return;
            }

            event.consume();

            ///new window to confirm exit
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to exit?");

            ButtonType exitBtn = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);

            ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(exitBtn, cancelBtn);

            Optional<ButtonType> result = alert.showAndWait();

            ///if exit, close the app
            if (result.isPresent() && result.get() == exitBtn) {
                Platform.exit();
                System.exit(0);
            }
        });

        stage.show();
    }
}

