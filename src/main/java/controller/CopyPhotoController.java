package controller;

import java.io.IOException;

import application.Album;
import application.Photo;
import application.User;
import application.Users;
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

public class CopyPhotoController extends Stage {

    @FXML
    Button CreateButton;
    @FXML
    Button CancelButton;
    @FXML
    ListView<String> PossibleAlbumsListView;

    private Users users;
    private User user;
    private Album album;
    private Photo photo;

    private ObservableList<String> obsList;

    public void copy(ActionEvent event) throws IOException, ClassNotFoundException {
        String albumname = PossibleAlbumsListView.getSelectionModel().getSelectedItem();

        if (albumname == null)
            return;

        user.getAlbum(albumname).addPhoto(photo);
        Users.store(users);

        loadMainWindow(event);

    }

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

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadUsers(Users users) {
        this.users = users;

    }

    public void loadAlbum(Album album) {
        this.album = album;
    }

    public void loadPhoto(Photo photo) {
        this.photo = photo;

    }

    public void view() {
        obsList = FXCollections.observableArrayList(user.getAlbumNames());
        PossibleAlbumsListView.setItems(obsList);
    }

}
