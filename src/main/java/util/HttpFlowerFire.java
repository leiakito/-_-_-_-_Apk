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
*/public class HttpFlowerFire {
    public static String get(Boolean zh_hk) throws IOException {
        URL path;
        if (zh_hk == true) {
            path = new URL("https://nmsl.shadiao.app/api.php?level=min&lang=zh_hk&from=noclip");
        } else {
            path = new URL("https://nmsl.shadiao.app/api.php?level=min&from=noclip");
        }
        //url对象
        //url对象设置参数 GET 链接超时时间
        HttpURLConnection connection = (HttpURLConnection) path.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "*/*");
        connection.setConnectTimeout(5000);

        connection.connect();
        //开启连接
        int code=connection.getResponseCode();
            if (code==200){
                //创建输入流对象
                InputStream stream=connection.getInputStream();
                //字符串Buffer
                StringBuffer buffer=new StringBuffer();
                String line;

                BufferedReader br=new BufferedReader(new InputStreamReader(stream));

                while ((line=br.readLine())!=null){
                    buffer.append(line);
                }
                    String result=buffer.toString();
                Log.d("msg",result);
                return result;
            }
        //处理返回流

        return null;
    }
}
