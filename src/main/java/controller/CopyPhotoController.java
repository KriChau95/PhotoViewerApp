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
 * The {@code CopyPhotoController} class controls the user interface for copying a photo to another album.
 * It provides functionality for selecting the destination album, copying the photo, and navigating back to the main application window.
 */
public class CopyPhotoController {

    @FXML
    Button CancelButton;
    @FXML
    ListView<String> DestinationAlbumList;

    private UserData userData;
    private User user;
    private Album album;
    private Photo photo;
    private ObservableList<String> obsList;

    private String destinationAlbumName;

    /**
     * Gets the name of the destination album.
     *
     * @return The name of the destination album.
     */
    public String getDestinationAlbumName(){
        return destinationAlbumName;
    }

    /**
     * Copies the selected photo to the chosen destination album.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void copy(ActionEvent event) throws IOException {
        destinationAlbumName = DestinationAlbumList.getSelectionModel().getSelectedItem();

        if (destinationAlbumName != null){
            user.getAlbum(destinationAlbumName).addPhoto(photo);
            UserData.store(userData);

            goBackToEditWindow(event);
        }
    }

    /**
     * Navigates back to the edit application window after copying the photo.
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
        controller.pasteSetup();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
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

    /**
     * Loads the current album for the controller.
     *
     * @param album The current album.
     */
    public void loadAlbum(Album album) {
        this.album = album;
    }

    /**
     * Loads the current photo for the controller.
     *
     * @param photo The current photo.
     */
    public void loadPhoto(Photo photo) {
        this.photo = photo;
    }

    /**
     * Initializes the view by populating the destination album list.
     */
    public void view() {
        obsList = FXCollections.observableArrayList(user.getAlbumNames());
        DestinationAlbumList.setItems(obsList);
    }

    /**
     * Handles the "Quit Application" button action, exiting the application.
     *
     * @param event The action event.
     */
    public void quitApp(ActionEvent event){
        System.exit(0);
    }

}
