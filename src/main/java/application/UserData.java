package application;

import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;

/**
 * The {@code UserData} class represents the data storage and management for user-related information,
 * including user accounts and their associated albums and photos.
 * <p>
 * @author Krishaan Chaudhary & Preston Clawson
 * This class implements the {@link Serializable} interface to allow for serialization.
 */
public class UserData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<User> usersList;
    private final static String usersFile = "userdata.ser";

    /**
     * UserData Constructor - Constructs a UserData object and initializes a stock user with a
     * default album stock containing 5 stock photos.
     */
    public UserData() {
        usersList = new ArrayList<User>();
        User stock = new User("stock");
        usersList.add(stock);
        stock.addAlbum("stock");
        Album stockAlbum = stock.getAlbum("stock");
        InputStream stream1 = null;
        InputStream stream2 = null;
        InputStream stream3 = null;
        InputStream stream4 = null;
        InputStream stream5 = null;
        try {
            stream1 = new FileInputStream("data/StockPhoto1.jpg");
            stream2 = new FileInputStream("data/StockPhoto2.jpg");
            stream3 = new FileInputStream("data/StockPhoto3.jpg");
            stream4 = new FileInputStream("data/StockPhoto4.jpg");
            stream5 = new FileInputStream("data/StockPhoto5.bmp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Image image1 = new Image(stream1);
        Image image2 = new Image(stream2);
        Image image3 = new Image(stream3);
        Image image4 = new Image(stream4);
        Image image5 = new Image(stream5);
        stockAlbum.addPhoto(new Photo(image1));
        stockAlbum.addPhoto(new Photo(image2));
        stockAlbum.addPhoto(new Photo(image3));
        stockAlbum.addPhoto(new Photo(image4));
        stockAlbum.addPhoto(new Photo(image5));
        store(this);
    }

    /**
     * Gets a specific user based on their username.
     *
     * @param username The username of the user to retrieve.
     * @return The user with the specified username, or {@code null} if not found.
     */
    public User getUser(String username) {
        for (User user : usersList) {
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    /**
     * Gets the list of users stored in UserData.
     *
     * @return The list of users.
     */
    public ArrayList<User> getUserList() {
        return usersList;
    }

    /**
     * Gets the usernames of all users stored in UserData.
     *
     * @return The list of usernames.
     */
    public ArrayList<String> getUsernames() {
        ArrayList<User> users = getUserList();
        ArrayList<String> usernames = new ArrayList<String>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    /**
     * Adds a new user with the given username to UserData.
     *
     * @param username The username of the new user.
     */
    public void addUser(String username) {

        if (usersList.isEmpty()){
            usersList.add(new User(username));
            return;
        }

        for (User user : usersList) {
            if (user.getUsername().equals(username)) {
                return;
            }
        }

        usersList.add(new User(username));
    }

    /**
     * Deletes a user with the specified username from UserData.
     *
     * @param username The username of the user to delete.
     */
    public void delete(String username) {
        for (int i = usersList.size()-1; i >=0; i--) {
            if (usersList.get(i).getUsername().equals(username)) {
                usersList.remove(i);
                break;
            }
        }
    }

    /**
     * Stores the current state of UserData to a file.
     *
     * @param userData The UserData object to store.
     */
    public static void store(UserData userData){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile, false));
            oos.writeObject(userData);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads UserData from a file. If the file does not exist, a new UserData object is created and stored.
     *
     * @return The loaded or newly created UserData object.
     */
    public static UserData load() {
        UserData userData = null;
        try {
            FileInputStream filestream = new FileInputStream(usersFile);
            ObjectInputStream ois = new ObjectInputStream(filestream);
            Object object = ois.readObject();
            userData = (UserData) object;
            ois.close();
        } catch (Exception e) {
            userData = new UserData();
            store(userData);
        }
        return userData;
    }
}
