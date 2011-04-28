package info.rospil.xml.rss;

import java.util.List;

import android.util.Xml;

public class AndroidSaxFeedParser extends BaseFeedParser {

    public AndroidSaxFeedParser(String feedUrl) {
        super(feedUrl);
    }

    public List<Message> parse() {
        RssHandler handler = new RssHandler();
        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, handler);
        } catch (Exception e) {
            //throw new RuntimeException(e); TODO add alert;

        }
        return handler.getMessages();
    }

}