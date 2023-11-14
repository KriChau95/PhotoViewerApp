package controller;

import java.io.IOException;

import application.Album;
import application.Photo;
import application.User;
import application.Users;
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

    private Users users;
    private User user;
    private Album album;
    private Photo photo;

    /**
     * update - the create button is pressed, and captions and tags are updated.
     * @param event
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void update(ActionEvent event) throws IOException, ClassNotFoundException {

        photo.setCaption(CaptionTextField.getText());

        String tag = TagsTextField.getText();

        if (!isValidTag(tag)) {
            return;
        }
        setTags(tag);//adds a photo tag

        Users.store(users);

        loadMainWindow(event);

    }
    /**
     * setTags - sets the tags on a picture.
     * @param text The text of the tag.
     */
    private void setTags(String text) {
        String name = text.substring(0, text.indexOf(','));

        String value = text.substring(text.indexOf(',')+1);
        name = name.trim();
        value = value.trim();

        photo.addTag(name, value);
    }
    /**
     * isValidTag - checks if tag is valid.
     * @param tag The tag being checked.
     * @return true if valid, false if not.
     */
    private boolean isValidTag(String tag) {
        if (tag.length() == 0) return true;

        if (tag.indexOf(',') == -1) return false;

        if (tag.indexOf(',')+1 == tag.length()) return false;

        if (tag.substring(tag.indexOf(',')+1).indexOf(',') != -1) return false;

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
            loader.setLocation(getClass().getResource("/fxml/OpenAlbumWindow.fxml"));
            Parent root = (Parent) loader.load();

            OpenAlbumController controller = loader.<OpenAlbumController>getController();
            controller.loadUser(user);
            controller.loadUsers(users);
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
     * @param users
     */
    public void loadUsers(Users users) {
        this.users = users;
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
