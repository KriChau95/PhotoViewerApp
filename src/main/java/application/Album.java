package application;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.image.Image;

public class Album implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private ArrayList<Photo> photoList;
    private String name;
    private int numPhotos;

    private Photo first, last; //for keeping track of the oldest and newest photos in the album

    public Album(String name) {
        this.name = name;
        photoList = new ArrayList<Photo>();
        numPhotos = 0;
        first = null;
        last = null;
    }

    public void findDates() {
        findFirst();
        findLast();
    }

    public void findFirst() {
        if (photoList.isEmpty()) {
            first = null;
            return;
        }

        first = photoList.get(0);
        Calendar first = photoList.get(0).getCalendar();
        for (int i = 1 ;i < photoList.size(); i++) {
            if (photoList.get(i).getCalendar().before(first)) {
                first = photoList.get(i).getCalendar();
                this.first = photoList.get(i);
            }
        }
    }

    public void findLast() {
        if (photoList.isEmpty()) {
            last = null;
            return;
        }

        last = photoList.get(0);
        Calendar last = photoList.get(0).getCalendar();
        for (int i = 1 ;i < photoList.size(); i++) {
            if (last.before(photoList.get(i).getCalendar())) {
                last = photoList.get(i).getCalendar();
                this.last = photoList.get(i);
            }
        }
    }

    public ArrayList<Photo> getPhotoList() {
        return photoList;
    }

    public void addImage(Image i) {
        photoList.add(new Photo(i));
    }


    public void addPhoto(Photo p) {
        photoList.add(p);
    }

    public void removeIndex(int index) {
        photoList.remove(index);
    }

    public void remove(Photo photo) {
        photoList.remove(photo);
    }

    public boolean photoExists(String name) {
        for (Photo photo : photoList) {
            if (photo != null) {
                return true;
            }
        }
        return false;
    }


    public Image getThumbnail() {
        if (photoList.isEmpty()){
            return null;
        }
        else {
            return photoList.get(0).getImage();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public Photo getPhoto(int index) {
        return photoList.get(index);
    }

    public Image getImage(int index) {
        return photoList.get(index).getImage();
    }

    public int getSize() {
        return photoList.size();
    }

    public void incrementNumPhotos() {
        numPhotos++;
    }

    public int getNumPhotos() {
        return numPhotos;
    }

    public String getFirstDate() {
        if (first != null) {
            return first.getDate();
        } else {
            return "";
        }
    }
    public String getLastDate() {
        if (last != null) {
            return last.getDate();
        } else {
            return "";
        }
    }

}
