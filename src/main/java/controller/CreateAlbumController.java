package controller;

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

import java.io.IOException;

/**
 * The {@code CreateAlbumController} class controls the user interface for creating a new album.
 * It provides functionality for creating an album, navigating back to the main application window, and quitting the application.
 */
public class CreateAlbumController {

    @FXML
    Button CreateButton;
    @FXML
    Button CancelButton;
    @FXML
    TextField AlbumNameTextField;
    @FXML
    Button QuitButton;

    private UserData userData;
    private User user;

    /**
     * Creates a new album using the provided album name, after checking to ensure that it is unique and non-empty.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void createAlbum(ActionEvent event) throws IOException {

        String albumName = AlbumNameTextField.getText().trim();
        if (albumName.equals("") || user.albumExists(albumName)) {
            return;
        }
        albumName = albumName.trim();
        user.addAlbum(albumName);
        UserData.store(userData);

        goBackToMainWindow(event);

    }

    /**
     * Navigates back to the main application window after creating an album.
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
     * Loads the current user for the controller.
     *
     * @param user The current user.
     */
    public void loadUser(User user) {
        this.user = user;
    }

    /**
     * Loads the UserData for the controller.
     *
     * @param userData The UserData.
     */
    public void loadUsers(UserData userData) {
        this.userData = userData;
    }

}
