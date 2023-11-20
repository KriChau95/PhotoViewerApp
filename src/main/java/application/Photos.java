package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Photos Application
 * <p>
 * This is an application where multiple users can log in to save and view photos in albums.
 *
 * @author Krishaan Chaudhary & Preston Clawson
 * @date 11/20/2023
 */
public class Photos extends Application {
    /**
     * The {@code Photos} class is the entry point for the Photos88 application. It initializes the application
     * by displaying the login window on the primary stage.
     * <p>
     * The login window is loaded using JavaFX FXMLLoader from the Login.fxml file, and the primary stage is
     * configured with dimensions 700 x 550 pixels.
     *
     * @param stage The primary stage of the application where the login window will be displayed.
     * @throws IOException If an error occurs while loading the Login.fxml file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Photos88");
        FXMLLoader fxmlLoader = new FXMLLoader(Photos.class.getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700 , 550);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method launches the JavaFX application by calling
     * the launch method to initialize and display the login window.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}