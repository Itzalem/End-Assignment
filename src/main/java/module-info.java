module com.example.endassigment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.endassigment to javafx.fxml;
    exports com.example.endassigment;
}