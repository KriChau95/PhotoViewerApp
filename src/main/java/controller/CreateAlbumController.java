package controller;

import application.User;
import application.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAlbumController {

    @FXML
    Button CreateButton;
    @FXML
    Button CancelButton;
    @FXML
    TextField AlbumNameTextField;
    @FXML
    Button QuitButton;

    private UserData userData;
    private User user;


    public void createAlbum(ActionEvent event) throws IOException {

        String albumname = AlbumNameTextField.getText();
        albumname = albumname.trim();
        if (albumname.equals("") || user.albumExists(albumname)) {
            return;
        }
        albumname = albumname.trim();
        user.addAlbum(albumname);
        UserData.store(userData);

        goBackToMainWindow(event);

    }

    public void goBackToMainWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainAppWindow.fxml"));
        Parent root = (Parent) loader.load();

        MainAppWindowController controller = loader.<MainAppWindowController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.view();

        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void quitApp(ActionEvent event){
        System.exit(0);
    }

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadUsers(UserData userData) {
        this.userData = userData;
    }

}
