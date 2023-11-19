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

public class MainAppWindowController implements Initializable {

    UserData userData;
    User user;

    @FXML
    ListView<String> AlbumView;

    @FXML
    TextArea PhotoInfoTextArea;

    private ObservableList<String> AlbumsObsList;

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

    public void setScene(ActionEvent event, Parent root){
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void searchByDate(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SearchByDateWindow.fxml"));
        Parent root = (Parent) loader.load();

        SearchByDateController controller = loader.<SearchByDateController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);

        setScene(event, root);
    }

    public void createAlbum(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CreateAlbumWindow.fxml"));
        Parent root = (Parent) loader.load();

        CreateAlbumController controller = loader.<CreateAlbumController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);

        setScene(event, root);
    }

    public void deleteAlbum() {

        String album = AlbumView.getSelectionModel().getSelectedItem();
        if (album == null) return;

        user.deleteAlbum(album);
        UserData.store(userData);
        view();
    }

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

    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
        Parent root = (Parent) loader.load();
        setScene(event, root);
    }

    public void quitApp(ActionEvent event){
        System.exit(0);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        PhotoInfoTextArea.setEditable(false);
        AlbumView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                String albumname = arg2;
                if (albumname == null) {
                    PhotoInfoTextArea.setText("");
                    return;
                }
                user.getAlbum(albumname).findDateRange();
                PhotoInfoTextArea.setText("Name: "+user.getAlbum(albumname).getName()+"\n"+
                        "Size: "+user.getAlbum(albumname).getSize()+"\n"+
                        "Earliest Photo: "+user.getAlbum(albumname).getEarliestDate()+"\n"+
                        "Latest Photo: "+user.getAlbum(albumname).getLatestDate());
            }
        });

        userData = UserData.load();
    }

    public void view() {
        AlbumsObsList = FXCollections.observableArrayList(user.getAlbumNames());
        AlbumView.setItems(AlbumsObsList);
    }

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadUsers(UserData userData) {
        this.userData = userData;
    }
}
