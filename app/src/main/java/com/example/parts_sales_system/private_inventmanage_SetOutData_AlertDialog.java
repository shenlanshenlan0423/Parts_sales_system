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
import java.util.HashMap;

public class private_inventmanage_SetOutData_AlertDialog extends Activity {
    TextView creator;
    TextView createtime;
    TextView updater;
    TextView updatetime;
    TextView id;//不能改
    TextView usedeptid;//下拉框
    TextView userid;//下拉框
    EditText date;
    EditText des;
    TextView username;//不能改
    TextView usedeptname;//不能改
    Button set;
    Button cancel;
    String id_str;
    String usedeptid_str;
    String date_str;
    String des_str;
    String userid_str;
    PopupMenu pm_usedeptid;
    Handler handler_usedeptid;
    ArrayList data_usedeptid;
    PopupMenu pm_userid;
    Handler handler_userid;
    ArrayList data_userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_inventmanage_set_out_data_alert_dialog);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        creator=findViewById(R.id.creator);
        creator.setText((String)data.get("creator"));
        createtime=findViewById(R.id.createtime);
        createtime.setText((String)data.get("createTime"));
        updater=findViewById(R.id.updatetime);
        updater.setText((String)data.get("updater"));
        updatetime=findViewById(R.id.updatetime);
        updatetime.setText((String)data.get("updatetime"));
        id=findViewById(R.id.id);
        id.setText((String)data.get("ID"));
        usedeptid=findViewById(R.id.usedeptid);
        usedeptid.setText((String)data.get("UseDeptID"));
        usedeptid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMenu_usedeptid();
            }
        });
        userid=findViewById(R.id.userid);
        userid.setText((String)data.get("UserID"));
        userid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMenu_userid();
            }
        });
        date=findViewById(R.id.mfjchudate);
        date.setText((String)data.get("MFJChuDate"));
        des=findViewById(R.id.mfjchudes);
        des.setText((String)data.get("MFJChuDes"));
        username=findViewById(R.id.username);
        username.setText((String)data.get("Username"));
        usedeptname=findViewById(R.id.UseDeptName);
        usedeptname.setText((String)data.get("UseDeptName"));
        set=findViewById(R.id.outset);
        set.setOnClickListener(new set());
        cancel=findViewById(R.id.outcancel);
        cancel.setOnClickListener(new cancel());
    }
    private void initMenu_usedeptid(){
        // 这里的catagory是下拉菜单控件
        pm_usedeptid=new PopupMenu(private_inventmanage_SetOutData_AlertDialog.this,usedeptid);
        Menu menu=pm_usedeptid.getMenu();
        // 这里的R.menu.catagory是第一步定义的menu下的文件名catagory.xml
        pm_usedeptid.getMenuInflater().inflate(R.menu.catagory,menu);
        handler_usedeptid=new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:{
                        data_usedeptid=(ArrayList) msg.obj;
                    }
                }
                for(int i=0;i<data_usedeptid.size();i++){
                    // 1-组别、2-数据项id、3-数据项顺序、4-数据项内容
                    menu.add(0,i,i,String.valueOf(data_usedeptid.get(i)));
                    System.out.println(data_usedeptid.get(i));
                }
                // 添加单击数据项事件
                pm_usedeptid.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // 选择某一个分类后将控件的值改为数据项的内容
                        usedeptid.setText(item.getTitle());
                        return true;
                    }
                });
                pm_usedeptid.show();
            }
        };
        // 数据库查询所有分类的方法
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = getData.getData("UseDept", "null");
                    //System.out.println(jsonArray);
                    data_usedeptid = new ArrayList();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                        data_usedeptid.add(jsonObject.getString("ID"));
                    }
                    data_usedeptid.add("20230227110803832");
                    System.out.println(data_usedeptid);
                    Message msg=new Message();
                    msg.obj=data_usedeptid;
                    msg.what=1;
                    handler_usedeptid.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        // 将分类全部添加到数据项中
    }
    private void initMenu_userid(){
        // 这里的catagory是下拉菜单控件
        pm_userid=new PopupMenu(private_inventmanage_SetOutData_AlertDialog.this,userid);
        Menu menu=pm_userid.getMenu();
        // 这里的R.menu.catagory是第一步定义的menu下的文件名catagory.xml
        pm_userid.getMenuInflater().inflate(R.menu.catagory,menu);
        handler_userid=new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:{
                        data_userid=(ArrayList) msg.obj;
                    }
                }
                for(int i=0;i<data_userid.size();i++){
                    // 1-组别、2-数据项id、3-数据项顺序、4-数据项内容
                    menu.add(0,i,i,String.valueOf(data_userid.get(i)));
                    System.out.println(data_userid.get(i));
                }
                // 添加单击数据项事件
                pm_userid.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // 选择某一个分类后将控件的值改为数据项的内容
                        userid.setText(item.getTitle());
                        return true;
                    }
                });
                pm_userid.show();
            }
        };
        // 数据库查询所有分类的方法
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    JSONArray jsonArray = getData.getData("User", "null");
                    //System.out.println(jsonArray);
                    data_userid = new ArrayList();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                        data_userid.add(jsonObject.getString("ID"));
                    }
                    data_userid.add("1");
                    System.out.println(data_userid);
                    Message msg=new Message();
                    msg.obj=data_userid;
                    msg.what=1;
                    handler_userid.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        // 将分类全部添加到数据项中
    }
    public class set implements View.OnClickListener{
        @Override
        public void onClick(View view){
            id_str=String.valueOf(id.getText());
            usedeptid_str= String.valueOf(usedeptid.getText());
            date_str=String.valueOf(date.getText());
            des_str=String.valueOf(des.getText());
            userid_str=String.valueOf(userid.getText());
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        System.out.println("{\"ID\":\""+id_str+"\","+ "\"UseDeptID\":\""+usedeptid_str+"\","+ "\"MFJChuDate\":\""+date_str+"\"," +
                                "\"MFJChuDes\":\""+des_str+"\"," +
                                "\"UserID\":\""+userid_str+"\"" +
                                "}");
                        addData.addData("MFJChu","{\"ID\":\""+id_str+"\","+ "\"UseDeptID\":\""+usedeptid_str+"\","+ "\"MFJChuDate\":\""+date_str+"\","+
                                "\"MFJChuDes\":\""+des_str+"\"," +
                                "\"UserID\":\""+userid_str+"\"" +
                                "}");
                        Intent intent=new Intent(private_inventmanage_SetOutData_AlertDialog.this,private_InventManageActivity.class);
                        intent.putExtra("page",1);
                        startActivity(intent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();

        }
    }
    public class cancel implements  View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent=new Intent(private_inventmanage_SetOutData_AlertDialog.this,private_InventManageActivity.class);
            intent.putExtra("page",1);
            startActivity(intent);
        }
    }
}