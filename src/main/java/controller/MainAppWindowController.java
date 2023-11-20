package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.User;
import application.UserData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


/**
 * The {@code MainAppWindowController} class controls the main application window.
 * It provides methods for handling user actions such as searching by tags or date,
 * creating, deleting, renaming, or opening albums, logging out, and quitting the application.
 * It also initializes the user interface components and updates the view with album information.
 * @author Krishaan Chaudhary & Preston Clawson
 */
public class MainAppWindowController implements Initializable {

    UserData userData;
    User user;

    @FXML
    ListView<String> AlbumView;
    @FXML
    TextArea PhotoInfoTextArea;

    private ObservableList<String> AlbumsObsList;

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
     * Initiates a search operation based on user-defined tags. Goes to the SearchyByTagWindow
     * and transfers control to the SearchByTagsController.
     *
     * @param event The action event triggering the search.
     * @throws IOException If an I/O error occurs.
     */
    public void searchByTags(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SearchByTagWindow.fxml"));
        Parent root = (Parent) loader.load();

        SearchByTagsController controller = loader.<SearchByTagsController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.setup();

        setScene(event, root);
    }


    /**
     * Initiates a search operation based on a date range. Goes to the SearchyByDateWindow
     * and transfers control to the SearchByDateController.
     *
     * @param event The action event triggering the search.
     * @throws IOException If an I/O error occurs.
     */
    public void searchByDate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SearchByDateWindow.fxml"));
        Parent root = (Parent) loader.load();

        SearchByDateController controller = loader.<SearchByDateController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);

        setScene(event, root);
    }

    /**
     * Switches to the create album window and transfers control to the CreateAlbumController.
     *
     * @param event The action event triggering the switch.
     * @throws IOException If an I/O error occurs.
     */
    public void createAlbum(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CreateAlbumWindow.fxml"));
        Parent root = (Parent) loader.load();

        CreateAlbumController controller = loader.<CreateAlbumController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);

        setScene(event, root);
    }

    /**
     * Deletes the selected album.
     */
    public void deleteAlbum() {

        String album = AlbumView.getSelectionModel().getSelectedItem();
        if (album == null) return;

        user.deleteAlbum(album);
        UserData.store(userData);
        view();
    }

    /**
     * Switches to the rename album window and transfers control to the RenameAlbumController.
     *
     * @param event The action event triggering the switch.
     * @throws IOException If an I/O error occurs.
     */
    public void renameAlbumButton(ActionEvent event) throws IOException {
        String album = AlbumView.getSelectionModel().getSelectedItem();

        if (album != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/RenameAlbumWindow.fxml"));
            Parent root = (Parent) loader.load();

            RenameAlbumController controller = loader.<RenameAlbumController>getController();
            controller.loadUser(user);
            controller.loadUsers(userData);
            controller.loadAlbum(user.getAlbum(album));

            setScene(event, root);
        }

    }

    /**
     * Opens the selected album in a grid format and transfers control to the AllPhotosController
     *
     * @param event The action event triggering the opening of the album.
     * @throws IOException If an I/O error occurs.
     */
    public void openAlbum(ActionEvent event) throws IOException {

        String album = AlbumView.getSelectionModel().getSelectedItem();

        if (album != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AllPhotos.fxml"));
            Parent root = (Parent) loader.load();

            AllPhotosController controller = loader.<AllPhotosController>getController();
            controller.loadUser(user);
            controller.loadUsers(userData);
            controller.loadAlbum(user.getAlbum(album));
            controller.setup();

            setScene(event, root);
        }

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
     * Initializes the user interface components and adds a listener for album selection changes.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        PhotoInfoTextArea.setEditable(false);
        AlbumView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String albumName = arg2;
                if (albumName == null) {
                    PhotoInfoTextArea.setText("");
                    return;
                }
                user.getAlbum(albumName).findDateRange();
                PhotoInfoTextArea.setText("Name: "+user.getAlbum(albumName).getName()+"\n"+
                        "Size: "+user.getAlbum(albumName).getSize()+"\n"+
                        "Earliest Photo: "+user.getAlbum(albumName).getEarliestDate()+"\n"+
                        "Latest Photo: "+user.getAlbum(albumName).getLatestDate());
            }
        });

        userData = UserData.load();
    }

    /**
     * Updates the view with the list of albums.
     */
    public void view() {
        AlbumsObsList = FXCollections.observableArrayList(user.getAlbumNames());
        AlbumView.setItems(AlbumsObsList);
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
}
