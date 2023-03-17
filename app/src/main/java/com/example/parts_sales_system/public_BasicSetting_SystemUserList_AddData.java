package com.example.parts_sales_system;

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

import com.example.parts_sales_system.data.api_connection.addData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class public_BasicSetting_SystemUserList_AddData extends Activity {
    private EditText userNumber,user,phone,userloginname,userloginpwd,userdes,usertype,userwechat,userstatue,userlogintime;
    private Spinner validity,usedeptID,deptID;
    private Button add_info,close_item;
    private String[] valArray,usedeptArray,deptidArray;
    private String deptIDstring,userNumberstring,userstring,phonestring,userloginnamestring,usedeptIDstring,valstring,
            userloginpwdstring,userdesstring,usertypestring,userwechatstring,userstatuestring,userlogintimestring;
    private Intent intent;

    JSONObject jsonObject = new JSONObject();
    String jsonObjectstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_basic_setting_systemuserlist_adddata);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        usedeptArray = (String[]) getIntent().getSerializableExtra("array1");
        valArray = (String[]) getIntent().getSerializableExtra("array2");
        deptidArray = (String[]) getIntent().getSerializableExtra("array3");


        userNumber=findViewById(R.id.userNumber);
        user=findViewById(R.id.user);
        phone=findViewById(R.id.phone);
        userloginname=findViewById(R.id.userloginname);
        userloginpwd=findViewById(R.id.userloginpwd);
        userdes=findViewById(R.id.userdes);
        usertype=findViewById(R.id.usertype);
        userwechat=findViewById(R.id.userwechat);
        userstatue=findViewById(R.id.userstatue);
        userlogintime=findViewById(R.id.userregistertime);

        deptID=findViewById(R.id.deptID);
        usedeptID=findViewById(R.id.UseDeptID);
        validity=findViewById(R.id.userval);

        //下拉列表的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(public_BasicSetting_SystemUserList_AddData.this, R.layout.common_spinner_list, usedeptArray);
        usedeptID.setAdapter(adapter);
        usedeptID.setSelection(usedeptArray.length-1);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(public_BasicSetting_SystemUserList_AddData.this, R.layout.common_spinner_list, valArray);
        validity.setAdapter(adapter1);
        validity.setSelection(valArray.length-1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(public_BasicSetting_SystemUserList_AddData.this, R.layout.common_spinner_list, deptidArray);
        deptID.setAdapter(adapter2);
        deptID.setSelection(deptidArray.length-1);

        add_info=findViewById(R.id.add_info);
        add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deptIDstring = deptID.getSelectedItem().toString();
                valstring = validity.getSelectedItem().toString();
                usedeptIDstring = usedeptID.getSelectedItem().toString();

                userNumberstring = userNumber.getText().toString();
                userstring =user.getText().toString();
                phonestring = phone.getText().toString();
                userloginnamestring= userloginname.getText().toString();
                userloginpwdstring = userloginpwd.getText().toString();
                userdesstring = userdes.getText().toString();
                usertypestring = usertype.getText().toString();
                userwechatstring = userwechat.getText().toString();
                userstatuestring = userstatue.getText().toString();
                userlogintimestring = userlogintime.getText().toString();

                try {
                    jsonObject.put("ID","")
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
                            .put("DeptID",deptIDstring)
                            .put("UseIsValid",valstring)
                            .put("UseDeptID",usedeptIDstring);
                    jsonObjectstring = String.valueOf(jsonObject);
                    addJsonArrayData(jsonObjectstring);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent=new Intent(public_BasicSetting_SystemUserList_AddData.this,public_BasicSettingActivity.class);
                intent.putExtra("page",2);
                startActivity(intent);
            }
        });
        close_item=findViewById(R.id.close_item);
        close_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(public_BasicSetting_SystemUserList_AddData.this,public_BasicSettingActivity.class);
                intent.putExtra("page",2);
                startActivity(intent);
            }
        });
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
    //向表中新增item
    void addJsonArrayData(String jsonObjectstring){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    addData.addData("User",jsonObjectstring);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}