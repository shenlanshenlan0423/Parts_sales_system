package com.example.parts_sales_system.data.api_connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class addData {
    public static void addData(String dbname,String params) throws IOException {
        //根据地址创建URL对象(网络访问
        //发布文章的url)
        URL url = new URL("https://www.safety123.cn/api/"+dbname+"/setdata");
        HttpURLConnection conn = (HttpURLConnection)
                //设置请求的方式
                url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);//发送POST请求必须设置允许输出
        conn.setDoOutput(true);//发送POST请求必须设置允许输入
        //设置请求的头
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Charset", "utf-8");
        conn.addRequestProperty("Connection","close");
//        String data = "参数1=" + URLEncoder.encode("参数1值", "UTF-8")+
//                "&参数2=" + URLEncoder.encode("参数2值", "UTF-8")
//                ;//传递的数据
        String data=params;
        conn.setRequestProperty("Content-Length",
                    String.valueOf(data.getBytes().length));
        //获取输出流
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();

        //获取响应的输入流对象
        InputStreamReader is = new InputStreamReader(conn.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(is);

        //关闭InputStream、关闭http连接
        is.close();
        conn.disconnect();
    }
}
