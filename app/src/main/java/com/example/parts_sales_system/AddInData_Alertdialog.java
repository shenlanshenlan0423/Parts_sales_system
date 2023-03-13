package com.example.parts_sales_system;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.parts_sales_system.data.api_connection.addData;
import com.example.parts_sales_system.data.api_connection.getData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddInData_Alertdialog extends Activity {
    Button in_submit;
    Button cancel;
    PopupMenu pm1;
    PopupMenu pm2;
    TextView in_detail_id;
    TextView prod_id;
    EditText mfjyan_order;
    EditText mfjyandate;
    EditText mfjyandes;
    ArrayList data1;
    ArrayList data2;
    Handler handler1;
    Handler handler2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_in_data_alertdialog);
        mfjyan_order=findViewById(R.id.in_ID);
        mfjyandate=findViewById(R.id.in_number);
        mfjyandes=findViewById(R.id.in_type);
        in_submit=findViewById(R.id.in_submit);
        in_submit.setOnClickListener(new submit());
        cancel=findViewById(R.id.in_cancel);
        cancel.setOnClickListener(new cancel());
        in_detail_id=findViewById(R.id.in_detail_id);
        in_detail_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMenu1();
            }
        });
        prod_id=findViewById(R.id.prod_id);
        prod_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMenu2();
            }
        });
    }
    private void initMenu1(){
        // 这里的catagory是下拉菜单控件
        pm1=new PopupMenu(AddInData_Alertdialog.this,in_detail_id);
        Menu menu=pm1.getMenu();
        // 这里的R.menu.catagory是第一步定义的menu下的文件名catagory.xml
        pm1.getMenuInflater().inflate(R.menu.catagory,menu);
        handler1=new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:{
                        data1=(ArrayList) msg.obj;
                    }
                }
                for(int i=0;i<data1.size();i++){
                    // 1-组别、2-数据项id、3-数据项顺序、4-数据项内容
                    menu.add(0,i,i,String.valueOf(data1.get(i)));
                    System.out.println(data1.get(i));
                }
                menu.add(0,data1.size(),data1.size(),"2023030609293529357");
                // 添加单击数据项事件
                pm1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // 选择某一个分类后将控件的值改为数据项的内容
                        in_detail_id.setText(item.getTitle());
                        return true;
                    }
                });
                pm1.show();
            }
        };
        // 数据库查询所有分类的方法
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = getData.getData("MFJOrder", "null");
                    //System.out.println(jsonArray);
                    data1 = new ArrayList();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                        data1.add(jsonObject.getString("ID"));
                    }
                    System.out.println(data1);
                    Message msg=new Message();
                    msg.obj=data1;
                    msg.what=1;
                    handler1.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        // 将分类全部添加到数据项中

    }
    private void initMenu2(){
        // 这里的catagory是下拉菜单控件
        pm2=new PopupMenu(AddInData_Alertdialog.this,prod_id);
        Menu menu=pm2.getMenu();
        // 这里的R.menu.catagory是第一步定义的menu下的文件名catagory.xml
        pm2.getMenuInflater().inflate(R.menu.catagory,menu);
        handler2=new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:{
                        data2=(ArrayList) msg.obj;
                    }
                }
                for(int i=0;i<data2.size();i++){
                    // 1-组别、2-数据项id、3-数据项顺序、4-数据项内容
                    menu.add(0,i,i,String.valueOf(data2.get(i)));
                    System.out.println(data2.get(i));
                }
                // 添加单击数据项事件
                pm2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // 选择某一个分类后将控件的值改为数据项的内容
                        prod_id.setText(item.getTitle());
                        return true;
                    }
                });
                pm2.show();
            }
        };
        // 数据库查询所有分类的方法
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = getData.getData("User", "null");
                    //System.out.println(jsonArray);
                    data2 = new ArrayList();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                        data2.add(jsonObject.getString("DeptID"));
                    }
                    System.out.println(data2);
                    Message msg=new Message();
                    msg.obj=data2;
                    msg.what=1;
                    handler2.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        // 将分类全部添加到数据项中

    }

    //提交按钮响应事件
    public class submit implements View.OnClickListener{
        @Override
        public void onClick(View view){
            String orderid=String.valueOf(in_detail_id.getText());
            String userid=String.valueOf(prod_id.getText());
            String order=String.valueOf(mfjyan_order.getText());
            String date=String.valueOf(mfjyandate.getText());
            String des=String.valueOf(mfjyandes.getText());
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        addData.addData("MFJYan","\"{\"ID\":\"\",\"MFJOrderID\":\"" +orderid+
                                "\",\"MFJYanDate\":\""+date+
                                "\",\"MFJYanOrder\":\""+order+
                                "\",\"MFJYanDes\":\""+des+
                                "\",\"UserID\":\""+userid+"\"}");
                        System.out.println("\"{\"ID\":\"\",\"MFJOrderID\":\"" +orderid+
                                "\",\"MFJYanDate\":\""+date+
                                "\",\"MFJYanOrder\":\""+order+
                                "\",\"MFJYanDes\":\""+des+
                                "\",\"UserID\":\""+userid+"\"}");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
            Intent intent;
            intent=new Intent(AddInData_Alertdialog.this,private_InventManageActivity.class);
            startActivity(intent);
        }
    }
    //取消按钮事件
    public class cancel implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent;
            intent=new Intent(AddInData_Alertdialog.this,private_InventManageActivity.class);
            startActivity(intent);
        }
    }
}