package mobileapps.g483.bakhtuev.networkmaps;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequest {

    Activity activity;

    public void onSuccess(String data)
    {

    }

    public void onError()
    {

    }

    public HttpRequest(Activity activity)
    {
        this.activity = activity;
    }

    public void post(String endpoint,String payload) throws InterruptedException {
        Thread thread = new Thread(() ->{
            try
            {
                URL url = new URL(endpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Accept","application/json");
                connection.setRequestProperty("Content-Type","application/json; utf-8");
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                byte[] out = payload.getBytes(StandardCharsets.UTF_8);
                outputStream.write(out);
                int responseCode = connection.getResponseCode();
                Log.d("Code", String.valueOf(responseCode));

                InputStream inputStream = connection.getInputStream();
                byte[] inp = new byte[1024];
                String data = "";
                while (true){
                    int len = inputStream.read(inp);
                    if(len < 0) break;
                    data += new String(inp,0,len);
                }
                Log.i(TAG, "post: " + data);
                connection.disconnect();
                final String result = data;
                activity.runOnUiThread(()-> {onSuccess(result);});
            }
            catch (Exception e)
            {
                e.printStackTrace();
                activity.runOnUiThread(()->{onError();});
            }
        });
        thread.start();
    }
}
