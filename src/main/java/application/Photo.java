package application;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Photo implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private String caption;
    private ArrayList<Tag> tagList;
    private int width;
    private int height;
    private int[][] photoArray;
    private Calendar date;

    private String imageFilePath;

    public Photo(Image i){
        caption = "";
        tagList = new ArrayList<Tag>();
        date = Calendar.getInstance();
        date.set(Calendar.MILLISECOND, 0);
        width = (int) i.getWidth();
        height = (int) i.getHeight();
        photoArray = new int[width][height];

        PixelReader reader = i.getPixelReader();
        if (reader != null){
            for (int w = 0; w < width; w++) {
                for (int h= 0; h < height; h++) {
                    photoArray[w][h] = reader.getArgb(w, h);
                }
            }
        }
    }

    public Photo(File f){
        caption = "";
        tagList = new ArrayList<Tag>();
        date = Calendar.getInstance();
        date.set(Calendar.MILLISECOND, 0);
        imageFilePath = f.getPath();
    }

    public ArrayList<Tag> getTagList(){
        return tagList;
    }

    public Image getImage(){
        if (width != 0){
            WritableImage result = new WritableImage(width, height);
            PixelWriter writer = result.getPixelWriter();
            for (int w = 0; w < width; w++){
                for (int h = 0; h < height; h++){
                    writer.setArgb(w, h, photoArray[w][h]);
                }
            }
            return result;
        } else {
            return new Image(imageFilePath);
        }

    }

    public String getCaption(){
        return caption;
    }

    public Calendar getDate(){
        return date;
    }

    public void setCaption(String caption){
        this.caption = caption;
    }

    public boolean hasTag(Tag t){
        for (Tag tag : tagList) {
            if (tag.equals(t)) {
                return true;
            }
        }
        return false;
    }

    public void addTag(Tag t){
        if (hasTag(t)){
            return;
        }
        tagList.add(t);
    }

    public void removeTag(Tag t) {
        for (int i = tagList.size() - 1; i >= 0 ; i--) {
            if (tagList.get(i).equals(t)) {
                tagList.remove(i);
            }
        }
    }

}
