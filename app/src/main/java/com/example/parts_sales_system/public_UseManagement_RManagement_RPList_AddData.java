package com.example.parts_sales_system;

import android.annotation.SuppressLint;
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

public class public_UseManagement_RManagement_RPList_AddData extends Activity {
    private EditText MFJXuQiuNum,MFJXuQiuTime,MFJXuQiuDes;
    private String[] MFJXuQiuCodeIDStringArray,MFJIDStringArray;
    private Spinner MFJXuQiuCodeID,MFJID;
    private Button add_info,close_item;
    Intent intent;
    JSONObject jsonObject = new JSONObject();
    String jsonObjectstring;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_use_management_rpllist_adddata);
        MFJXuQiuCodeIDStringArray = (String[]) getIntent().getSerializableExtra("MFJXuQiuCodeID");
        MFJXuQiuCodeID=findViewById(R.id.MFJXuQiuCodeID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(public_UseManagement_RManagement_RPList_AddData.this, R.layout.common_spinner_list, MFJXuQiuCodeIDStringArray);
        MFJXuQiuCodeID.setAdapter(adapter1); // 设置下拉框的数组适配器
        MFJXuQiuCodeID.setSelection(MFJXuQiuCodeIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子
        
        MFJIDStringArray = (String[]) getIntent().getSerializableExtra("MFJID");
        MFJID=findViewById(R.id.MFJID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(public_UseManagement_RManagement_RPList_AddData.this, R.layout.common_spinner_list, MFJIDStringArray);
        MFJID.setAdapter(adapter); // 设置下拉框的数组适配器
        MFJID.setSelection(MFJIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子

        MFJXuQiuNum=findViewById(R.id.MFJXuQiuNum);
        MFJXuQiuTime=findViewById(R.id.MFJXuQiuTime);
        MFJXuQiuDes=findViewById(R.id.MFJXuQiuDes);

        add_info=findViewById(R.id.add_info);
        add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    jsonObject.put("ID","").put("MFJXuQiuCodeID",MFJXuQiuCodeID.getSelectedItem().toString())
                            .put("MFJID",MFJID.getSelectedItem().toString())
                            .put("MJFXuQiuNum",MFJXuQiuNum.getText().toString())
                            .put("MJFXuQiuTime",MFJXuQiuTime.getText().toString())
                            .put("MJFXuQiuDes",MFJXuQiuDes.getText().toString());
                    jsonObjectstring = String.valueOf(jsonObject);
                    addJsonArrayData(jsonObjectstring);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent=new Intent(public_UseManagement_RManagement_RPList_AddData.this,public_UseManagementActivity.class);
                intent.putExtra("page",5);
                startActivity(intent);
            }
        });
        close_item=findViewById(R.id.close_item);
        close_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(public_UseManagement_RManagement_RPList_AddData.this,public_UseManagementActivity.class);
                intent.putExtra("page",5);
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
                    addData.addData("MJFXuQiu",jsonObjectstring);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}