package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The {@code EditAlbumController} class controls the editing of an album.
 * It provides methods for adding photos, removing photos, copying/moving photos,
 * changing captions and tags, navigating through photos, and updating the view.
 */
public class EditAlbumController {

    private UserData userData;
    private User user;
    private Album album;
    private int index,size;

    @FXML
    ImageView PhotoView;
    @FXML
    TextArea InfoArea;
    @FXML
    ListView<String> TagsListView;
    @FXML
    Text AlbumName;
    @FXML
    Text Caption;

    private ObservableList<String> obsList;

    /**
     * Adds a photo to the album. Opens up a FileChooser to let the user select a Photo
     *
     * @param event The action event triggering the addition of a photo.
     */
    public void addPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null) return; //no file selected
        Image image;
        try {
            image = new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (image.isError()) {
            return;
        }
        if (isGifFile(file)){
            album.addPhoto(new Photo(file));
        } else {
            album.addPhoto(new Photo(image));
        }
        UserData.store(userData);
        update();
        updateLatestView();
    }

    /**
     * Removes the selected photo from the album.
     */
    public void remove() {
        if (size > 0){
            album.removeIndex(index); //removes photo
            UserData.store(userData);
            removePhotoUpdate();
            updateLatestView();
        }
    }

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
     * Switches to the copy photo window, and transfers control to the CopyPhotoWindowController
     *
     * @param event The action event triggering the switch.
     * @throws IOException If an I/O error occurs.
     */
    public void copy(ActionEvent event) throws IOException {
        Image image = PhotoView.getImage();
        if (image != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/CopyPhotoWindow.fxml"));
            Parent root = (Parent) loader.load();

            CopyPhotoController controller = loader.<CopyPhotoController>getController();
            controller.loadUser(user);
            controller.loadUsers(userData);
            controller.loadAlbum(album);
            controller.loadPhoto(album.getPhoto(index));
            controller.view();

            setScene(event, root);
        }
    }

    /**
     * Checks if the given File corresponds to a GIF file based on its file extension.
     *
     * @param file The File object to be checked.
     * @return True if the file is a GIF, false otherwise.
     */
    private boolean isGifFile(File file) {
        String fileName = file.getName();
        String extension = "";

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }

        return extension.equals("gif");
    }


    /**
     * Switches to the move photo window and transfers control to the MovePhotoController
     *
     * @param event The action event triggering the switch.
     * @throws IOException If an I/O error occurs.
     */
    public void move(ActionEvent event) throws IOException {
        Image image = PhotoView.getImage();
        if (image != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MovePhotoWindow.fxml"));
            Parent root = (Parent) loader.load();

            MovePhotoController controller = loader.<MovePhotoController>getController();
            controller.loadUser(user);
            controller.loadUsers(userData);
            controller.loadAlbum(album);
            controller.loadPhoto(album.getPhoto(index));
            controller.loadIndex(index);
            controller.view();

            setScene(event, root);
        }
    }


    /**
     * Navigates to the previous photo in the album.
     */
    public void left() {
        if (index == 0) {
            index = size-1;
            album.setCurrentIndex(index);
        } else {
            index--;
            album.setCurrentIndex(index);
        }
        updateLatestView();
    }

    /**
     * Navigates to the next photo in the album.
     */
    public void right() {
        if (index+1 == size) {
            index = 0;
            album.setCurrentIndex(index);
        } else{
            index++;
            album.setCurrentIndex(index);
        }
        updateLatestView();
    }

    /**
     * Switches to the caption and tags window for editing and transfers control to the AddCaptionsTagsWindow controller
     *
     * @param event The action event triggering the switch.
     * @throws IOException If an I/O error occurs.
     */
    public void captionChangeTags(ActionEvent event) throws IOException {
        Image image = PhotoView.getImage();
        if (image != null){
            int currentIndex = index;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AddCaptionsTagsWindow.fxml"));
            Parent root = (Parent) loader.load();

            AddCaptionsTagsController controller = loader.<AddCaptionsTagsController>getController();
            controller.loadUser(user);
            controller.loadUsers(userData);
            controller.loadAlbum(album);
            controller.loadPhoto(album.getPhoto(index));
            controller.setCaption(album.getPhoto(index).getCaption());

            setScene(event, root);

            index = currentIndex;
            updateLatestView();
        }
    }

    /**
     * Removes the selected tag from the current photo.
     *
     * @param event The action event triggering the removal of the tag.
     */
    public void removeTag(ActionEvent event) {

        String tag = TagsListView.getSelectionModel().getSelectedItem();

        if (tag != null){
            String name = tag.substring(0, tag.indexOf(':'));
            String value = tag.substring(tag.indexOf(':')+1);

            Tag toRemove = new Tag(name, value);

            album.getPhoto(index).removeTag(toRemove);
            if (index < 0) {
                TagsListView.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
                return;
            }

            displayTags();
        }

    }


    /**
     * Navigates back to the all photos window.
     *
     * @param event The action event triggering the switch.
     * @throws IOException If an I/O error occurs.
     */
    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AllPhotos.fxml"));
        Parent root = (Parent) loader.load();

        AllPhotosController controller = loader.<AllPhotosController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.loadAlbum(album);
        controller.setup();

        setScene(event, root);
    }


    /**
     * Updates the size and index of the album.
     */
    private void update() {
        size = album.getSize();
        index = size-1;
        album.setCurrentIndex(index);
    }

    /**
     * Updates the size and index of the album in the case that a photo is removed, so that
     * the user stays in the same point in the album slideshow
     */
    private void removePhotoUpdate(){
        size = album.getSize();
        int prevIndex = album.getCurrentIndex();
        if (prevIndex == size){
            index = size - 1;
            album.setCurrentIndex(index);
        } else {
            index = prevIndex;
        }

    }


    /**
     * Initializes the controller with the current album and sets up the view.
     */
    public void setup() {
        size = album.getPhotoList().size();
        index = album.getCurrentIndex();
        InfoArea.setEditable(false);
        updateLatestView();
    }

    /**
     * Sets up the view for pasting photos. Adjusts the index to the end of the destination album.
     */
    public void pasteSetup(){
        size = album.getPhotoList().size();
        index = size - 1;
        InfoArea.setEditable(false);
        updateLatestView();
    }

    /**
     * Sets up the view for moving photos. Adjusts the index to the end of the destination album.
     */
    public void moveSetup(){
        size = album.getPhotoList().size();
        index = size - 1;
        InfoArea.setEditable(false);
        updateLatestView();
    }

    /**
     * Updates the view with the latest photo information.
     */
    public void updateLatestView() {

        AlbumName.setText(album.getName());

        if (size == 0) {
            PhotoView.setImage(null);
            InfoArea.setText(album.getName()+ "\n" + "(no images to display)");
            displayTags();
            Caption.setText("");
            return;
        }

        Caption.setText(album.getPhoto(index).getCaption());

        PhotoView.setImage(album.getImage(index));

        String photoInfo = "";
        photoInfo += (index+1)+" out of "+size + "\n";
        photoInfo += album.getPhoto(index).getDate().getTime().toString();
        photoInfo += "\n";

        InfoArea.setText(photoInfo);
        displayTags();
    }

    /**
     * Displays the tags of the current photo in the list view.
     */
    public void displayTags() {
        if (size == 0) {
            TagsListView.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
            return;
        }
        ArrayList<String> tagStringList = new ArrayList<String>();
        for (Tag t : album.getPhoto(index).getTagList()){
            tagStringList.add(t.toString());
        }
        obsList = FXCollections.observableArrayList(tagStringList);
        TagsListView.setItems(obsList);
    }

    /**
     * Logs out the user and switches to the login window.
     *
     * @param event The action event triggering the logout.
     * @throws IOException If an I/O error occurs.
     */
    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
        Parent root = (Parent) loader.load();
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
     * Loads the user object into the controller.
     *
     * @param user The user object.
     */
    public void loadUser(User user) {
        this.user = user;
    }

    /**
     * Loads user data into the controller.
     *
     * @param userData The user data object.
     */
    public void loadUsers(UserData userData) {
        this.userData = userData;
    }

    /**
     * Loads the current album into the controller.
     *
     * @param album The current album.
     */
    public void loadAlbum(Album album) {
        this.album = album;
    }

}
