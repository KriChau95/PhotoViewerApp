package application;

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
    private int[][] pixels;
    private Calendar date;

    public Photo(Image i){
        caption = "";
        tagList = new ArrayList<Tag>();
        date = Calendar.getInstance();
        date.set(Calendar.MILLISECOND, 0);
        setImage(i);
    }

    public ArrayList<Tag> getTagList(){
        return tagList;
    }

    public ArrayList<String> getTagStrings(){
        ArrayList<String> result = new ArrayList<String>();
        for (Tag tag : tagList) {
            result.add(tag.toString());
        }
        return result;
    }

    public Image getImage(){
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();
        for (int w = 0; w < width; w++){
            for (int h = 0; h < height; h++){
                writer.setArgb(w, h, pixels [w][h]);
            }
        }
        return result;
    }

    public String getCaption(){
        return caption;
    }

    public Calendar getCalendar(){
        return date;
    }

    public String getDate(){
        return date.getTime().toString();
    }

    public void setImage(Image i){
        width = 0 + (int) i.getWidth();
        height = 0 + (int) i.getHeight();
        pixels = new int[width][height];

        PixelReader reader = i.getPixelReader();

        for (int w = 0; w < width; w++) {
            for (int h= 0; h < height; h++) {
                pixels[w][h] = reader.getArgb(w, h);
            }
        }
    }

    public void setCaption(String caption){
        this.caption = caption;
    }

    public boolean hasTag(String type, String value){
        for (Tag tag : tagList) {
            if (tag.getType().equals(type) && tag.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void addTag(String type, String value){
        if (hasTag(type, value)){
            return;
        }
        tagList.add(new Tag (type, value));
    }

    public void removeTag(String name, String value) {
        for (int i = tagList.size() - 1; i > 0 ; i--) {
            if (tagList.get(i).getType().equals(name) && tagList.get(i).getValue().equals(value)) {
                tagList.remove(i);
            }
        }
    }



}
