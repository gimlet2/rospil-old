package info.rospil.view;

import java.io.Serializable;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import info.rospil.R;
import info.rospil.xml.ditail.Ditail;
import info.rospil.xml.ditail.XPathDitailsParser;

/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/12/11
 * Time: 1:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class DetailView extends TabActivity {
    public static final String CONCURS_SKIPED = "конкурс отменен";
    private TextView title;
    private TextView why;
    private TextView desc;
    private TextView link;
    private TabHost tabHost;
    private TextView org;
    private TextView person;
    private TextView price;
    private TextView status;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(info.rospil.R.layout.detail);
        url = getIntent().getExtras().getString(PilsListView.URL);
        XPathDitailsParser xPathDitailsParser = new XPathDitailsParser(url);
        Ditail result = xPathDitailsParser.parse();

        Intent intent = new Intent().setClass(this, CommentsView.class);
        intent.putExtra("comments", (Serializable) result.getComments());
        intent.putExtra("leadid", url.substring(url.lastIndexOf("/") + 1));
        tabHost = (TabHost) findViewById(android.R.id.tabhost);



        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Детали", getResources().getDrawable(R.drawable.ditail)).setContent(R.id.tab1Layout));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Кто", getResources().getDrawable(R.drawable.contact)).setContent(R.id.tab2Layout));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Комментарии", getResources().getDrawable(R.drawable.comments)).setContent(intent));
//        TabHost.TabSpec.setIndicator();


        title = (TextView) findViewById(R.id.title);
        why = (TextView) findViewById(R.id.whyfraud);
        desc = (TextView) findViewById(R.id.description);

        price = (TextView) findViewById(R.id.price);
        person = (TextView) findViewById(R.id.person2);
        org = (TextView) findViewById(R.id.org3);
        status = (TextView) findViewById(R.id.status);

        title.setText(result.getTitle());
        why.setText(result.getWhyfraud());
        desc.setText(result.getDescription());
        price.setText(result.getPrice());
        person.setText(Html.fromHtml(result.getPerson()));
        org.setText(Html.fromHtml(result.getOrganization()));
        //desc.setMovementMethod(new ScrollingMovementMethod());

        link = (TextView) findViewById(R.id.link);

        if (result.getStatus().equalsIgnoreCase(CONCURS_SKIPED)) {
            status.setBackgroundColor(0xFF00FF00);
        } else {
            status.setBackgroundColor(0xFFFF0000);
        }
        status.setText(result.getStatus());

        String text = "<a href=\"" + result.getLink() + "\">Ссылка на источник</a>";

        link.setText(Html.fromHtml(text));
        link.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
