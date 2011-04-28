package info.rospil.xml.ditail;

import android.text.Html;
import android.util.Xml;
import org.htmlcleaner.HtmlCleaner;

/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/12/11
 * Time: 7:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class AndroidSaxDitailParser extends BaseDitailParser {

    public AndroidSaxDitailParser(String feedUrl) {
        super(feedUrl);
    }

    public Ditail parse() {
        DitailHandler ditailHandler = new DitailHandler();
        try {
            HtmlCleaner htmlCleaner = new HtmlCleaner();

            //Xml.parse(htmlCleaner.clean(this.getInputStream()), Xml.Encoding.UTF_8, ditailHandler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ditailHandler.getDitail();
    }
}
