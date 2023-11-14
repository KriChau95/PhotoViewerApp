package application;

import java.io.Serial;
import java.io.Serializable;

public class Tag implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String type;
    private String value;

    public Tag(String type, String value){
        this.type = type;
        this.value = value;
    }

    public String getType(){
        return this.type;
    }

    public String getValue(){
        return this.value;
    }

    public String toString(){
        return type + ":" + value;
    }

}
