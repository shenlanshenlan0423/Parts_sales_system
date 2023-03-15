package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;

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

public class private_inventmanage_AddOutData_AlertDialog extends Activity {
    Button out_submit;
    Button cancel;
    PopupMenu pm1;
    PopupMenu pm2;
    TextView Usedeptid;
    TextView Userid;
    EditText mfjchudate;
    EditText mfjchudes;
    ArrayList data1;
    ArrayList data2;
    Handler handler1;
    Handler handler2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_inventmanage_add_out_data_alert_dialog);
        Usedeptid=findViewById(R.id.usedeptid);
        Usedeptid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMenu1();
            }
        });
        Userid=findViewById(R.id.userid);
        Userid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMenu2();
            }
        });
        mfjchudate=findViewById(R.id.chudate);
        mfjchudes=findViewById(R.id.chudes);
        out_submit=findViewById(R.id.outsubmit);
        out_submit.setOnClickListener(new submit());
        cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(new cancel());
    }
    private void initMenu1(){
        // 这里的catagory是下拉菜单控件
        pm1=new PopupMenu(private_inventmanage_AddOutData_AlertDialog.this,Usedeptid);
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
                menu.add(0,data1.size(),data1.size(),"20230227110803832");
                // 添加单击数据项事件
                pm1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // 选择某一个分类后将控件的值改为数据项的内容
                        Usedeptid.setText(item.getTitle());
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
                    JSONArray jsonArray = getData.getData("UseDept", "null");
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
        pm2=new PopupMenu(private_inventmanage_AddOutData_AlertDialog.this,Userid);
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
                        Userid.setText(item.getTitle());
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
                        data2.add(jsonObject.getString("ID"));
                    }
                    data2.add("1");
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
            String usedeptid=String.valueOf(Usedeptid.getText());
            String userid=String.valueOf(Userid.getText());
            String date=String.valueOf(mfjchudate.getText());
            String des=String.valueOf(mfjchudes.getText());
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        addData.addData("MFJChu","{\"ID\":\"\",\"UseDeptID\":\"" +usedeptid+
                                "\",\"MFJChuDate\":\""+date+
                                "\",\"MFJChuDes\":\""+des+
                                "\",\"UserID\":\""+userid+"\"}");
                        System.out.println("{\"ID\":\"\",\"UseDeptID\":\"" +usedeptid+
                                "\",\"MFJChuDate\":\""+date+
                                "\",\"MFJChuDes\":\""+des+
                                "\",\"UserID\":\""+userid+"\"}");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
            Intent intent;
            intent=new Intent(private_inventmanage_AddOutData_AlertDialog.this,private_InventManageActivity.class);
            intent.putExtra("page",1);
            startActivity(intent);
        }
    }
    //取消按钮事件
    public class cancel implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent;
            intent=new Intent(private_inventmanage_AddOutData_AlertDialog.this,private_InventManageActivity.class);
            intent.putExtra("page",1);
            startActivity(intent);
        }
    }
}