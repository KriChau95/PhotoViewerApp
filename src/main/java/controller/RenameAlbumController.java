package controller;

import java.io.IOException;
import application.Album;
import application.User;
import application.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RenameAlbumController extends Stage {

    @FXML
    Button CreateButton;
    @FXML
    Button CancelButton;
    @FXML
    TextField UsernameTextField;

    Users users;
    Album album;
    User user;

    public void rename(ActionEvent event) throws IOException, ClassNotFoundException {

        String albumname = UsernameTextField.getText();
        albumname = albumname.trim();
        if (albumname.equals("") || user.albumExists(albumname)) {
            System.out.println("please enter an album name");
            return;
        }

        album.setName(albumname);
        Users.store(users);

        loadMainWindow(event);

    }

    public void cancel(ActionEvent event) {

        loadMainWindow(event);

    }


    public void loadMainWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MainAppWindow.fxml"));
            Parent root = (Parent) loader.load();

            MainAppWindowController controller = loader.<MainAppWindowController>getController();
            controller.loadUser(user);
            controller.loadUsers(users);
            controller.view();

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

}
