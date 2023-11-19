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
    private Photo earliestPhoto;
    private Photo latestPhoto;

    public Album(String name) {
        this.name = name;
        photoList = new ArrayList<Photo>();
        earliestPhoto = null;
        latestPhoto = null;
    }

    public void findDateRange() {
        if (photoList.isEmpty()){
            earliestPhoto = null;
            latestPhoto = null;
            return;
        }
        earliestPhoto = photoList.get(0);
        latestPhoto = photoList.get(0);
        Calendar earliestDate = earliestPhoto.getDate();
        Calendar latestDate = latestPhoto.getDate();

        for (int i = 1; i < photoList.size(); i++){
            Photo currPhoto = photoList.get(i);
            Calendar thisDate = currPhoto.getDate();
            if (thisDate.before(earliestDate)){
                earliestDate = thisDate;
                earliestPhoto = currPhoto;
            }
            if (thisDate.after(latestDate)){
                latestDate = thisDate;
                latestPhoto = currPhoto;
            }
        }
    }

    public ArrayList<Photo> getPhotoList() {
        return photoList;
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

    public String getEarliestDate() {
        if (earliestPhoto != null) {
            return earliestPhoto.getDate().getTime().toString();
        } else {
            return "";
        }
    }
    public String getLatestDate() {
        if (latestPhoto != null) {
            return latestPhoto.getDate().getTime().toString();
        } else {
            return "";
        }
    }

}
