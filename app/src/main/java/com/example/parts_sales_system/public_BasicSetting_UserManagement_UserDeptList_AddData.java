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
import android.widget.Toast;

import com.example.parts_sales_system.data.api_connection.addData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class public_BasicSetting_UserManagement_UserDeptList_AddData extends Activity {
    EditText UseDeptCode,UseDeptName,UseDeptAllName,UseDeptDes;
    private String[] SuperiorUseDeptIDStringArray;
    private Spinner SuperiorUserDeptCodeID;
    private Button add_info,close_item;
    Intent intent;
    JSONObject jsonObject = new JSONObject();
    String jsonObjectstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_basic_setting_usermanagement_userdeptlist_adddata);
        SuperiorUseDeptIDStringArray = (String[]) getIntent().getSerializableExtra("array");
        SuperiorUserDeptCodeID=findViewById(R.id.SuperiorUserDeptCodeID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(public_BasicSetting_UserManagement_UserDeptList_AddData.this, R.layout.common_spinner_list, SuperiorUseDeptIDStringArray);
        SuperiorUserDeptCodeID.setAdapter(adapter); // 设置下拉框的数组适配器
        SuperiorUserDeptCodeID.setSelection(SuperiorUseDeptIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子

        UseDeptCode=findViewById(R.id.UseDeptCode);
        UseDeptName=findViewById(R.id.UseDeptName);
        UseDeptAllName=findViewById(R.id.UseDeptAllName);
        UseDeptDes=findViewById(R.id.UseDeptDes);

        add_info=findViewById(R.id.add_info);
        add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    jsonObject.put("ID","")
                            .put("UseDeptCode",UseDeptCode.getText().toString())
                            .put("UseDeptName",UseDeptName.getText().toString())
                            .put("UseDeptAllName",UseDeptAllName.getText().toString())
                            .put("UseDeptDes",UseDeptDes.getText().toString());

                    jsonObjectstring = String.valueOf(jsonObject);
                    addJsonArrayData(jsonObjectstring);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent=new Intent(public_BasicSetting_UserManagement_UserDeptList_AddData.this,public_BasicSettingActivity.class);
                intent.putExtra("page",0);
                startActivity(intent);
            }
        });
        close_item=findViewById(R.id.close_item);
        close_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(public_BasicSetting_UserManagement_UserDeptList_AddData.this,public_BasicSettingActivity.class);
                intent.putExtra("page",0);
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
                    addData.addData("UseDept",jsonObjectstring);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}