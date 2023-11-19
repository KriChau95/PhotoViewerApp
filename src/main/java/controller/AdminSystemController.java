package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class AdminSystemController implements Initializable{

    @FXML
    private BorderPane root;
    @FXML
    Button CreateNewButton;
    @FXML
    Button DeleteUserButton;
    @FXML
    Button LogoutButton;
    @FXML
    Button QuitButton;
    @FXML
    ListView<String> UsersListView;

    private ObservableList<String> obsList;

    private UserData userData;

    public void createUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CreateNewUserWindow.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void deleteUser(ActionEvent e) {
        userData.delete(UsersListView.getSelectionModel().getSelectedItem());
        UserData.store(userData);

        obsList = FXCollections.observableArrayList(userData.getUsernames());
        UsersListView.setItems(obsList);
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void quitApp(ActionEvent event){
        System.exit(0);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userData = UserData.load();
        obsList = FXCollections.observableArrayList(userData.getUsernames());
        UsersListView.setItems(obsList);
        root.setPrefWidth(700);
        root.setPrefHeight(550);
    }
}
