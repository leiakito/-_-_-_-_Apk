package util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
    Author:leia
    Write The Code Change The World    
*/public class HttpDogRaiBow {
    public static String getDogRainBOw() throws IOException {
        //创建url对象
        URL url = new URL("https://chp.shadiao.app/api.php?from=noclip");

        //创建HTTPURlConnection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //设置connection参数

        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "/*/");
        connection.setConnectTimeout(5000);
        connection.connect();
        int code = connection.getResponseCode();
        if (code == 200) {
            InputStream inputStream = connection.getInputStream();

            StringBuffer sb = new StringBuffer();

            String line;

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String result = sb.toString();
            Log.d("msg", result);
            return result;
        }
        return null;
    }

}
