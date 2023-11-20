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

/**
 * The {@code SearchByDateController} class controls the user interface for searching photos by date.
 * It provides methods for searching photos within a specified date range, loading search results, returning to the main application window, and quitting the application.
 * @author Krishaan Chaudhary & Preston Clawson
 */
public class SearchByDateController extends Stage{

    private UserData userData;
    private User user;
    private Album resultAlbum;

    @FXML
    DatePicker StartDatePicker;
    @FXML
    DatePicker EndDatePicker;

    /**
     * Converts a {@code LocalDate} object to a {@code Calendar} object.
     *
     * @param ld The LocalDate object to convert.
     * @return The corresponding Calendar object.
     */
    public Calendar getCalendar(LocalDate ld){
        Instant startInstant = Instant.from(ld.atStartOfDay(ZoneId.systemDefault()));
        Date startingDate = Date.from(startInstant);
        Calendar result = Calendar.getInstance();
        result.setTime(startingDate);
        return result;
    }

    /**
     * Performs a search for photos within the specified date range and loads the search results window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Loads the search results window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Returns to the main application window.
     *
     * @param event The action event.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Sets the scene of the current stage with the provided root.
     *
     * @param event The action event triggering the scene change.
     * @param root  The root node of the new scene.
     */
    public void setScene(ActionEvent event, Parent root){
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles the "Quit Application" button action, exiting the application.
     *
     * @param event The action event.
     */
    public void quitApp(ActionEvent event){
        System.exit(0);
    }

    /**
     * Loads the user information into the controller.
     *
     * @param user The user object.
     */
    public void loadUser(User user) {
        this.user = user;
    }

    /**
     * Loads the user data into the controller.
     *
     * @param userData The user data object.
     */
    public void loadUsers(UserData userData) {
        this.userData = userData;
    }
}