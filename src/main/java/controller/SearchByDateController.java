package controller;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import application.Album;
import application.Photo;
import application.User;
import application.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class SearchByDateController extends Stage{

    private UserData userData;
    private User user;
    private Album resultAlbum;

    @FXML
    DatePicker StartDatePicker;

    @FXML
    DatePicker EndDatePicker;

    public Calendar getCalendar(LocalDate ld){
        Instant startInstant = Instant.from(ld.atStartOfDay(ZoneId.systemDefault()));
        Date startingDate = Date.from(startInstant);
        Calendar result = Calendar.getInstance();
        result.setTime(startingDate);
        return result;
    }

    public void search(ActionEvent event) throws IOException {

        LocalDate startDate = StartDatePicker.getValue();
        LocalDate endDate = EndDatePicker.getValue();

        if (startDate == null || endDate == null){
            return;
        }

        Calendar start = getCalendar(startDate);
        Calendar end = getCalendar(endDate);

        resultAlbum = new Album("");
        ArrayList<Album> albums = user.getAlbums();
        for (Album album : albums) {
            for (int i = 0; i < album.getSize(); i++) {
                Photo photo = album.getPhoto(i);
                if (photo.getDate().after(start) && photo.getDate().before(end)) {
                    resultAlbum.addPhoto(photo);
                }
            }
        }
        loadSearchResultsWindow(event);
    }

    public void loadSearchResultsWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SearchResultsWindow.fxml"));
        Parent root = (Parent) loader.load();

        SearchResultsController controller = loader.<SearchResultsController>getController();
        controller.loadUser(user);
        controller.loadUsers(userData);
        controller.loadAlbum(resultAlbum);
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