package controller;

import java.io.IOException;

import application.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

/**
 * The {@code AllPhotosController} class controls the user interface for viewing all photos in an album in a grid view.
 * It provides functionality for navigating back, editing the album, logging out, and quitting the application.
 */
public class AllPhotosController {

    private UserData userData;
    private User user;
    private Album album;
    private int index;
    private int size;
    @FXML
    Text AlbumNameText;
    @FXML
    TilePane photoGrid;

    /**
     * Sets the scene of the current stage with the provided root.
     *
     * @param event The action event triggering the scene change.
     * @param root  The root node of the new scene.
     */
    public void setScene(ActionEvent event, Parent root){
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles the "Back" button action, returning to the main application window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void back(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainAppWindow.fxml"));
        Parent root = (Parent) loader.load();

        MainAppWindowController controller = loader.<MainAppWindowController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.view();

        setScene(event, root);
    }

    /**
     * Handles the "Edit Album" button action, navigating to the Edit Album window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
    public void editAlbum(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/EditAlbumWindow.fxml"));
        Parent root = (Parent) loader.load();

        EditAlbumController controller = loader.<EditAlbumController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.loadAlbum(album);
        controller.setup();

        setScene(event, root);
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

        setScene(event, root);
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
     * Initializes the controller, setting up the photo grid for the current album.
     */
    public void setup() {
        size = album.getPhotoList().size();
        index = album.getPhotoList().size()-1;
        updatePhotoGrid();
    }

    /**
     * Updates the photo grid based on the current album's photos.
     */
    public void updatePhotoGrid() {
        AlbumNameText.setText(album.getName());
        photoGrid.getChildren().clear();

        for (int i = 0; i < album.getSize(); i++) {
            Image image = album.getImage(i);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            Text captionText = new Text(album.getPhoto(i).getCaption());
            captionText.setWrappingWidth(100);

            VBox photoBox = new VBox(imageView, captionText);
            photoBox.setAlignment(Pos.CENTER);

            photoGrid.getChildren().add(photoBox);
        }
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
}
