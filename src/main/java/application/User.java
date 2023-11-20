package application;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The {@code User} class represents a User of the Photos Application. Users can create and manage
 * albums to organize their photos.
 * <p>
 * This class implements the {@link Serializable} interface to allow for serialization.
 */
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Album> albumList;
    private String username;

    /**
     * User Constructor - Constructs a User with a given username.
     *
     * @param username The desired username of the user.
     */
    public User(String username) {
        this.username = username;
        albumList = new ArrayList<Album>();
    }

    /**
     * Adds an album with the given name to the user's album list.
     *
     * @param name The name of the album to add.
     */
    public void addAlbum(String name) {
        if (!albumExists(name)) {
            albumList.add(new Album(name));
        }
    }

    /**
     * Adds a pre-existing album to the user's album list.
     *
     * @param album The album to add.
     */
    public void addAlbum(Album album) {
        if (!albumExists(album.getName())) {
            albumList.add(album);
        }
    }

    /**
     * Deletes an album with the given name from the user's album list.
     *
     * @param album The name of the album to delete.
     */
    public void deleteAlbum(String album) {
        for (int i = albumList.size() - 1; i >= 0 ; i--) {
            if (albumList.get(i).getName().equals(album)) {
                albumList.remove(i);
            }
        }
    }

    /**
     * Checks if an album with the given name exists in the user's album list.
     *
     * @param name The name of the album to check for.
     * @return {@code true} if the album exists, {@code false} otherwise.
     */
    public boolean albumExists(String name) {
        if (albumList.isEmpty()){
            return false;
        }

        for (Album album : albumList) {
            if (album != null && album.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the list of albums associated with the user.
     *
     * @return The list of albums.
     */
    public ArrayList<Album> getAlbums() {
        return albumList;
    }

    /**
     * Gets the names of all albums associated with the user.
     *
     * @return The list of album names.
     */
    public ArrayList<String> getAlbumNames() {
        ArrayList<String> result = new ArrayList<String>();
        for (Album album : albumList) {
            result.add(album.getName());
        }
        return result;
    }

    /**
     * Gets a specific album from the user's album list based on its name.
     *
     * @param name The name of the album to retrieve.
     * @return The album with the specified name, or {@code null} if not found.
     */
    public Album getAlbum(String name) {
        for (Album album : albumList) {
            if (album.getName().equals(name))
                return album;
        }
        return null;
    }

    /**
     * Overriding the Object class equal method to check for equality between tags.
     *
     * @param o The object to compare to.
     * @return {@code true} if the users' usernames are equal, {@code false} otherwise.
     */
    public boolean equals(Object o){
        if (o == null || !(o instanceof User)){
            return false;
        }
        User other = (User) o;
        return this.username.equals(other.getUsername());
    }

}
