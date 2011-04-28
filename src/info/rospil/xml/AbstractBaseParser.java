package info.rospil.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/12/11
 * Time: 7:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractBaseParser {
    final URL feedUrl;

    public AbstractBaseParser(String feedUrl) {
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
