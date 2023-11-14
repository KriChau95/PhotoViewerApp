package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.User;
import application.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Arrays;

public class LoginController extends Stage implements Initializable{
    @FXML
    TextField userInput = new TextField();
    String username = "";

    @FXML
    private Label welcomeText;

    private Users users;
    private User user;

    @FXML
    protected void onQuitButtonClick() {
        System.exit(0);
    }

    @FXML
    protected void onUserEntry(ActionEvent event) {
        username = userInput.getText();
        welcomeText.setText("Welcome user " + username + ".");
    }

    @FXML
    public void login(ActionEvent event) {
        String username = userInput.getText();
        if (username.equals("admin")) {
            admin(event);
        }
        else if (username.equals("")) {
            welcomeText.setText("Please enter a username");
        }
        else {
            user = users.getUser(username);

            if (user == null) {
                welcomeText.setText("User doesn't exist");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/MainAppWindow.fxml"));
                Parent root = (Parent) loader.load();

                MainAppWindowController controller = loader.<MainAppWindowController>getController();
                controller.loadUser(user);
                controller.loadUsers(users);
                controller.view();

                Scene scene = new Scene(root);
                Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void admin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AdminSystem.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        users = Users.load();
    }
}