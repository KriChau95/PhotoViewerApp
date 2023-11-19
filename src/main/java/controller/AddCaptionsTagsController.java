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

public class AddCaptionsTagsController extends Stage {

    @FXML
    TextField CaptionTextField;
    @FXML
    TextField TagsTextField;

    private UserData userData;
    private User user;
    private Album album;
    private Photo photo;

    public void update(ActionEvent event) throws IOException, ClassNotFoundException {

        photo.setCaption(CaptionTextField.getText());

        String tag = TagsTextField.getText();

        if (!isValidTag(tag)) {
            return;
        } else {
            setTags(tag);//adds a photo tag
        }


        UserData.store(userData);

        loadMainWindow(event);

    }
    /**
     * setTags - sets the tags on a picture.
     * @param text The text of the tag.
     */
    private void setTags(String text) {
        if (text.contains(",")){
            String name = text.substring(0, text.indexOf(','));

            String value = text.substring(text.indexOf(',')+1);
            name = name.trim();
            value = value.trim();

            Tag toAdd = new Tag(name, value);

            photo.addTag(toAdd);
        }
    }
    /**
     * isValidTag - checks if tag is valid.
     * @param tag The tag being checked.
     * @return true if valid, false if not.
     */
    private boolean isValidTag(String tag) {
        if (tag.isEmpty()) return true;

        String[] tagParts = tag.split(",");

        // Check if there is at least one comma and the parts are not empty or whitespace only
        if (tagParts.length != 2 || tagParts[0].trim().isEmpty() || tagParts[1].trim().isEmpty()) {
            return false;
        }

        return true;
    }


    /**
     * cancel - go back to main window.
     * @param event
     */
    public void cancel(ActionEvent event) {
        loadMainWindow(event); //go back
    }

    /**
     * loadMainWindow - load the window one step before (OpenAlbumWindow).
     * @param event
     */
    public void loadMainWindow(ActionEvent event) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * loadUser - load the current user.
     * @param user
     */
    public void loadUser(User user) {
        this.user = user;
    }
    /**
     * loadUsers - load all the users.
     * @param userData
     */
    public void loadUsers(UserData userData) {
        this.userData = userData;
    }
    /**
     * loadAlbum - load the user's album.
     * @param album
     */
    public void loadAlbum(Album album) {
        this.album = album;
    }
    /**
     * loadPhoto - load the current photo.
     * @param photo
     */
    public void loadPhoto(Photo photo) {
        this.photo = photo;
    }
    /**
     * setCaption - set the caption on a photo.
     * @param caption
     */
    public void setCaption(String caption) {
        CaptionTextField.setText(caption);
    }

}
