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

public class private_UseManagement_PatrolManagement_PatrolList_AddData extends Activity {
    private EditText MFJXunJianDate,MFJXunJianCont,MFJXunJianUser;
    private String[] BuildRecordCodeIDStringArray;
    private Spinner BuildRecordCodeID;
    private String BuildRecordCodeIDString,MFJXunJianDateString,MFJXunJianContString,MFJXunJianUserString;
    private Button add_info,close_item;
    Intent intent;
    JSONObject jsonObject = new JSONObject();
    String jsonObjectstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_use_management_patrolrecordlist_adddata);
        BuildRecordCodeIDStringArray = (String[]) getIntent().getSerializableExtra("array");
        BuildRecordCodeID=findViewById(R.id.BuildRecordCodeID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(private_UseManagement_PatrolManagement_PatrolList_AddData.this, R.layout.common_spinner_list, BuildRecordCodeIDStringArray);
        BuildRecordCodeID.setAdapter(adapter); // 设置下拉框的数组适配器
        BuildRecordCodeID.setSelection(BuildRecordCodeIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子

        MFJXunJianDate=findViewById(R.id.MFJXunJianDate);
        MFJXunJianCont=findViewById(R.id.MFJXunJianCont);
        MFJXunJianUser=findViewById(R.id.MFJXunJianUser);

        add_info=findViewById(R.id.add_info);
        add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildRecordCodeIDString = BuildRecordCodeID.getSelectedItem().toString();
                MFJXunJianDateString= MFJXunJianDate.getText().toString();
                MFJXunJianContString=MFJXunJianCont.getText().toString();
                MFJXunJianUserString=MFJXunJianUser.getText().toString();
                try {
                    jsonObject.put("ID","").put("MFJUseID",BuildRecordCodeIDString)
                            .put("MFJXunJianDate",MFJXunJianDateString).put("MFJXunJianCont",MFJXunJianContString)
                            .put("MFJXunJianUser",MFJXunJianUserString);
                    jsonObjectstring = String.valueOf(jsonObject);
                    addJsonArrayData(jsonObjectstring);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent=new Intent(private_UseManagement_PatrolManagement_PatrolList_AddData.this,private_UseManagementActivity.class);
                intent.putExtra("page",0);
                startActivity(intent);
            }
        });
        close_item=findViewById(R.id.close_item);
        close_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(private_UseManagement_PatrolManagement_PatrolList_AddData.this,private_UseManagementActivity.class);
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
                    addData.addData("MFJXunJian",jsonObjectstring);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}