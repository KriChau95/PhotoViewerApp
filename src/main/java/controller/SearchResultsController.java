package controller;

import java.io.File;
import java.util.ArrayList;

import application.User;
import application.Users;
import application.Album;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SearchResultsController extends Stage{

    private Users users;
    private User user;
    private Album album;
    private int index,size;

    @FXML
    ImageView PhotoView;

    @FXML
    TextArea CaptionTextArea;

    @FXML
    ListView<String> TagsListView;

    @FXML
    TextField NewAlbumNameTextField;

    private ObservableList<String> obsList;

    public void add(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null) return; //no file selected
        Image image = new Image(file.toURI().toString());
        try {
            image.getHeight();
        } catch (Exception e) {
            System.out.println("Wrong type of file");
            return;
        }
        album.addImage(image);
        update(); //updates values of album

        //change view to new image
        updateLatestView();

    }

    public void remove() {
        if (index < 0) return;
        album.removeIndex(index); //removes photo
        update();
        updateLatestView();
    }

    public void createNewAlbum(ActionEvent event) {
        String albumname = NewAlbumNameTextField.getText();
        if (albumname.equals("") || user.albumExists(albumname)) {
            System.out.println("please enter an album name");
            return;
        }

        album.setName(albumname);
        user.addAlbum(album);
        Users.store(users);
        loadMainWindow(event);
    }

    public void left() {
        if (index == 0) index = size-1;
        else index--;
        updateLatestView();
    }

    public void right() {
        if (index+1 == size) index = 0;
        else index++;
        updateLatestView();
    }

    public void back(ActionEvent event) {
        loadMainWindow(event);
    }

    private void update() {
        size = album.getSize();
        index = size-1;
    }

    public void setup() {
        size = album.getPhotoList().size();

        index = album.getPhotoList().size()-1;
        CaptionTextArea.setEditable(false);
        updateLatestView();
    }

    public void updateLatestView() {

        if (index < 0) {
            PhotoView.setImage(null);
            CaptionTextArea.setText(album.getName()+ "\n" + "(no images to display)");
            tagsView();
            return;
        }

        PhotoView.setImage(album.getImage(index));

        String photoInfo = "";
        photoInfo += album.getName() + "\n";
        photoInfo += (index+1)+" out of "+size + "\n";
        photoInfo += album.getPhoto(index).getDate();
        photoInfo += "\n";
        photoInfo += album.getPhoto(index).getCaption();

        CaptionTextArea.setText(photoInfo);
        tagsView();
    }

    public void tagsView() {
        if (index < 0) {
            TagsListView.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
            return;
        }
        obsList = FXCollections.observableArrayList(album.getPhoto(index).getTagStrings());
        TagsListView.setItems(obsList);
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
