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

public class public_BasicSetting_ProductData_MFJList_AddData extends Activity {
    EditText MFJName,MFJXing,MFJWaiJing,MFJGuang,MFJYuJiGeng,MFJDes,MFJZaiTu,MFJTuiHuo,MFJZaiKu,MFJChuKu,MFJZaiYong,MFJModelNo,MFJModelName,MFJModelDes,
            MFJModelIfYou,MFJModelDate,MFJModelDan,MFJModelIfShou;
    private String[] UseDeptIDStringArray;
    private Spinner UseDeptID;
    private Button add_info,close_item;
    Intent intent;
    JSONObject jsonObject = new JSONObject();
    String jsonObjectstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_basic_setting_product_data_mfjlist_adddata);
        UseDeptIDStringArray = (String[]) getIntent().getSerializableExtra("array");
        UseDeptID=findViewById(R.id.UseDeptID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(public_BasicSetting_ProductData_MFJList_AddData.this, R.layout.common_spinner_list, UseDeptIDStringArray);
        UseDeptID.setAdapter(adapter); // 设置下拉框的数组适配器
        UseDeptID.setSelection(UseDeptIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子

        MFJName=findViewById(R.id.MFJName);
        MFJXing=findViewById(R.id.MFJXing);
        MFJWaiJing=findViewById(R.id.MFJWaiJing);
        MFJGuang=findViewById(R.id.MFJGuang);
        MFJYuJiGeng=findViewById(R.id.MFJYuJiGeng);
        MFJDes=findViewById(R.id.MFJDes);
        MFJZaiTu=findViewById(R.id.MFJZaiTu);
        MFJZaiTu.setText("数据库中无此字段");
        MFJTuiHuo=findViewById(R.id.MFJTuiHuo);
        MFJZaiKu=findViewById(R.id.MFJZaiKu);
        MFJChuKu=findViewById(R.id.MFJChuKu);
        MFJChuKu.setText("此字段无法写入和更改");
        MFJZaiYong=findViewById(R.id.MFJZaiYong);
        MFJModelNo=findViewById(R.id.MFJModelNo);
        MFJModelName=findViewById(R.id.MFJModelName);
        MFJModelDes=findViewById(R.id.MFJModelDes);
        MFJModelIfYou=findViewById(R.id.MFJModelIfYou);
        MFJModelDate=findViewById(R.id.MFJModelDate);
        MFJModelDan=findViewById(R.id.MFJModelDan);
        MFJModelIfShou=findViewById(R.id.MFJModelIfShou);
        add_info=findViewById(R.id.add_info);
        add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    jsonObject.put("ID","").put("UseDeptID",UseDeptID.getSelectedItem().toString())
                            .put("MFJName",MFJName.getText().toString())
                            .put("MFJXing",MFJXing.getText().toString())
                            .put("MFJWaiJing",MFJWaiJing.getText().toString())
                            .put("MFJGuang",MFJGuang.getText().toString())
                            .put("MFJYuJiGeng",MFJYuJiGeng.getText().toString())
                            .put("MFJDes",MFJDes.getText().toString())
                            .put("MFJTuiHuo",MFJTuiHuo.getText().toString())
                            .put("MFJZaiKu",MFJZaiKu.getText().toString())
                            .put("MFJZaiYong",MFJZaiYong.getText().toString())
                            .put("MFJModelNo",MFJModelNo.getText().toString())
                            .put("MFJModelName",MFJModelName.getText().toString())
                            .put("MFJModelDes",MFJModelDes.getText().toString())
                            .put("MFJModelIfYou",MFJModelIfYou.getText().toString())
                            .put("MFJModelDate",MFJModelDate.getText().toString())
                            .put("MFJModelDan",MFJModelDan.getText().toString())
                            .put("MFJModelIfShou",MFJModelIfShou.getText().toString());
                    jsonObjectstring = String.valueOf(jsonObject);
                    addJsonArrayData(jsonObjectstring);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent=new Intent(public_BasicSetting_ProductData_MFJList_AddData.this,public_BasicSettingActivity.class);
                intent.putExtra("page",2);
                startActivity(intent);
            }
        });
        close_item=findViewById(R.id.close_item);
        close_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(public_BasicSetting_ProductData_MFJList_AddData.this,public_BasicSettingActivity.class);
                intent.putExtra("page",2);
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
                    addData.addData("MFJ",jsonObjectstring);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}