package info.rospil.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import info.rospil.R;
import info.rospil.xml.ditail.Comment;

/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/13/11
 * Time: 6:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommentsView extends ListActivity {
    protected CommentsView self;
    private SimpleAdapter adapter;

    class DoLoad extends AsyncTask<List, Integer, List> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TextView empty = (TextView) self.findViewById(android.R.id.empty);
            empty.setText("Комментарии загружаются");
        }

        @Override
        protected List doInBackground(List... lists) {


            List<Comment> comments = lists[0];
            int i = 0;
            for (Comment c : comments) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("author", c.getName());
                map.put("date", Html.fromHtml(c.getDate()).toString());
                map.put("mess", Html.fromHtml(c.getMessage()).toString());
                fillMaps.add(map);
//                i++;
//                if (i == 20) {
                    this.publishProgress(i);
//                    i = 0;
//                }
            }
            return fillMaps;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);    //To change body of overridden methods use File | Settings | File Templates.
            adapter.notifyDataSetChanged();
            //SimpleAdapter adapter = new SimpleAdapter(self, fillMaps, R.layout.comment_item, from, to);
            //setListAdapter(adapter);
            //self.onContentChanged();
           // self.getListView().;
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            adapter.notifyDataSetChanged();
            //SimpleAdapter adapter = new SimpleAdapter(self, list, R.layout.comment_item, from, to);

            //mList.invalidateViews();
            //setListAdapter(adapter);
        }
    }

    private List<HashMap<String, String>> fillMaps;
    private ListView mList;
    private String[] from;
    private int[] to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.comment_list);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        from = new String[]{ "author", "date", "mess" };
        fillMaps = new ArrayList<HashMap<String, String>>();

        to = new int[]{ R.id.author, R.id.date, R.id.message };
        adapter = new SimpleAdapter(self, fillMaps, R.layout.comment_item, from, to);

        setListAdapter(adapter);

        List<Comment> comments = (List<Comment>) getIntent().getSerializableExtra("comments");
        if (comments.size() > 0) {
            new DoLoad().execute(comments);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.comment, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_comment:
                Intent i = new Intent(self, PostComment.class);
                i.putExtra("leadid", getIntent().getStringExtra("leadid"));
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
