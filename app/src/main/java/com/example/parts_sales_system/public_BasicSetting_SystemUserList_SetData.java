package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parts_sales_system.data.api_connection.delData;
import com.example.parts_sales_system.data.api_connection.modifyData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class public_BasicSetting_SystemUserList_SetData extends Activity {
    private TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,userID,deptID,usedeptID,userNumber;
    private EditText user,phone,userloginname,userloginpwd,userdes,
            usertype,userwechat,userstatus,userlogintime,validity;
    private Button modify_button,del_button,close_button;
    private String userIDstring,deptIDstring,userNumberstring,userstring,phonestring,userloginnamestring,valstring,usedeptIDstring,
            userloginpwdstring,userdesstring,usertypestring,userwechatstring,userstatuestring,userlogintimestring;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_basic_setting_systemuserlist_setdata);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        CreateBy=findViewById(R.id.CreateBy);
        CreateBy.setText((String)data.get("CreateBy"));
        CreateDateTime=findViewById(R.id.CreateDateTime);
        CreateDateTime.setText((String)data.get("CreateDateTime"));
        UpdateBy=findViewById(R.id.UpdateBy);
        UpdateBy.setText((String)data.get("UpdateBy"));
        UpdateDateTime=findViewById(R.id.UpdateDateTime);
        UpdateDateTime.setText((String)data.get("UpdateDateTime"));
        userID=findViewById(R.id.userID);
        deptID=findViewById(R.id.deptID);
        usedeptID=findViewById(R.id.UseDeptID);
        userNumber=findViewById(R.id.userNumber);
        user=findViewById(R.id.user);
        phone=findViewById(R.id.phone);
        userloginname=findViewById(R.id.userloginname);
        userloginpwd=findViewById(R.id.userloginpwd);
        userdes=findViewById(R.id.userdes);
        validity=findViewById(R.id.userval);
        usertype=findViewById(R.id.usertype);
        userwechat=findViewById(R.id.userwechat);
        userstatus=findViewById(R.id.userstatus);
        userlogintime=findViewById(R.id.userregistertime);

        userID.setText((String)data.get("UserCodeID"));
        deptID.setText((String)data.get("DeptID"));
        usedeptID.setText((String)data.get("UseDeptID"));
        userNumber.setText((String)data.get("UserNo"));
        user.setText((String)data.get("UserName"));
        phone.setText((String)data.get("UserTel"));
        userloginname.setText((String)data.get("UserLoginName"));
        userloginpwd.setText((String)data.get("UserLoginPwd"));
        userdes.setText((String)data.get("UserDes"));
        usertype.setText((String)data.get("UserType"));
        userwechat.setText((String)data.get("UserWebChatOpenID"));
        userstatus.setText((String)data.get("UserStatus"));
        userlogintime.setText((String)data.get("UserRegDate"));
        validity.setText((String)data.get("UserIsValid"));

        modify_button=findViewById(R.id.modify_info);
        modify_button.setOnClickListener(new buttonClick());
        del_button=findViewById(R.id.del_info);
        del_button.setOnClickListener(new buttonClick());
        close_button = findViewById(R.id.close_item);
        close_button.setOnClickListener(new buttonClick());
    }
    //禁止侧滑返回方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Toast.makeText(this, "当前页面禁止侧滑返回", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
    public class buttonClick implements  View.OnClickListener {
        @Override
        public void onClick(View view) {
            //将ID和可修改的字段赋值到JSONObject中
            JSONObject jsonObject = new JSONObject();
            String jsonObjectstring;
            switch (view.getId()) {
                case R.id.modify_info:
                    valstring = validity.getText().toString();
                    userIDstring = String.valueOf(userID.getText());
                    deptIDstring = String.valueOf(deptID.getText());
                    usedeptIDstring = String.valueOf(usedeptID.getText());
                    userNumberstring = String.valueOf(userNumber.getText());
                    userstring = String.valueOf(user.getText());
                    phonestring = String.valueOf(phone.getText());
                    userloginnamestring= String.valueOf(userloginname.getText());
                    userloginpwdstring = String.valueOf(userloginpwd.getText());
                    userdesstring = String.valueOf(userdes.getText());
                    usertypestring = String.valueOf(usertype.getText());
                    userwechatstring = String.valueOf(userwechat.getText());
                    userstatuestring = String.valueOf(userstatus.getText());
                    userlogintimestring = String.valueOf(userlogintime.getText());
                    try {
                        jsonObject.put("ID",userIDstring)
                                .put("DeptID",deptIDstring)
                                .put("UseDeptID",usedeptIDstring)
                                .put("UserNO",userNumberstring)
                                .put("UserName",userstring)
                                .put("UserTel",phonestring)
                                .put("UserLoginName",userloginnamestring)
                                .put("UserLoginPwd",userloginpwdstring)
                                .put("UserDes",userdesstring)
                                .put("UserType",usertypestring)
                                .put("UserWebChatOpenID",userwechatstring)
                                .put("UserStatus",userstatuestring)
                                .put("UserRegDate",userlogintimestring)
                                .put("UseIsValid",valstring);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                System.out.println(jsonObjectstring);
                                modifyData.modifyData("User",jsonObjectstring);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    intent=new Intent(public_BasicSetting_SystemUserList_SetData.this,public_BasicSettingActivity.class);
                    intent.putExtra("page",2);
                    startActivity(intent);
                    break;
                //这里写一个刷新页面的代码
                case R.id.del_info:
                    try {
                        jsonObject.put("ID",String.valueOf(userID.getText()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                delData.delData("User",jsonObjectstring);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    intent=new Intent(public_BasicSetting_SystemUserList_SetData.this,public_BasicSettingActivity.class);
                    intent.putExtra("page",2);
                    startActivity(intent);
                    break;
                case R.id.close_item:
                    intent=new Intent(public_BasicSetting_SystemUserList_SetData.this,public_BasicSettingActivity.class);
                    intent.putExtra("page",2);
                    startActivity(intent);
                    break;

            }
        }
    }
}