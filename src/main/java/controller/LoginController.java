package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.User;
import application.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;


public class LoginController implements Initializable {
    @FXML
    TextField userInput = new TextField();

    @FXML
    private VBox root;

    @FXML
    private Label ErrorText;

    private UserData userData;
    private User user;

    @FXML
    protected void onQuitButtonClick() {
        System.exit(0);
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        String username = userInput.getText().trim();
        if (username.equals("admin")) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AdminSystem.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } else if (username.equals("")) {
            ErrorText.setText("Please enter a username");
        }
        else {
            user = userData.getUser(username);

            if (user == null) {
                ErrorText.setText("User doesn't exist");
                return;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MainAppWindow.fxml"));
            Parent root = (Parent) loader.load();

            MainAppWindowController controller = loader.<MainAppWindowController>getController();
            controller.loadUser(user);
            controller.loadUsers(userData);
            controller.view();

            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        }
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userData = UserData.load();
        root.setPrefWidth(700);
        root.setPrefHeight(550);
    }
}