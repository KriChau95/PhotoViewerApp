package controller;

import java.io.IOException;

import application.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateNewUserController extends Stage {

    @FXML
    Button CreateButton;
    @FXML
    Button CancelButton;
    @FXML
    TextField UsernameTextField;
    @FXML
    private Label ErrorText;

    Users users;

    public void createNewUser(ActionEvent event) throws IOException, ClassNotFoundException {

        String username = UsernameTextField.getText();
        username = username.trim();
        if (username.equals("")) {
            ErrorText.setText("Invalid: empty username");
            return;
        }
        if (username.equals("admin")){
            ErrorText.setText("Invalid username: admin");
            return;
        }

        users = Users.load();
        users.add(username);
        Users.store(users);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AdminSystem.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void cancel(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AdminSystem.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
