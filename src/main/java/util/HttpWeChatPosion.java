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
*/public class HttpWeChatPosion {

    private static URL path;


    public static String get(Boolean flag) throws IOException {
        //设置url路径

        String wechat = "https://pyq.shadiao.app/api.php?from=noclip";
        String posion = "https://du.shadiao.app/api.php?from=noclip";
        //设置URL对象
        if (flag == true) {
            path = new URL(posion);
        } else {
            path = new URL(wechat);
        }
        //设置HTTPURLConnection
        HttpURLConnection connection = (HttpURLConnection) path.openConnection();
        int code = connection.getResponseCode();
        if (code == 200) {
            InputStream stream = connection.getInputStream();

            StringBuffer bf = new StringBuffer();

            String line;

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));

            while ((line = br.readLine()) != null) {
                bf.append(line);
            }
            String result = bf.toString();
            Log.d("msg", result);

            return result;
        }
        return null;
    }
}
