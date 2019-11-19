package tw.org.iii.android_mysql_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test1(View view) {
        new Thread() {
            @Override
            public void run() {
                test11();
            }
        }.start();
    }

    //爬蟲 取得html
    private void test11() {
        try {
            URL url = new URL("http://10.0.2.2/php_03");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            InputStream in = connection.getInputStream();
            InputStreamReader irs = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(irs);

            String line;
            while ((line = br.readLine()) != null) {
                Log.v("DCH", line);
            }

            br.close();
        } catch (Exception e) {
            Log.v("DCH", e.toString());
        }
    }
}
