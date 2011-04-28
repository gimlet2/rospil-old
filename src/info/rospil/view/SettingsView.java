package info.rospil.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import info.rospil.R;
import info.rospil.rest.QuerySender;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/18/11
 * Time: 11:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsView extends Activity {
    public static final String SETTINGS_FILE = "rospil.conf";
    public static final String USERNAME_KEY = "prefs.usernameInput";
    public static final String PASSWORD_KEY = "prefs.password";
    public static final String LOGIN_QUERY = "f=login&jc=&name=%s&password=%s";

    private String userName;
    private String password;
    private TextView usernameInput;
    private TextView passwordInput;
    private Button saveButton;
    private SettingsView self;
    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.settings);
        SharedPreferences settings = getSharedPreferences(SETTINGS_FILE, 0);
        userName = settings.getString(USERNAME_KEY, "");
        password = settings.getString(PASSWORD_KEY, "");
        usernameInput = (TextView) findViewById(R.id.username);
        passwordInput = (TextView) findViewById(R.id.password);

        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                SharedPreferences settings = getSharedPreferences(SETTINGS_FILE, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(USERNAME_KEY, usernameInput.getText().toString());
                editor.putString(PASSWORD_KEY, passwordInput.getText().toString());
                self.onBackPressed();

                // Commit the edits!
                editor.commit();
            }
        });

        testButton = (Button) findViewById(R.id.test);
        testButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                String login = QuerySender.getContent(QuerySender.postData(String.format(LOGIN_QUERY, usernameInput.getText().toString(), passwordInput.getText().toString()), ""));

                try {
                    JSONObject jObject = new JSONObject(login);

                    AlertDialog alertDialog = new AlertDialog.Builder(self).create();
                    alertDialog.setTitle("Проверка логина");
                    if (jObject.getString("status").equalsIgnoreCase("OK")) {
                        alertDialog.setMessage("Всё верно");
                    } else {
                        alertDialog.setMessage("Проблема с логином");
                    }
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.setIcon(R.drawable.icon);
                    alertDialog.show();
                } catch (JSONException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }


            }
        }

        );

        usernameInput.setText(userName);
        passwordInput.setText(password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
