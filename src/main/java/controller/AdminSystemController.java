package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Users;
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
import javafx.stage.Stage;
public class AdminSystemController extends Stage implements Initializable{

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

    private Users users;

    public void createNewUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/CreateNewUserWindow.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(ActionEvent e) {
        users.remove(UsersListView.getSelectionModel().getSelectedItem());
        Users.store(users);

        obsList = FXCollections.observableArrayList(users.getUsernames());
        UsersListView.setItems(obsList);
    }

    @FXML
    public void logout(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        users = Users.load();
        obsList = FXCollections.observableArrayList(users.getUsernames());
        UsersListView.setItems(obsList);
    }
}
