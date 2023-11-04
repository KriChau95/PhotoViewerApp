module com.example.photos {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.photos to javafx.fxml;
    exports com.example.photos;
}