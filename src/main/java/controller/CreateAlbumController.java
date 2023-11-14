package controller;

import java.io.IOException;
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

public class CreateAlbumController extends Stage {

    @FXML
    Button CreateButton;
    @FXML
    Button CancelButton;
    @FXML
    TextField UsernameTextField;

    private Users users;
    private User user;


    public void createAlbum(ActionEvent event) throws IOException, ClassNotFoundException {

        String albumname = UsernameTextField.getText();
        albumname = albumname.trim();
        if (albumname.equals("") || user.albumExists(albumname)) {
            return;
        }
        albumname = albumname.trim();
        //users = Users.load();
        user.addAlbum(albumname);
        Users.store(users);

        loadMainWindow(event);

    }

    public void cancel(ActionEvent event) {
        loadMainWindow(event); //go back
    }

    public void loadMainWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MainAppWindow.fxml"));
            Parent root = (Parent) loader.load();
//
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

}
