package info.rospil.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import info.rospil.R;
import info.rospil.rest.QuerySender;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/19/11
 * Time: 6:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class PostComment extends Activity {

    public static final String SEND_COMMENT = "userid=%s&f=comment&user_name=%s&leadid=%s&context=&comment=%s";
    private Button sendButton;
    private EditText message;
    private PostComment self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.new_comment);
        sendButton = (Button) findViewById(R.id.send);
        message = (EditText) findViewById(R.id.message);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    SharedPreferences settings = getSharedPreferences(SettingsView.SETTINGS_FILE, 0);
                    String userName = settings.getString(SettingsView.USERNAME_KEY, "");
                    String password = settings.getString(SettingsView.PASSWORD_KEY, "");
                    HttpResponse response = QuerySender.postData(String.format(SettingsView.LOGIN_QUERY, userName, password), "");
                    String login = QuerySender.getContent(response);


                    JSONObject jObject = new JSONObject(login);
                    if (!jObject.getString("status").equalsIgnoreCase("OK")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(self).create();
                        alertDialog.setTitle("Ошибка");

                        alertDialog.setMessage("Проблемы с авторизацией. Проверьте настройки приложения.");

                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();
                                finish();
                            }
                        });
                        alertDialog.setIcon(R.drawable.icon);
                        alertDialog.show();

                        return;


                    }
                    String cookie = QuerySender.getCookie(response);
                    cookie = cookie.substring(0, cookie.indexOf(";"));
                    String uid = jObject.getString("userid");
                    String result = QuerySender.getContent(QuerySender.postData(String.format(SEND_COMMENT, uid, userName, getIntent().getStringExtra("leadid"), message.getText().toString()), cookie));
                    finish();
                } catch (JSONException e) {

                }
            }
        });
    }
}
