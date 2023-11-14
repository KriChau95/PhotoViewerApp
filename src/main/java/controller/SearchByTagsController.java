package controller;

import java.util.ArrayList;
import application.Album;
import application.Photo;
import application.Tag;
import application.User;
import application.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchByTagsController extends Stage{

    private Users users;
    private User user;
    private Album newAlbum;

    private ArrayList<Tag> tags;

    @FXML
    TextField TagsTextField;

    @FXML
    ListView<String> TagsListView;

    private ObservableList<String> obsList;

    public void search(ActionEvent event) {
        if (tags.size() == 0) return;

        newAlbum = new Album("");
        ArrayList<Album> albums = user.getAlbums();
        for (int i = 0; i < albums.size(); i++) {
            Album album = albums.get(i);
            for (int j = 0; j < album.getSize(); j++) {
                Photo photo = album.getPhoto(j);
                boolean photoAdded = false;
                for (int k = 0; k < photo.getTagList().size(); k++) {
                    if (photoAdded) break;
                    Tag tag = photo.getTagList().get(k);
                    for (int l = 0; l < tags.size(); l++) {
                        Tag otherTag = tags.get(l);
                        if (tag.getType().toLowerCase().equals(otherTag.getType().toLowerCase()) && tag.getValue().toLowerCase().equals(otherTag.getValue().toLowerCase())){
                            newAlbum.addPhoto(photo);
                            photoAdded = true;
                        }
                    }
                }

            }
        }

        loadSearchWindow(event);
    }

    public void cancel(ActionEvent event) {
        loadMainWindow(event); //go back
    }

    public void addTag(ActionEvent event) {
        String tag = TagsTextField.getText();

        tag = tag.trim();

        if (!isValidTag(tag)) return;

        String name = tag.substring(0, tag.indexOf(','));
        String value = tag.substring(tag.indexOf(',')+1);

        name = name.trim();
        value = value.trim();

        tags.add(new Tag(name,value));

        updateView();
    }

    public void removeTag(ActionEvent event) {
        String tag = TagsListView.getSelectionModel().getSelectedItem();
        if (tag == null) return;

        String name = tag.substring(0, tag.indexOf(','));
        String value = tag.substring(tag.indexOf(',')+1);

        for (int i=0; i < tags.size(); i++) {
            if (tags.get(i).getType().equals(name) && tags.get(i).getValue().equals(value)) {
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
        ArrayList<String> tags_to_strings = new ArrayList<String>();

        for (int i = 0; i < tags.size(); i++) {
            Tag tag = tags.get(i);
            tags_to_strings.add(tag.toString());
        }

        obsList = FXCollections.observableArrayList(tags_to_strings);
        TagsListView.setItems(obsList);
    }

    public void loadSearchWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/SearchResultsWindow.fxml"));
            Parent root = (Parent) loader.load();

            SearchResultsController controller = loader.<SearchResultsController>getController();
            controller.loadUser(user);
            controller.loadUsers(users);
            controller.loadAlbum(newAlbum);
            controller.setup();

            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void initialize() {
        tags = new ArrayList<Tag>();
        updateView();
    }

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadUsers(Users users) {
        this.users = users;
    }
}