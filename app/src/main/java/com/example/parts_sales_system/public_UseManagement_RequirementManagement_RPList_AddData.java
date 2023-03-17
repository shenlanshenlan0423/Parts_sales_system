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

public class public_UseManagement_RequirementManagement_RPList_AddData extends Activity {
    private EditText MFJXuQiuDate,MFJXuQiuCont,MFJXuQiuUser;
    private String[] BuildPlanningCodeIDStringArray;
    private Spinner BuildPlanningCodeID;
    private String BuildPlanningCodeIDString,MFJXuQiuDateString,MFJXuQiuContString,MFJXuQiuUserString;
    private Button add_info,close_item;
    Intent intent;
    JSONObject jsonObject = new JSONObject();
    String jsonObjectstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_use_management_rpllist_adddata);
        BuildPlanningCodeIDStringArray = (String[]) getIntent().getSerializableExtra("array");
        BuildPlanningCodeID=findViewById(R.id.BuildPlanningCodeID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(public_UseManagement_RequirementManagement_RPList_AddData.this, R.layout.common_spinner_list, BuildPlanningCodeIDStringArray);
        BuildPlanningCodeID.setAdapter(adapter); // 设置下拉框的数组适配器
        BuildPlanningCodeID.setSelection(BuildPlanningCodeIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子

        MFJXuQiuDate=findViewById(R.id.MFJXuQiuDate);
        MFJXuQiuCont=findViewById(R.id.MFJXuQiuCont);
        MFJXuQiuUser=findViewById(R.id.MFJXuQiuUser);

        add_info=findViewById(R.id.add_info);
        add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuildPlanningCodeIDString = BuildPlanningCodeID.getSelectedItem().toString();
                MFJXuQiuDateString= MFJXuQiuDate.getText().toString();
                MFJXuQiuContString=MFJXuQiuCont.getText().toString();
                MFJXuQiuUserString=MFJXuQiuUser.getText().toString();
                try {
                    jsonObject.put("ID","").put("MFJUseID",BuildPlanningCodeIDString)
                            .put("MJFXuQiuDate",MFJXuQiuDateString).put("MJFXuQiuCont",MFJXuQiuContString)
                            .put("MJFXuQiuUser",MFJXuQiuUserString);
                    jsonObjectstring = String.valueOf(jsonObject);
                    addJsonArrayData(jsonObjectstring);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent=new Intent(public_UseManagement_RequirementManagement_RPList_AddData.this,public_UseManagementActivity.class);
                intent.putExtra("page",0);
                startActivity(intent);
            }
        });
        close_item=findViewById(R.id.close_item);
        close_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(public_UseManagement_RequirementManagement_RPList_AddData.this,public_UseManagementActivity.class);
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
                    addData.addData("MJFXuQiu",jsonObjectstring);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}