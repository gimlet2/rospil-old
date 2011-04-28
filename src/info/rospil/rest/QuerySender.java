package info.rospil.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

/**
 * Created by IntelliJ IDEA.
 * User: andreychernyshev
 * Date: 4/19/11
 * Time: 4:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class QuerySender {
    public static final String BASE_URL = "http://rospil.info/a";

    public static HttpResponse postData(String data, String cookie) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(BASE_URL);
        if (!cookie.equalsIgnoreCase("")) {
            httppost.addHeader("Cookie", cookie);
        }

        try {
            // Add your data
            StringEntity sEntity = new StringEntity(data, HTTP.UTF_8);
            // Log.d("3", httppost.getURI().getQuery());
            sEntity.setContentType("application/x-www-form-urlencoded");
            httppost.setEntity(sEntity);

            // Execute HTTP Post Request

            HttpResponse response = httpclient.execute(httppost);
            return response;


        } catch (ClientProtocolException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (IOException e) {

        }
        return null;
    }

    public static String getCookie(HttpResponse response) {
        Header cookie = response.getFirstHeader("Set-Cookie");
        if (cookie != null) {
            return response.getFirstHeader("Set-Cookie").getValue();
        }
        return "";
    }

    public static String getContent(HttpResponse response) {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {

                writer.write(buffer, 0, n);

            }
            return writer.toString();
        } catch (IOException e) {

        }
        return "";
    }
}
