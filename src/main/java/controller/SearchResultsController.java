package controller;

import java.io.IOException;
import java.util.ArrayList;

import application.Tag;
import application.User;
import application.UserData;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SearchResultsController extends Stage{

    private UserData userData;
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

    public void createNewAlbum(ActionEvent event) throws IOException {
        String albumname = NewAlbumNameTextField.getText();
        if (albumname.equals("") || user.albumExists(albumname)) {
            // maybe have some text popup
            return;
        }

        album.setName(albumname);
        user.addAlbum(album);
        UserData.store(userData);
        goBackToMainWindow(event);
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
        photoInfo += album.getPhoto(index).getDate().getTime().toString();
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
        ArrayList<String> tagStringList = new ArrayList<String>();
        for (Tag t : album.getPhoto(index).getTagList()){
            tagStringList.add(t.toString());
        }
        obsList = FXCollections.observableArrayList(tagStringList);
        TagsListView.setItems(obsList);
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

    public void loadAlbum(Album album) {
        this.album = album;
    }
}
