package info.rospil.xml.rss;

import info.rospil.xml.AbstractBaseParser;

public abstract class BaseFeedParser extends AbstractBaseParser implements FeedParser {

    // names of the XML tags
    static final String PUB_DATE = "pubDate";
    static final String DESCRIPTION = "description";
    static final String LINK = "link";
    static final String TITLE = "title";
    static final String ITEM = "item";


    protected BaseFeedParser(String feedUrl) {
        super(feedUrl);
    }

}