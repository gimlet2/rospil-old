package info.rospil.xml.ditail;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/13/11
 * Time: 5:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class Comment implements Serializable{

    private String name;
    private String date;
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
