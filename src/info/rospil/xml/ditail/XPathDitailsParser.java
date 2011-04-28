package info.rospil.xml.ditail;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/12/11
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class XPathDitailsParser extends BaseDitailParser {
    public static final String TITLE_XPATH = "//div[@class='title'][position()=1]/h1/text()";
    public static final String WHYFRAUD_XPATH = "//div[@class='whyfraud bottom20']/text()";
    public static final String DESC_XPATH = "//div[@class='description']/text()";

    public Ditail parse() {
        Ditail result = new Ditail();
        try {
            InputStreamReader isr =
                    new InputStreamReader(this.getInputStream());
            BufferedReader buff = new BufferedReader(isr);
            StringBuffer strBuff = new StringBuffer();
            String s;
            while ((s = buff.readLine()) != null) {
                strBuff.append(s);
            }
            s = strBuff.toString();
            result.setTitle(subString(s, "<div class=\"title\"><h1>", "</h1></div>"));
            result.setWhyfraud(subString(s, "<div class=\"whyfraud bottom20\">", "</div>"));
            result.setDescription(subString(s, "<div class=\"description\">", "</div>"));
            result.setLink(subString(s, "<div class=\"link\"><b><a target=\"blank\" href=\"", "\">"));

            String whoiswho = subString(s, "<div class=\"whoiswho\">", " <div class=\"clear\"></div>");
            result.setPrice(subString(whoiswho, "<span class=\"red\"><b>", "</b></span>"));
            result.setOrganization(subString(subString(whoiswho, "<a href=\"/orgs/", "</a>"), "<b>", "</b>"));
            result.setPerson(subString(whoiswho, "<div class=\"\" id=\"chief_name_area\">", "</div>"));

            String status = subString(s, "<div class=\"statusbar\">", "</div>");
            if (status.contains("<span class=\"green\">")) {
                result.setStatus(subString(status, "<span class=\"green\">", "</span>"));
            } else {
                result.setStatus(subString(status, "<b>", "</b>"));
            }

            String comments = subString(s, "<div class=\"comments\">", "<div id=\"loginregisterarea\">");

            while (comments.contains("<div class=\"comment ")) {
                String com = subString(comments, "<div class=\"comment ", "<div class=\"clear\"></div>");
                comments = comments.substring(com.length() + 20);
                Comment comment = new Comment();
                comment.setName(subString(com, "<span class=\"name\">", "</span>"));
                comment.setDate(subString(com, "<span class=\"when\">", "</span>"));
                comment.setMessage(subString(com, "<div class=\"what\">", "</div>"));
                result.addComment(comment);
            }

            //result.setWhyfraud(doc.evaluateXPath(WHYFRAUD_XPATH)[0].toString());
            //result.setDescription(doc.evaluateXPath(DESC_XPATH)[0].toString());

            //Xml.parse(htmlCleaner.clean(this.getInputStream()), Xml.Encoding.UTF_8, ditailHandler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private String subString(String source, String start, String end) {
        int startP = source.indexOf(start) + start.length();
        return source.substring(startP, source.indexOf(end, startP));
    }

    public XPathDitailsParser(String feedUrl) {
        super(feedUrl);

    }
}
