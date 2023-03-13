package com.example.parts_sales_system.data.api_connection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class getData {
    public static JSONArray getData(String dbname, String params) throws IOException {
        //根据地址创建URL对象(网络访问
        //发布文章的url)
        URL url = new URL("https://www.safety123.cn/api/"+dbname+"/postdata");
        HttpURLConnection conn = (HttpURLConnection)
                //设置请求的方式
                url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);//发送POST请求必须设置允许输出
        conn.setDoOutput(true);//发送POST请求必须设置允许输入
        //设置请求的头
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Charset", "utf-8");
        conn.setRequestProperty("Connection","close");//防止连接不能成功关闭影响下一次连接
//        String data = "参数1=" + URLEncoder.encode("参数1值", "UTF-8")+
//                "&参数2=" + URLEncoder.encode("参数2值", "UTF-8")
//                ;//传递的数据
        String data="";
        if (params!="null"){
            data=params;
            conn.setRequestProperty("Content-Length",
                    String.valueOf(data.getBytes().length));
        }
        //获取输出流
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes());
        os.flush();

        //获取响应的输入流对象
        InputStreamReader is = new InputStreamReader(conn.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(is);
        StringBuffer strBuffer = new StringBuffer();
        String line = null;
        //读取服务器返回信息
        while ((line = bufferedReader.readLine()) != null) {
            strBuffer.append(line);
        }
        String result = strBuffer.toString();//接收从服务器返回的数据

        //将取回的String转换成可用的json
        JSONArray jdata = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject(result);//String转JSONObject
            String stringd = jsonObject.getString("d");//JSONObject中取出key为"d"的value
            JSONObject jsonObjectd = new JSONObject(stringd);//String转JSONObject
            jdata = jsonObjectd.getJSONArray("data");//JSONObject中取出key为"data"的value（取出后为JSONArray）
        } catch (Exception e) {
            e.printStackTrace();
        }

        //关闭InputStream、关闭http连接
        is.close();
        conn.disconnect();

        return jdata;//返回取出的数据
    }

}
