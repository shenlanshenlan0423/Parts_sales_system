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

public class public_UseManagement_UseAlertSetData_AlertDialog extends Activity {

    private TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,MFJUseYuJingCodeID,MFJUseID;
    private EditText MFJUseYuJingDate,MFJUseYuJingStatus,MFJUseYuJingDes;
    private Button modify_button,del_button,close_button;
    private String MFJUseYuJingCodeID_str,MFJUseID_str,MFJUseYuJingDate_str,MFJUseYuJingStatus_str,MFJUseYuJingDes_str;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_usemanagement_usealert_setdata);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        CreateBy=findViewById(R.id.CreateBy);
        CreateBy.setText((String)data.get("CreateBy"));
        CreateDateTime=findViewById(R.id.CreateDateTime);
        CreateDateTime.setText((String)data.get("CreateDateTime"));
        UpdateBy=findViewById(R.id.UpdateBy);
        UpdateBy.setText((String)data.get("UpdateBy"));
        UpdateDateTime=findViewById(R.id.UpdateDateTime);
        UpdateDateTime.setText((String)data.get("UpdateDateTime"));

        MFJUseYuJingCodeID=findViewById(R.id.MFJUseYuJingCodeID);
        MFJUseYuJingCodeID.setText((String)data.get("ID"));
        MFJUseID = findViewById(R.id.MFJUseID);
        MFJUseID.setText((String)data.get("MFJUseID"));

        MFJUseYuJingDate=findViewById(R.id.MFJUseYuJingDate);
        MFJUseYuJingDate.setText((String)data.get("MFJUseYuJingDate"));
        MFJUseYuJingStatus=findViewById(R.id.MFJUseYuJingStatus);
        MFJUseYuJingStatus.setText((String)data.get("MFJUseYuJingStatus"));
        MFJUseYuJingDes=findViewById(R.id.MFJUseYuJingDes);
        MFJUseYuJingDes.setText((String)data.get("MFJUseYuJingDes"));

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
    public class buttonClick implements  View.OnClickListener{
        @Override
        public void onClick(View view){
            //将ID和可修改的字段赋值到JSONObject中
            JSONObject jsonObject = new JSONObject();
            String jsonObjectstring;
            switch (view.getId()){
                case R.id.modify_info:
                    MFJUseYuJingCodeID_str= String.valueOf(MFJUseYuJingCodeID.getText());
                    MFJUseID_str= String.valueOf(MFJUseID.getText());
                    MFJUseYuJingDate_str=String.valueOf(MFJUseYuJingDate.getText());
                    MFJUseYuJingStatus_str=String.valueOf(MFJUseYuJingStatus.getText());
                    MFJUseYuJingDes_str=String.valueOf(MFJUseYuJingDes.getText());
                    try {
                        jsonObject.put("ID",MFJUseYuJingCodeID_str)
                                .put("MFJUseID",MFJUseID_str)
                                .put("MFJUseYuJingDate",MFJUseYuJingDate_str)
                                .put("MFJUseYuJingStatus",MFJUseYuJingStatus_str)
                                .put("MFJUseYuJingDes",MFJUseYuJingDes_str);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                modifyData.modifyData("MJFUseYuJing",jsonObjectstring);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    intent=new Intent(public_UseManagement_UseAlertSetData_AlertDialog.this,public_UseManagementActivity.class);
                    intent.putExtra("page",5);
                    startActivity(intent);
                    break;
                case R.id.del_info:
                    try {
                        jsonObject.put("ID",String.valueOf(MFJUseYuJingCodeID.getText()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                delData.delData("MFJUseYuJing",jsonObjectstring);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    intent=new Intent(public_UseManagement_UseAlertSetData_AlertDialog.this,public_UseManagementActivity.class);
                    intent.putExtra("page",5);
                    startActivity(intent);
                    break;
                case R.id.close_item:
                    intent=new Intent(public_UseManagement_UseAlertSetData_AlertDialog.this,public_UseManagementActivity.class);
                    intent.putExtra("page",5);
                    startActivity(intent);
                    break;
            }
        }
    }
}