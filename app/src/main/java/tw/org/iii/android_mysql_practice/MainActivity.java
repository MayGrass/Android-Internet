package tw.org.iii.android_mysql_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private ImageView img;
    private Bitmap bitmap;
    private UIHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new UIHandler();
        img = findViewById(R.id.img);
        queue = Volley.newRequestQueue(this);
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

    public void test2(View view) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("DCH", response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("DCH", "error" + error.toString());
            }
        };
        StringRequest request = new StringRequest(Request.Method.GET,
                "http://10.0.2.2/php_03",
                listener,
                errorListener);
        queue.add(request);

    }

    public void test3(View view) {
        new Thread() {
            @Override
            public void run() {
                test13();
            }
        }.start();
    }

    //獲得圖片資訊
    private void test13() {
        try {
            URL url = new URL("https://cdn.vox-cdn.com/thumbor/8Gm6NK0gcEvZsLT7Q2TJCTkcurQ=/0x0:2040x1360/1200x800/filters:focal(857x517:1183x843)/cdn.vox-cdn.com/uploads/chorus_image/image/65301238/wjoel_180413_1777_android_001.0.jpg");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            //安卓的影像
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            handler.sendEmptyMessage(0);

        } catch (Exception e) {
            Log.v("DCH", e.toString());
        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            img.setImageBitmap(bitmap);
        }
    }
}
