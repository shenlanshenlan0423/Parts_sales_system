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

public class public_UseManagement_UseAlertAddData_AlertDialog extends Activity {
    private EditText MFJUseYuJingDate,MFJUseYuJingStatus,MFJUseYuJingDes;
    private String[] MFJUseIDArray;
    private Spinner MFJUseID;
    private String MFJUseID_str,MFJUseYuJingDate_str,MFJUseYuJingStatus_str,MFJUseYuJingDes_str;
    private Button add_info,close_item;
    Intent intent;
    JSONObject jsonObject = new JSONObject();
    String jsonObjectstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_usemanagement_installmanagement_usealert_adddata);
        MFJUseIDArray= (String[]) getIntent().getSerializableExtra("array");
        MFJUseID=findViewById(R.id.MFJUseID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(public_UseManagement_UseAlertAddData_AlertDialog.this, R.layout.common_spinner_list, MFJUseIDArray);
        MFJUseID.setAdapter(adapter); // 设置下拉框的数组适配器
        MFJUseID.setSelection(MFJUseIDArray.length-1); // 设置下拉框默认显示最后一项的测试例子

        MFJUseYuJingDate=findViewById(R.id.MFJUseYuJingDate);
        MFJUseYuJingStatus=findViewById(R.id.MFJUseYuJingStatus);
        MFJUseYuJingDes=findViewById(R.id.MFJUseYuJingDes);

        add_info=findViewById(R.id.add_info);
        add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFJUseID_str = MFJUseID.getSelectedItem().toString();
                MFJUseYuJingDate_str= MFJUseYuJingDate.getText().toString();
                MFJUseYuJingStatus_str=MFJUseYuJingStatus.getText().toString();
                MFJUseYuJingDes_str=MFJUseYuJingDes.getText().toString();
                try {
                    jsonObject.put("ID","").put("MFJUseID",MFJUseID_str)
                            .put("MFJUseYuJingDate",MFJUseYuJingDate_str).put("MFJUseYuJingStatus",MFJUseYuJingStatus_str)
                            .put("MFJUseYuJingDes",MFJUseYuJingDes_str);
                    jsonObjectstring = String.valueOf(jsonObject);
                    addJsonArrayData(jsonObjectstring);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent=new Intent(public_UseManagement_UseAlertAddData_AlertDialog.this,public_UseManagementActivity.class);
                intent.putExtra("page",3);
                startActivity(intent);
            }
        });
        close_item=findViewById(R.id.close_item);
        close_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(public_UseManagement_UseAlertAddData_AlertDialog.this,public_UseManagementActivity.class);
                intent.putExtra("page",3);
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
                    addData.addData("MFJUseYuJing",jsonObjectstring);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}