package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Photo;
import application.Tag;
import application.User;
import application.UserData;
import application.Album;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SearchByTagsController implements Initializable {

    private UserData userData;
    private User user;
    private Album newAlbum;
    @FXML
    private BorderPane root;

    private ArrayList<Tag> tags;

    @FXML
    TextField TagsTextField;

    @FXML
    ListView<String> TagsListView;

    private ObservableList<String> obsList;

    public void searchOneTag(ActionEvent event) throws IOException {
        if (tags.isEmpty()) return;

        newAlbum = new Album("");
        ArrayList<Album> albums = user.getAlbums();
        for (Album album : albums) {
            for (int j = 0; j < album.getSize(); j++) {
                Photo photo = album.getPhoto(j);
                boolean photoAdded = false;
                for (int k = 0; k < photo.getTagList().size(); k++) {
                    if (photoAdded) break;
                    Tag tag = photo.getTagList().get(k);
                    Tag targetTag = tags.get(0);
                    if (tag.getName().equalsIgnoreCase(targetTag.getName()) && tag.getValue().equalsIgnoreCase(targetTag.getValue())) {
                        newAlbum.addPhoto(photo);
                        photoAdded = true;
                    }
                }
            }
        }
        loadSearchResultsWindow(event);
    }

    public void searchAndTags(ActionEvent event) throws IOException {
        if (tags.isEmpty()) return;
        if (tags.size() !=2) return;

        Tag targetTagOne = tags.get(0);
        Tag targetTagTwo = tags.get(1);

        newAlbum = new Album("");
        ArrayList<Album> albums = user.getAlbums();
        for (Album album : albums) {
            for (int j = 0; j < album.getSize(); j++) {
                Photo photo = album.getPhoto(j);
                boolean hasTargetTagOne = false;
                boolean hasTargetTagTwo = false;
                for (int k = 0; k < photo.getTagList().size(); k++) {
                    Tag tag = photo.getTagList().get(k);
                    if(tag.getName().trim().equalsIgnoreCase(targetTagOne.getName().trim()) && tag.getValue().trim().equalsIgnoreCase(targetTagOne.getValue().trim())){
                        hasTargetTagOne = true;
                    }
                    if(tag.getName().trim().equalsIgnoreCase(targetTagTwo.getName().trim()) && tag.getValue().trim().equalsIgnoreCase(targetTagTwo.getValue().trim())){
                        hasTargetTagTwo = true;
                    }
                }
                if (hasTargetTagOne && hasTargetTagTwo) {
                    newAlbum.addPhoto(photo);
                }

            }
        }
        loadSearchResultsWindow(event);
    }

    public void searchOrTags(ActionEvent event) throws IOException {
        if (tags.isEmpty()) return;
        if (tags.size() !=2) return;

        Tag targetTagOne = tags.get(0);
        Tag targetTagTwo = tags.get(1);

        newAlbum = new Album("");
        ArrayList<Album> albums = user.getAlbums();
        for (Album album : albums) {
            for (int j = 0; j < album.getSize(); j++) {
                Photo photo = album.getPhoto(j);
                boolean hasTargetTagOne = false;
                boolean hasTargetTagTwo = false;
                for (int k = 0; k < photo.getTagList().size(); k++) {
                    Tag tag = photo.getTagList().get(k);
                    if(tag.getName().trim().equalsIgnoreCase(targetTagOne.getName().trim()) && tag.getValue().trim().equalsIgnoreCase(targetTagOne.getValue().trim())){
                        hasTargetTagOne = true;
                    }
                    if(tag.getName().trim().equalsIgnoreCase(targetTagTwo.getName().trim()) && tag.getValue().trim().equalsIgnoreCase(targetTagTwo.getValue().trim())){
                        hasTargetTagTwo = true;
                    }
                }
                if (hasTargetTagOne || hasTargetTagTwo) {
                    newAlbum.addPhoto(photo);
                }

            }
        }
        loadSearchResultsWindow(event);
    }

    public void addTag(ActionEvent event) {

        String tag = TagsTextField.getText().trim();

        if (!isValidTag(tag) || tags.size() ==2){
            return;
        }

        String name = (tag.substring(0, tag.indexOf(','))).trim();
        String value = (tag.substring(tag.indexOf(',')+1)).trim();

        Tag toAdd = new Tag(name, value);
        for (int i = 0; i < tags.size(); i++){
            if (tags.get(i).equals(toAdd)){
                return;
            }
        }

        tags.add(new Tag(name,value));
        updateView();
    }

    public void removeTag(ActionEvent event) {
        String tag = TagsListView.getSelectionModel().getSelectedItem();
        if (tag == null) return;

        String name = tag.substring(0, tag.indexOf(':'));
        String value = tag.substring(tag.indexOf(':')+1);

        for (int i=0; i < tags.size(); i++) {
            if (tags.get(i).getName().equals(name) && tags.get(i).getValue().equals(value)) {
                tags.remove(i);
            }
        }

        updateView();
    }

    private boolean isValidTag(String tag) {
        if (tag.length() == 0) return false;

        if (tag.indexOf(',') == -1) return false;

        if (tag.indexOf(',')+1 == tag.length()) return false;

        if (tag.substring(tag.indexOf(',')+1).indexOf(',') != -1) return false;

        return true;
    }

    public void updateView() {
        ArrayList<String> tagStringList = new ArrayList<String>();

        for (Tag tag : tags) {
            tagStringList.add(tag.toString());
        }

        obsList = FXCollections.observableArrayList(tagStringList);
        TagsListView.setItems(obsList);
    }

    public void loadSearchResultsWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SearchResultsWindow.fxml"));
        Parent root = (Parent) loader.load();

        SearchResultsController controller = loader.<SearchResultsController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.loadAlbum(newAlbum);
        controller.setup();

        setScene(event, root);
    }

    public void goBackToMainWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainAppWindow.fxml"));
        Parent root = (Parent) loader.load();

        MainAppWindowController controller = loader.<MainAppWindowController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.view();

        setScene(event, root);
    }

    public void setScene(ActionEvent event, Parent root){
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setup() {
        tags = new ArrayList<Tag>();
        updateView();
    }

    public void loadUser(User user) {
        this.user = user;
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        userData = UserData.load();
        root.setPrefWidth(900);
        root.setPrefHeight(550);
    }

    public void quitApp(ActionEvent event){
        System.exit(0);
    }

    public void loadUsers(UserData userData) {
        this.userData = userData;
    }
}