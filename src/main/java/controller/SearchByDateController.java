package controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import application.Album;
import application.Photo;
import application.User;
import application.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class SearchByDateController extends Stage{

    private Users users;
    private User user;
    private Album newAlbum;

    @FXML
    DatePicker LeftDatePicker;

    @FXML
    DatePicker RightDatePicker;

    public void search(ActionEvent event) {
        LocalDate localDate = LeftDatePicker.getValue();
        if (localDate == null) return;

        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date leftdate = Date.from(instant);
        Calendar leftCalendar = Calendar.getInstance();
        leftCalendar.setTime(leftdate);

        localDate = RightDatePicker.getValue();
        if (localDate == null) return;

        instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date rightdate= Date.from(instant);
        Calendar rightCalendar = Calendar.getInstance();
        rightCalendar.setTime(rightdate);


        newAlbum = new Album("");
        ArrayList<Album> albums = user.getAlbums();
        for (int i = 0; i < albums.size(); i++) {
            Album album = albums.get(i);
            for (int j = 0; j < album.getSize(); j++) {
                Photo photo = album.getPhoto(j);
                if (photo.getCalendar().before(rightCalendar) && photo.getCalendar().after(leftCalendar)) {
                    newAlbum.addPhoto(photo);
                }
            }
        }

        loadSearchWindow(event);
    }

    public void cancel(ActionEvent event) {
        loadMainWindow(event); //go back
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

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadUsers(Users users) {
        this.users = users;
    }
}