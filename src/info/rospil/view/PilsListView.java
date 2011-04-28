package info.rospil.view;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import info.rospil.R;
import info.rospil.xml.rss.AndroidSaxFeedParser;
import info.rospil.xml.rss.Message;


/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/11/11
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class PilsListView extends ListActivity {
    public static final String URL = "URL";
    private PilsListView self;
    private static final String FEED_URI = "http://rospil.info/rss";
    private List<Message> messages;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(info.rospil.R.layout.main);
        updateFeed();

        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                updateFeed();
            }
        });


        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(self, DetailView.class);
                i.putExtra(URL, messages.get(position).getLink().toString());
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent i = new Intent(self, SettingsView.class);
                startActivity(i);
                return true;
            case R.id.about:
                //showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateFeed() {
        AndroidSaxFeedParser parser = new AndroidSaxFeedParser(FEED_URI);
        ArrayList<String> titles = new ArrayList<String>();
        messages = parser.parse();
        if (messages == null) {
            return;
        }
        for (Message m : messages) {
            titles.add(m.getTitle().trim());
        }

        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, titles));
    }
}
