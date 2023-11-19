package controller;

import java.io.IOException;

import application.UserData;
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

    UserData userData;

    public void createNewUser(ActionEvent event) throws IOException {

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

        userData = UserData.load();
        userData.addUser(username);
        UserData.store(userData);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AdminSystem.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void cancel(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AdminSystem.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void quitApp(ActionEvent event){
        System.exit(0);
    }
}
