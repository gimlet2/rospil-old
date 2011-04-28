package info.rospil.xml.ditail;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/12/11
 * Time: 7:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class Ditail {
    static SimpleDateFormat FORMATTER =
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
    private String title;
    private String whyfraud;
    private URL link;
    private String status;
    private String description;
    private String price;
    private String organization;
    private String person;
    private String lifetime;
    private List<Comment> comments = new ArrayList<Comment>();

    public void addComment(Comment comment) {
        this.comments.add(comment);

    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getLifetime() {
        return lifetime;
    }

    public void setLifetime(String lifetime) {
        this.lifetime = lifetime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWhyfraud() {
        return whyfraud;
    }

    public void setWhyfraud(String whyfraud) {
        this.whyfraud = whyfraud;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public void setLink(String link) {
        try {
            this.link = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
