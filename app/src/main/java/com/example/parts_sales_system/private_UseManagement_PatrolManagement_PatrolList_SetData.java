package com.example.parts_sales_system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.parts_sales_system.data.api_connection.delData;
import com.example.parts_sales_system.data.api_connection.modifyData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class private_UseManagement_PatrolManagement_PatrolList_SetData extends Activity {
    private TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,PatrolRecordCodeID;
    private EditText MFJXunJianDate,MFJXunJianCont,MFJXunJianUser;
    private Button modify_button,del_button,close_button;
    private String[] BuildRecordCodeIDStringArray;
    private Spinner BuildRecordCodeID;
    private String MFJXunJianDateString,MFJXunJianContString,MFJXunJianUserString;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_use_management_patrolrecordlist_setdata);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        BuildRecordCodeIDStringArray = (String[]) getIntent().getSerializableExtra("array");
        CreateBy=findViewById(R.id.CreateBy);
        CreateBy.setText((String)data.get("CreateBy"));
        CreateDateTime=findViewById(R.id.CreateDateTime);
        CreateDateTime.setText((String)data.get("CreateDateTime"));
        UpdateBy=findViewById(R.id.UpdateBy);
        UpdateBy.setText((String)data.get("UpdateBy"));
        UpdateDateTime=findViewById(R.id.UpdateDateTime);
        UpdateDateTime.setText((String)data.get("UpdateDateTime"));
        PatrolRecordCodeID=findViewById(R.id.PatrolRecordCodeID);
        PatrolRecordCodeID.setText((String)data.get("PatrolRecordCodeID"));
        BuildRecordCodeID=findViewById(R.id.BuildRecordCodeID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(private_UseManagement_PatrolManagement_PatrolList_SetData.this, R.layout.common_spinner_list, BuildRecordCodeIDStringArray);
        BuildRecordCodeID.setAdapter(adapter); // 设置下拉框的数组适配器
        BuildRecordCodeID.setSelection(BuildRecordCodeIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子

        MFJXunJianDate=findViewById(R.id.MFJXunJianDate);
        MFJXunJianDate.setText((String)data.get("MFJXunJianDate"));
        MFJXunJianCont=findViewById(R.id.MFJXunJianCont);
        MFJXunJianCont.setText((String)data.get("MFJXunJianCont"));
        MFJXunJianUser=findViewById(R.id.MFJXunJianUser);
        MFJXunJianUser.setText((String)data.get("MFJXunJianUser"));

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
                    MFJXunJianDateString= String.valueOf(MFJXunJianDate.getText());
                    MFJXunJianContString=String.valueOf(MFJXunJianCont.getText());
                    MFJXunJianUserString=String.valueOf(MFJXunJianUser.getText());
                    try {
                        jsonObject.put("ID",String.valueOf(PatrolRecordCodeID.getText()))
                                .put("MFJXunJianDate",MFJXunJianDateString).put("MFJXunJianCont",MFJXunJianContString)
                                .put("MFJXunJianUser",MFJXunJianUserString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                modifyData.modifyData("MFJXunJian",jsonObjectstring);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    intent=new Intent(private_UseManagement_PatrolManagement_PatrolList_SetData.this,private_UseManagementActivity.class);
                    startActivity(intent);
                    break;
                case R.id.del_info:
                    try {
                        jsonObject.put("ID",String.valueOf(PatrolRecordCodeID.getText()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                delData.delData("MFJXunJian",jsonObjectstring);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    intent=new Intent(private_UseManagement_PatrolManagement_PatrolList_SetData.this,private_UseManagementActivity.class);
                    startActivity(intent);
                    break;
                case R.id.close_item:
                    intent=new Intent(private_UseManagement_PatrolManagement_PatrolList_SetData.this,private_UseManagementActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}