package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class LoginController {
    @FXML
    TextField userInput = new TextField();
    String username = "";

    @FXML
    private Label welcomeText;

    @FXML
    protected void onQuitButtonClick() {
        System.exit(0);
    }

    @FXML
    protected void onUserEntry(ActionEvent event) {
        username = userInput.getText();
        welcomeText.setText("Welcome user " + username + ".");
    }
}