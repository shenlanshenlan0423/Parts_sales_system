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

public class public_BasicSetting_UserManagement_SystemUserLog_SetData extends Activity {
    private TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,UserOperateLogCodeID,UserOperIP,
            UserOperLogDes,UserOperSource,UserOperName,UserOperType;
    private Button close_button;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_basic_setting_usermanagement_systemuserlog_setdata);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        CreateBy=findViewById(R.id.CreateBy);
        CreateBy.setText((String)data.get("CreateBy"));
        CreateDateTime=findViewById(R.id.CreateDateTime);
        CreateDateTime.setText((String)data.get("CreateDateTime"));
        UpdateBy=findViewById(R.id.UpdateBy);
        UpdateBy.setText((String)data.get("UpdateBy"));
        UpdateDateTime=findViewById(R.id.UpdateDateTime);
        UpdateDateTime.setText((String)data.get("UpdateDateTime"));
        UserOperateLogCodeID=findViewById(R.id.UserOperateLogCodeID);
        UserOperateLogCodeID.setText((String)data.get("UserOperateLogCodeID"));
        UserOperIP=findViewById(R.id.UserOperIP);
        UserOperIP.setText((String)data.get("UserOperIP"));

        UserOperLogDes=findViewById(R.id.UserOperLogDes);
        UserOperLogDes.setText((String)data.get("UserOperLogDes"));
        UserOperSource=findViewById(R.id.UserOperSource);
        UserOperSource.setText((String)data.get("UserOperSource"));
        UserOperName=findViewById(R.id.UserOperName);
        UserOperName.setText((String)data.get("UserOperName"));
        UserOperType=findViewById(R.id.UserOperType);
        UserOperType.setText((String)data.get("UserOperType"));

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
    public class buttonClick implements  View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.close_item:
                    intent=new Intent(public_BasicSetting_UserManagement_SystemUserLog_SetData.this,public_BasicSettingActivity.class);
                    intent.putExtra("page",1);
                    startActivity(intent);
                    break;
            }
        }
    }
}