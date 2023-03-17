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

public class public_UseManagement_FeedBackManagement_LiveFeedBackList_SetData extends Activity {
    private TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,FeedBackCodeID,UserCodeID,MFJID;
    private EditText UserFanKuiStatus,UserFanKuiType,UserFanKuiDes,UserFanKuiNum,UserFanKuiSource;
    private Button modify_button,del_button,close_button;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_use_management_livefeedbacklist_setdata);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        CreateBy=findViewById(R.id.CreateBy);
        CreateBy.setText((String)data.get("CreateBy"));
        CreateDateTime=findViewById(R.id.CreateDateTime);
        CreateDateTime.setText((String)data.get("CreateDateTime"));
        UpdateBy=findViewById(R.id.UpdateBy);
        UpdateBy.setText((String)data.get("UpdateBy"));
        UpdateDateTime=findViewById(R.id.UpdateDateTime);
        UpdateDateTime.setText((String)data.get("UpdateDateTime"));

        FeedBackCodeID=findViewById(R.id.FeedBackCodeID);
        FeedBackCodeID.setText((String)data.get("FeedBackCodeID"));
        UserCodeID=findViewById(R.id.UserCodeID);
        UserCodeID.setText((String)data.get("UserID"));
        MFJID=findViewById(R.id.MFJID);
        MFJID.setText((String)data.get("MFJID"));

        UserFanKuiStatus=findViewById(R.id.UserFanKuiStatus);
        UserFanKuiStatus.setText((String)data.get("UserFanKuiStatus"));
        UserFanKuiType=findViewById(R.id.UserFanKuiType);
        UserFanKuiType.setText((String)data.get("UserFanKuiType"));
        UserFanKuiDes=findViewById(R.id.UserFanKuiDes);
        UserFanKuiDes.setText((String)data.get("UserFanKuiDes"));
        UserFanKuiNum=findViewById(R.id.UserFanKuiNum);
        UserFanKuiNum.setText((String)data.get("UserFanKuiNum"));
        UserFanKuiSource=findViewById(R.id.UserFanKuiSource);
        UserFanKuiSource.setText((String)data.get("UserFanKuiSource"));

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
                        jsonObject.put("ID",String.valueOf(FeedBackCodeID.getText())).put("UserFanKuiStatus",UserFanKuiStatus.getText().toString())
                                .put("UserFanKuiType",UserFanKuiType.getText().toString())
                                .put("UserFanKuiDes",UserFanKuiDes.getText().toString()).put("UserFanKuiNum",UserFanKuiNum.getText().toString())
                                .put("UserFanKuiSource",UserFanKuiSource.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                modifyData.modifyData("UserFanKui",jsonObjectstring);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    intent=new Intent(public_UseManagement_FeedBackManagement_LiveFeedBackList_SetData.this,public_UseManagementActivity.class);
                    intent.putExtra("page",4);
                    startActivity(intent);
                    break;
                case R.id.del_info:
                    try {
                        jsonObject.put("ID",String.valueOf(FeedBackCodeID.getText()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                delData.delData("UserFanKui",jsonObjectstring);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    intent=new Intent(public_UseManagement_FeedBackManagement_LiveFeedBackList_SetData.this,public_UseManagementActivity.class);
                    intent.putExtra("page",4);
                    startActivity(intent);
                    break;
                case R.id.close_item:
                    intent=new Intent(public_UseManagement_FeedBackManagement_LiveFeedBackList_SetData.this,public_UseManagementActivity.class);
                    intent.putExtra("page",4);
                    startActivity(intent);
                    break;
            }
        }
    }//禁止侧滑返回方法
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