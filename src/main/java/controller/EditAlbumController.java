package controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import application.*;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class EditAlbumController {

    private UserData userData;
    private User user;
    private Album album;
    private int index,size;

    @FXML
    ImageView PhotoView;

    @FXML
    TextArea InfoArea;

    @FXML
    ListView<String> TagsListView;

    @FXML
    Text AlbumName;
    @FXML
    Text Caption;

    private ObservableList<String> obsList;


    public void addPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        File file = fileChooser.showOpenDialog(primaryStage);
        if (file == null) return; //no file selected
        Image image;
        try {
            image = new Image(file.toURI().toString());
        } catch (Exception e) {
            System.out.println("Error loading the image: " + e.getMessage());
            return;
        }

        if (image.isError()) {
            System.out.println("Wrong type of file or error loading the image");
            return;
        }

        album.addPhoto(new Photo(image));
        UserData.store(userData);
        update();
        updateLatestView();
    }

    public void remove() {
        if (index >= 0){
            album.removeIndex(index); //removes photo
            UserData.store(userData);
            update();
            updateLatestView();
        }
    }

    public void setScene(ActionEvent event, Parent root){
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void copy(ActionEvent event) throws IOException {
        Image image = PhotoView.getImage();
        if (image != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/CopyPhotoWindow.fxml"));
            Parent root = (Parent) loader.load();

            CopyPhotoController controller = loader.<CopyPhotoController>getController();
            controller.loadUser(user);
            controller.loadUsers(userData);
            controller.loadAlbum(album);
            controller.loadPhoto(album.getPhoto(index));
            controller.view();

            setScene(event, root);
        }
    }


    public void move(ActionEvent event) throws IOException {
        Image image = PhotoView.getImage();
        if (image != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/MovePhotoWindow.fxml"));
            Parent root = (Parent) loader.load();

            MovePhotoController controller = loader.<MovePhotoController>getController();
            controller.loadUser(user);
            controller.loadUsers(userData);
            controller.loadAlbum(album);
            controller.loadPhoto(album.getPhoto(index));
            controller.loadIndex(index);
            controller.view();

            setScene(event, root);
        }
    }


    public void left() {
        if (index == 0) {
            index = size-1;
        } else {
            index--;
        }
        updateLatestView();
    }

    public void right() {
        if (index+1 == size) {
            index = 0;
        } else{
            index++;
        }
        updateLatestView();
    }

    public void captionChangeTags(ActionEvent event) throws IOException {
        Image image = PhotoView.getImage();
        if (image != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AddCaptionsTagsWindow.fxml"));
            Parent root = (Parent) loader.load();

            AddCaptionsTagsController controller = loader.<AddCaptionsTagsController>getController();
            controller.loadUser(user);
            controller.loadUsers(userData);
            controller.loadAlbum(album);
            controller.loadPhoto(album.getPhoto(index));
            controller.setCaption(album.getPhoto(index).getCaption());

            setScene(event, root);
            updateLatestView();
        }
    }

    public void removeTag(ActionEvent event) {

        String tag = TagsListView.getSelectionModel().getSelectedItem();

        if (tag != null){
            String name = tag.substring(0, tag.indexOf(':'));
            String value = tag.substring(tag.indexOf(':')+1);

            Tag toRemove = new Tag(name, value);

            album.getPhoto(index).removeTag(toRemove);
            if (index < 0) {
                TagsListView.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
                return;
            }

            displayTags();
        }

    }


    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/AllPhotos.fxml"));
        Parent root = (Parent) loader.load();

        AllPhotosController controller = loader.<AllPhotosController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.loadAlbum(album);
        controller.setup();

        setScene(event, root);
    }


    private void update() {
        size = album.getSize();
        index = size-1;
    }


    public void setup() {
        size = album.getPhotoList().size();
        index = album.getPhotoList().size()-1;
        InfoArea.setEditable(false);
        updateLatestView();
    }

    public void updateLatestView() {

        AlbumName.setText(album.getName());

        if (index < 0) {
            PhotoView.setImage(null);
            InfoArea.setText(album.getName()+ "\n" + "(no images to display)");
            displayTags();
            return;
        }

        Caption.setText(album.getPhoto(index).getCaption());

        PhotoView.setImage(album.getImage(index));

        String photoInfo = "";
        photoInfo += (index+1)+" out of "+size + "\n";
        photoInfo += album.getPhoto(index).getDate().getTime().toString();
        photoInfo += "\n";

        InfoArea.setText(photoInfo);
        displayTags();
    }

    public void displayTags() {
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

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadUsers(UserData userData) {
        this.userData = userData;
    }

    public int rememberIndex(){
        return index;
    }

    public void loadAlbum(Album album) {
        this.album = album;
    }

}
