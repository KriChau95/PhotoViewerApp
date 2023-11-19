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

    public void copy(ActionEvent event) throws IOException, ClassNotFoundException {
        String destinationAlbumName = DestinationAlbumList.getSelectionModel().getSelectedItem();

        if (destinationAlbumName != null){
            user.getAlbum(destinationAlbumName).addPhoto(photo);
            UserData.store(userData);

            goBackToMainWindow(event);
        }


    }

    public void goBackToMainWindow(ActionEvent event) throws IOException {
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

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadUsers(UserData userData) {
        this.userData = userData;

    }

    public void loadAlbum(Album album) {
        this.album = album;
    }

    public void loadPhoto(Photo photo) {
        this.photo = photo;
    }

    public void view() {
        obsList = FXCollections.observableArrayList(user.getAlbumNames());
        DestinationAlbumList.setItems(obsList);
    }

    public void quitApp(ActionEvent event){
        System.exit(0);
    }

}