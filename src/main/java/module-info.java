module com.example.endassigment {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;


    opens com.example.endassigment to javafx.fxml;
    exports com.example.endassigment;


    opens com.example.endassigment.controllers to javafx.fxml;

    exports com.example.endassigment.model;
    opens com.example.endassigment.model to com.fasterxml.jackson.databind, javafx.fxml;

}