package info.rospil.xml.ditail;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DitailHandler extends DefaultHandler {
    public static final String CLASS = "class";
    private Ditail ditail;
    private StringBuilder builder;
    private String currentType;

    public Ditail getDitail() {
        return ditail;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        super.endElement(uri, localName, name);
        if (this.ditail != null) {
            if (currentType == BaseDitailParser.TITLE) {
                ditail.setTitle(builder.toString());
                builder.setLength(0);
            } else if (currentType == BaseDitailParser.WHYFRAUD) {
                ditail.setWhyfraud(builder.toString());
                builder.setLength(0);
            } else if (currentType == BaseDitailParser.LINK) {
                ditail.setLink(builder.toString());
                builder.setLength(0);
            } else if (currentType == BaseDitailParser.DESCRIPTION) {
                ditail.setDescription(builder.toString());
                builder.setLength(0);
            } else if (currentType == BaseDitailParser.STATUS) {
                ditail.setStatus(builder.toString());
                builder.setLength(0);
            }

        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if(attributes == null || attributes.getLength() == 0) {
            return;
        }
        if (isElementHasClass(attributes, BaseDitailParser.CONTAINER)) {
            this.ditail = new Ditail();
        }
        if (this.ditail != null) {
            if (isElementHasClass(attributes, BaseDitailParser.TITLE)) {
                currentType = BaseDitailParser.TITLE;
                builder.setLength(0);
            }
            if (isElementHasClass(attributes, BaseDitailParser.WHYFRAUD)) {
                currentType = BaseDitailParser.WHYFRAUD;
                builder.setLength(0);
            }
            if (isElementHasClass(attributes, BaseDitailParser.LINK)) {
                currentType = BaseDitailParser.LINK;
                builder.setLength(0);
            }
            if (isElementHasClass(attributes, BaseDitailParser.DESCRIPTION)) {
                currentType = BaseDitailParser.DESCRIPTION;
                builder.setLength(0);
            }
            if (isElementHasClass(attributes, BaseDitailParser.STATUS)) {
                currentType = BaseDitailParser.STATUS;
                builder.setLength(0);
            }
        }
    }

    private boolean isTitle(Attributes attributes) {
        return isElementHasClass(attributes, BaseDitailParser.TITLE);
    }

    private boolean isElementHasClass(Attributes attributes, String className) {
        String classN = attributes.getValue(CLASS);
        if (classN == null) {
            return false;
        }
        return attributes.getValue(CLASS).equalsIgnoreCase(className);
    }
}
