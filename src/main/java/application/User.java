package application;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Album> albumList;
    private String username;

    public User(String username) {
        this.username = username;
        albumList = new ArrayList<Album>();
    }

    public void addAlbum(String name) {
        if (albumExists(name)) return;
        albumList.add(new Album(name));
    }

    public void addAlbum(Album album) {
        if (albumExists(album.getName())) return;
        albumList.add(album);
    }
    public void renameAlbum(String album, String newName) {
        for (int i = 0; i < albumList.size();i++) {
            if (albumList.get(i).getName().equals(album)) {
                albumList.get(i).setName(newName);
                break;
            }
        }
    }

    public void deleteAlbum(String album) {
        for (int i = albumList.size() -1; i >=0 ; i--) {
            if (albumList.get(i).getName().equals(album)) {
                albumList.remove(i);
            }
        }
    }

    public boolean albumExists(String name) {
        if (albumList.isEmpty()) return false;

        for (int i = 0; i < albumList.size(); i++) {
            if (albumList.get(i) != null) {
                if (albumList.get(i).getName().equals(name))
                    return true;
            }
        }
        return false;
    }

    public String getUsername() { return username; }

    public ArrayList<Album> getAlbums() { return albumList; }

    public ArrayList<String> getAlbumNames() {
        ArrayList<String> arr = new ArrayList<String>();
        for (int i = 0; i < albumList.size(); i++) {
            arr.add(albumList.get(i).getName());
        }
        return arr;
    }

    public Album getAlbum(String name) {
        for (int i = 0; i < albumList.size(); i++) {
            if (albumList.get(i).getName().equals(name))
                return albumList.get(i);
        }
        return null;
    }

}
