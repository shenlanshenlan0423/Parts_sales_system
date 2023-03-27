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

public class public_UseManagement_RManagement_RPList_SetData extends Activity {
    private TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,MFJXuQiuCodeID,MFJID;
    private EditText MFJXuQiuNum,MFJXuQiuTime,MFJXuQiuDes;
    private Button modify_button,del_button,close_button;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_use_management_rpllist_setdata);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        CreateBy=findViewById(R.id.CreateBy);
        CreateBy.setText((String)data.get("CreateBy"));
        CreateDateTime=findViewById(R.id.CreateDateTime);
        CreateDateTime.setText((String)data.get("CreateDateTime"));
        UpdateBy=findViewById(R.id.UpdateBy);
        UpdateBy.setText((String)data.get("UpdateBy"));
        UpdateDateTime=findViewById(R.id.UpdateDateTime);
        UpdateDateTime.setText((String)data.get("UpdateDateTime"));

        MFJXuQiuCodeID=findViewById(R.id.MFJXuQiuCodeID);
        MFJXuQiuCodeID.setText((String)data.get("MFJXuQiuCodeID"));
        MFJID = findViewById(R.id.MFJID);
        MFJID.setText((String)data.get("MFJID"));

        MFJXuQiuNum=findViewById(R.id.MFJXuQiuNum);
        MFJXuQiuNum.setText((String)data.get("MFJXuQiuNum"));
        MFJXuQiuTime=findViewById(R.id.MFJXuQiuTime);
        MFJXuQiuTime.setText((String)data.get("MFJXuQiuTime"));
        MFJXuQiuDes=findViewById(R.id.MFJXuQiuDes);
        MFJXuQiuDes.setText((String)data.get("MFJXuQiuDes"));

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
                    try {
                        jsonObject.put("ID",String.valueOf(MFJXuQiuCodeID.getText()))
                                .put("MJFID",MFJID.getText().toString())
                                .put("MFJXuQiuNum",MFJXuQiuNum.getText().toString())
                                .put("MFJXuQiuTime",MFJXuQiuTime.getText().toString())
                                .put("MFJXuQiuDes",MFJXuQiuDes.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                modifyData.modifyData("MJFXuQiu",jsonObjectstring);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    intent=new Intent(public_UseManagement_RManagement_RPList_SetData.this,public_UseManagementActivity.class);
                    intent.putExtra("page",5);
                    startActivity(intent);
                    break;
                case R.id.del_info:
                    try {
                        jsonObject.put("ID",String.valueOf(MFJXuQiuCodeID.getText()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                delData.delData("MFJXuQiu",jsonObjectstring);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    intent=new Intent(public_UseManagement_RManagement_RPList_SetData.this,public_UseManagementActivity.class);
                    intent.putExtra("page",5);
                    startActivity(intent);
                    break;
                case R.id.close_item:
                    intent=new Intent(public_UseManagement_RManagement_RPList_SetData.this,public_UseManagementActivity.class);
                    intent.putExtra("page",5);
                    startActivity(intent);
                    break;
            }
        }
    }
}