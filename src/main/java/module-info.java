module photos {
    requires javafx.controls;
    requires javafx.fxml;

    opens application to javafx.fxml;
    opens controller to javafx.fxml;
    exports application;
    exports controller;
}