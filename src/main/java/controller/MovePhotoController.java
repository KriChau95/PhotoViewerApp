package controller;

import java.io.IOException;

import application.Album;
import application.Photo;
import application.User;
import application.UserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * The {@code MovePhotoController} class controls the user interface for moving a photo to another album.
 * It provides methods for handling the movement of a photo, loading the main application window, and quitting the application.
 * @author Krishaan Chaudhary & Preston Clawson
 */
public class MovePhotoController {

    @FXML
    Button CancelButton;
    @FXML
    ListView<String> DestinationAlbumList;

    private ObservableList<String> obsList;
    private UserData userData;
    private User user;
    private Album album;
    private Photo photo;
    private int index;

    private String destinationAlbumName;

    /**
     * Moves the selected photo to the destination album and updates the user data.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void move(ActionEvent event) throws IOException {

        destinationAlbumName = DestinationAlbumList.getSelectionModel().getSelectedItem();

        if (destinationAlbumName != null){
            user.getAlbum(destinationAlbumName).addPhoto(photo);
            album.removeIndex(index);

            UserData.store(userData);

            goBackToEditWindow(event);
        }

    }

    /**
     * Loads the edit application window after the photo has been moved.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void goBackToEditWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/EditAlbumWindow.fxml"));
        Parent root = (Parent) loader.load();

        EditAlbumController controller = loader.<EditAlbumController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.loadAlbum(user.getAlbum(destinationAlbumName));
        controller.moveSetup();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Navigates back to the edit application window after cancelling the move photo operation.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void goBackToEditWindowCancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/EditAlbumWindow.fxml"));
        Parent root = (Parent) loader.load();

        EditAlbumController controller = loader.<EditAlbumController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.loadAlbum(album);
        controller.setup();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
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

    /**
     * Loads the photo information into the controller.
     *
     * @param photo The photo object.
     */
    public void loadPhoto(Photo photo) {
        this.photo = photo;
    }

    /**
     * Loads the index of the photo into the controller.
     *
     * @param index The index of the photo in the album.
     */
    public void loadIndex(int index) {
        this.index = index;
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
     * Updates the view with the available destination albums.
     */
    public void view() {
        obsList = FXCollections.observableArrayList(user.getAlbumNames());
        DestinationAlbumList.setItems(obsList);
    }

}
