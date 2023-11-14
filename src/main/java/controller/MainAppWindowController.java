package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.User;
import application.Users;
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

public class MainAppWindowController extends Stage implements Initializable {

    Users users;
    User user;

    @FXML
    ListView<String> AlbumView;

    @FXML
    TextArea PhotoInfoTextArea;

    private ObservableList<String> obsList;
    /**
     * searchByTags - search for a photo by tags.
     * @param event
     */
    public void searchByTags(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/SearchByTagWindow.fxml"));
            Parent root = (Parent) loader.load();

            SearchByTagsController controller = loader.<SearchByTagsController>getController();
            controller.loadUser(user);
            controller.loadUsers(users);

            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchByDate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/SearchByDateWindow.fxml"));
            Parent root = (Parent) loader.load();

            SearchByDateController controller = loader.<SearchByDateController>getController();
            controller.loadUser(user);
            controller.loadUsers(users);

            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAlbum(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/CreateAlbumWindow.fxml"));
            Parent root = (Parent) loader.load();

            CreateAlbumController controller = loader.<CreateAlbumController>getController();
            controller.loadUser(user);
            controller.loadUsers(users);

            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAlbum() {

        String album = AlbumView.getSelectionModel().getSelectedItem();
        if (album == null) return;

        user.deleteAlbum(album);
        Users.store(users);
        view();
    }

    public void renameAlbumButton(ActionEvent event) {
        String album = AlbumView.getSelectionModel().getSelectedItem();

        if (album == null)
            return;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/RenameAlbumWindow.fxml"));
            Parent root = (Parent) loader.load();

            RenameAlbumController controller = loader.<RenameAlbumController>getController();
            controller.loadUser(user);
            controller.loadUsers(users);
            controller.loadAlbum(user.getAlbum(album));

            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openAlbum(ActionEvent event) {

        String album = AlbumView.getSelectionModel().getSelectedItem();
        if (album == null)
            return;

        try {
            // load ViewAlbumWindow
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OpenAlbumWindow.fxml"));
            Parent root = (Parent) loader.load();

            OpenAlbumController controller = loader.<OpenAlbumController>getController();
            controller.loadUser(user);
            controller.loadUsers(users);
            controller.loadAlbum(user.getAlbum(album));
            controller.setup();

            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                user.getAlbum(albumname).findDates();
                PhotoInfoTextArea.setText("Name: "+user.getAlbum(albumname).getName()+"\n"+
                        "Size: "+user.getAlbum(albumname).getSize()+"\n"+
                        "Oldest Photo: "+user.getAlbum(albumname).getFirstDate()+"\n"+
                        "Newest Photo: "+user.getAlbum(albumname).getLastDate());
            }
        });

        users = Users.load();
    }
    /**
     * view - view the albums.
     */
    public void view() {
        obsList = FXCollections.observableArrayList(user.getAlbumNames());
        AlbumView.setItems(obsList);
    }

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadUsers(Users users) {
        this.users = users;

    }
}
