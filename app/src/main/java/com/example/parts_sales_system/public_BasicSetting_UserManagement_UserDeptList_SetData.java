package com.example.parts_sales_system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parts_sales_system.data.api_connection.delData;
import com.example.parts_sales_system.data.api_connection.modifyData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class public_BasicSetting_UserManagement_UserDeptList_SetData extends Activity {
    private TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,UserDeptCodeID,SuperiorUserDeptCodeID;
    private EditText UseDeptCode,UseDeptName,UseDeptAllName,UseDeptDes;
    private Button modify_button,del_button,close_button;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_basic_setting_usermanagement_userdeptlist_setdata);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        CreateBy=findViewById(R.id.CreateBy);
        CreateBy.setText((String)data.get("CreateBy"));
        CreateDateTime=findViewById(R.id.CreateDateTime);
        CreateDateTime.setText((String)data.get("CreateDateTime"));
        UpdateBy=findViewById(R.id.UpdateBy);
        UpdateBy.setText((String)data.get("UpdateBy"));
        UpdateDateTime=findViewById(R.id.UpdateDateTime);
        UpdateDateTime.setText((String)data.get("UpdateDateTime"));
        UserDeptCodeID=findViewById(R.id.UserDeptCodeID);
        UserDeptCodeID.setText((String)data.get("UserDeptCodeID"));
        SuperiorUserDeptCodeID=findViewById(R.id.SuperiorUserDeptCodeID);
        SuperiorUserDeptCodeID.setText((String)data.get("SuperiorUserDeptCodeID"));

        UseDeptCode=findViewById(R.id.UseDeptCode);
        UseDeptCode.setText((String)data.get("UseDeptCode"));
        UseDeptName=findViewById(R.id.UseDeptName);
        UseDeptName.setText((String)data.get("UseDeptName"));
        UseDeptAllName=findViewById(R.id.UseDeptAllName);
        UseDeptAllName.setText((String)data.get("UseDeptAllName"));
        UseDeptDes=findViewById(R.id.UseDeptDes);
        UseDeptDes.setText((String)data.get("UseDeptDes"));

        modify_button=findViewById(R.id.modify_info);
        modify_button.setOnClickListener(new buttonClick());
        del_button=findViewById(R.id.del_info);
        del_button.setOnClickListener(new buttonClick());
        close_button = findViewById(R.id.close_item);
        close_button.setOnClickListener(new buttonClick());

    }
    public class buttonClick implements  View.OnClickListener{
        @Override
        public void onClick(View view){
            //将ID和可修改的字段赋值到JSONObject中
            JSONObject jsonObject = new JSONObject();
            String jsonObjectstring;
            switch (view.getId()){
                case R.id.modify_info:
                    try {
                        jsonObject.put("ID",String.valueOf(UserDeptCodeID.getText()))
                                .put("UseDeptCode",UseDeptCode.getText().toString())
                                .put("UseDeptName",UseDeptName.getText().toString())
                                .put("UseDeptAllName",UseDeptAllName.getText().toString())
                                .put("UseDeptDes",UseDeptDes.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                modifyData.modifyData("UseDept",jsonObjectstring);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    intent=new Intent(public_BasicSetting_UserManagement_UserDeptList_SetData.this,public_BasicSettingActivity.class);
                    intent.putExtra("page",0);
                    startActivity(intent);
                    break;
                case R.id.del_info:
                    try {
                        jsonObject.put("ID",String.valueOf(UserDeptCodeID.getText()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    System.out.println(jsonObjectstring);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                delData.delData("UseDept",jsonObjectstring);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    intent=new Intent(public_BasicSetting_UserManagement_UserDeptList_SetData.this,public_BasicSettingActivity.class);
                    intent.putExtra("page",0);
                    startActivity(intent);
                    break;
                case R.id.close_item:
                    intent=new Intent(public_BasicSetting_UserManagement_UserDeptList_SetData.this,public_BasicSettingActivity.class);
                    intent.putExtra("page",0);
                    startActivity(intent);
                    break;
            }
        }
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
}