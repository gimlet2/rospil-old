package info.rospil.xml.ditail;

import info.rospil.xml.AbstractBaseParser;


public abstract class BaseDitailParser extends AbstractBaseParser implements DitailParser {

    public static final String CONTAINER = "case";
    public static final String TITLE = "title";
    public static final String WHYFRAUD = "whyfraud";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";
    public static final String STATUS = "statusbar";

    protected BaseDitailParser(String feedUrl) {
        super(feedUrl);
    }

}
