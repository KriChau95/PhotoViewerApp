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

/**
 * The {@code RenameAlbumController} class controls the user interface for renaming an album.
 * It provides methods for handling the renaming of an album, loading the main application window, and quitting the application.
 */
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

    /**
     * Renames the album and updates the user data.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void rename(ActionEvent event) throws IOException {

        String albumName = AlbumNameTextField.getText().trim();

        if (albumName.isEmpty() || user.albumExists(albumName)) {
            // potential welcome/error text
            return;
        }

        album.setName(albumName);
        UserData.store(userData);

        goBackToMainWindow(event);

    }

    /**
     * Loads the main application window after the album has been renamed.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Handles the "Quit Application" button action, exiting the application.
     *
     * @param event The action event.
     */
    public void quitApp(ActionEvent event){
        System.exit(0);
    }

    /**
     * Loads the user information into the controller.
     *
     * @param user The user object.
     */
    public void loadUser(User user) {
        this.user = user;
    }

    /**
     * Loads the user data into the controller.
     *
     * @param userData The user data object.
     */
    public void loadUsers(UserData userData) {
        this.userData = userData;

    }

    /**
     * Loads the album information into the controller.
     *
     * @param album The album object.
     */
    public void loadAlbum(Album album) {
        this.album = album;
    }

}
