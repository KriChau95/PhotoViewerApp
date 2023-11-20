package controller;

import java.io.IOException;

import application.Album;
import application.Photo;
import application.User;
import application.UserData;
import application.Tag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for handling the addition and update of captions and tags for a photo.
 */
public class AddCaptionsTagsController extends Stage {

    @FXML
    TextField CaptionTextField;
    @FXML
    TextField TagsTextField;

    private UserData userData;
    private User user;
    private Album album;
    private Photo photo;

    /**
     * Updates the caption and tags of the current photo and stores the changes in the UserData.
     *
     * @param event The ActionEvent triggering the update.
     * @throws IOException If an error occurs during the navigation to the main window.
     */
    public void update(ActionEvent event) throws IOException {

        photo.setCaption(CaptionTextField.getText());

        String text = TagsTextField.getText();

        if (!isValidTag(text)) {
            return;
        } else if (text.contains(",")){
            String name = (text.substring(0, text.indexOf(','))).trim();

            String value = (text.substring(text.indexOf(',')+1)).trim();

            Tag toAdd = new Tag(name, value);

            photo.addTag(toAdd);
        }

        UserData.store(userData);

        goBackToEditWindow(event);

    }

    /**
     * Checks if the provided tag is valid (non-empty and has the correct format).
     *
     * @param tag The tag string to be validated.
     * @return True if the tag is valid, false otherwise.
     */
    private boolean isValidTag(String tag) {
        if (tag.isEmpty()){
            return true;
        }
        String[] tagParts = tag.split(",");

        if (tagParts.length != 2 || tagParts[0].trim().isEmpty() || tagParts[1].trim().isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Navigates back to the EditAlbumWindow after updating captions and tags, and transfers control to the EditAlbumController
     *
     * @param event The ActionEvent triggering the navigation.
     * @throws IOException If an error occurs during the navigation to the main window.
     */
    public void goBackToEditWindow(ActionEvent event) throws IOException {
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
     * Loads the current user into the controller.
     *
     * @param user The User object to be loaded.
     */
    public void loadUser(User user) {
        this.user = user;
    }

    /**
     * Loads the current UserData into the controller.
     *
     * @param userData The UserData object to be loaded.
     */
    public void loadUsers(UserData userData) {
        this.userData = userData;
    }

    /**
     * Loads the current Album into the controller.
     *
     * @param album The Album object to be loaded.
     */
    public void loadAlbum(Album album) {
        this.album = album;
    }

    /**
     * Loads the current Photo into the controller.
     *
     * @param photo The Photo object to be loaded.
     */
    public void loadPhoto(Photo photo) {
        this.photo = photo;
    }

    /**
     * Sets the caption text field with the provided caption.
     *
     * @param caption The caption to be set in the text field.
     */
    public void setCaption(String caption) {
        CaptionTextField.setText(caption);
    }

    /**
     * Handles the "Save & Quit" button action, exiting the application.
     *
     * @param event The action event.
     */
    public void quitApp(ActionEvent event){
        System.exit(0);
    }

}
