package controller;

import java.io.IOException;


import application.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
public class AllPhotosController {

    private UserData userData;
    private User user;
    private Album album;
    @FXML
    private Button BackButton;
    @FXML
    private Button QuitButton;
    @FXML
    private Button LogoutButton;
    private int index;
    private int size;

    @FXML
    TilePane photoGrid;

    public void back(ActionEvent event) throws IOException {

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

    public void editAlbum(ActionEvent event) throws IOException {
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

    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void quitApp(ActionEvent event){
        System.exit(0);
    }


    public void setup() {
        size = album.getPhotoList().size();
        index = album.getPhotoList().size()-1;
        updatePhotoGrid();
    }

    public void updatePhotoGrid() {
        photoGrid.getChildren().clear(); // Clear existing content


        for (int i = 0; i < album.getSize(); i++) {
            Image image = album.getImage(i);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100); // Adjust the size as needed
            imageView.setFitHeight(100);

            Text captionText = new Text(album.getPhoto(i).getCaption()); // Add this line for captions
            captionText.setWrappingWidth(100); // Adjust the width as needed

            // Add the image and captionText to a VBox
            VBox photoBox = new VBox(imageView, captionText);
            photoBox.setAlignment(Pos.CENTER);

            // Add VBox to TilePane
            photoGrid.getChildren().add(photoBox);
        }
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
}
