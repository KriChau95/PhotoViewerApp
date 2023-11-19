package controller;

import java.io.IOException;

import application.Album;
import application.User;
import application.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RenameAlbumController {

    @FXML
    Button RenameButton;
    @FXML
    Button CancelButton;
    @FXML
    TextField AlbumNameTextField;

    UserData userData;
    Album album;
    User user;

    public void rename(ActionEvent event) throws IOException {

        String albumName = AlbumNameTextField.getText().trim();
        if (albumName.equals("") || user.albumExists(albumName)) {
            // potential welcome/error text
            return;
        }
        album.setName(albumName);
        UserData.store(userData);

        goBackToMainWindow(event);

    }

    public void goBackToMainWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainAppWindow.fxml"));
        Parent root = (Parent) loader.load();

        MainAppWindowController controller = loader.<MainAppWindowController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.view();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void quitApp(ActionEvent vent){
        System.exit(0);
    }

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadUsers(UserData userData) {
        this.userData = userData;

    }

    public void loadAlbum(Album album) {
        this.album = album;
    }

}
