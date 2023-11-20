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
/**
 * The {@code AdminSystemController} class controls the admin system interface, allowing the
 * administrator to create new users, delete existing users, logout, and quit the application.
 * <p>
 * @author Krishaan Chaudhary & Preston Clawson
 * This class implements the {@link Initializable} interface to initialize the controller.
 */
public class AdminSystemController implements Initializable{

    @FXML
    BorderPane root;
    @FXML
    Button CreateNewButton;
    @FXML
    Button DeleteUserButton;
    @FXML
    Button LogoutButton;
    @FXML
    ListView<String> UsersListView;

    private ObservableList<String> obsList;
    private UserData userData;

    /**
     * Handles the "Create New User" button action, navigating to the Create New User window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void createUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CreateNewUserWindow.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles the "Delete User" button action, deleting the selected user.
     */
    public void deleteUser(ActionEvent e) {
        userData.delete(UsersListView.getSelectionModel().getSelectedItem());
        UserData.store(userData);

        obsList = FXCollections.observableArrayList(userData.getUsernames());
        UsersListView.setItems(obsList);
    }

    /**
     * Handles the "Logout" button action, returning to the login window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles the "Quit" button action, exiting the application.
     *
     * @param event The action event.
     */
    public void quitApp(ActionEvent event){
        System.exit(0);
    }

    /**
     * Initializes the controller, loading user data, setting up the user list view, and setting a preferred window size.
     *
     * @param arg0 The URL.
     * @param arg1 The ResourceBundle.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        userData = UserData.load();
        obsList = FXCollections.observableArrayList(userData.getUsernames());
        UsersListView.setItems(obsList);
        root.setPrefWidth(700);
        root.setPrefHeight(550);
    }
}
