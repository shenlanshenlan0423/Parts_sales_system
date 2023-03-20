package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;

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

public class public_UseManagement_Orderinfo_AddData extends Activity {

    private EditText MFJOrderDetShu,MFJOrderDetPrise,MFJOrderDetDes,MFJOrderDetShuShou;
    private String[] MFJOrderIDStringArray,MFJIDStringArray;
    private Spinner MFJOrderID,MFJID;
    private Button add_info,close_item;
    Intent intent;
    JSONObject jsonObject = new JSONObject();
    String jsonObjectstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_use_management_orderinfo_add_data);
        MFJOrderIDStringArray = (String[]) getIntent().getSerializableExtra("MFJOrderID");
        MFJOrderID=findViewById(R.id.MFJOrderID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(public_UseManagement_Orderinfo_AddData.this, R.layout.common_spinner_list, MFJOrderIDStringArray);
        MFJOrderID.setAdapter(adapter1); // 设置下拉框的数组适配器
        MFJOrderID.setSelection(MFJOrderIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子
        MFJIDStringArray=(String[])getIntent().getSerializableExtra("MFJID");
        MFJID=findViewById(R.id.MFJID);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(public_UseManagement_Orderinfo_AddData.this, R.layout.common_spinner_list, MFJIDStringArray);
        MFJID.setAdapter(adapter2);
        MFJID.setSelection(MFJIDStringArray.length-1);
        MFJOrderDetShu=findViewById(R.id.MFJOrderDetShu);
        MFJOrderDetPrise=findViewById(R.id.MFJOrderDetPrise);
        MFJOrderDetDes=findViewById(R.id.MFJOrderDetDes);
        MFJOrderDetShuShou=findViewById(R.id.MFJOrderDetShuShou);

        add_info=findViewById(R.id.add_info);
        add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    jsonObject.put("ID","").put("MFJOrderID",MFJOrderID.getSelectedItem().toString())
                            .put("MFJID",MFJID.getSelectedItem().toString())
                            .put("MFJOrderDetShu",MFJOrderDetShu.getText().toString())
                            .put("MFJOrderDetPrise",MFJOrderDetPrise.getText().toString())
                            .put("MFJOrderDetDes",MFJOrderDetDes.getText().toString())
                            .put("MFJOrderDetShuShou",MFJOrderDetShuShou.getText().toString());
                    jsonObjectstring = String.valueOf(jsonObject);
                    addJsonArrayData(jsonObjectstring);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent=new Intent(public_UseManagement_Orderinfo_AddData.this,public_UseManagementActivity.class);
                intent.putExtra("page",1);
                startActivity(intent);
            }
        });
        close_item=findViewById(R.id.close_item);
        close_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(public_UseManagement_Orderinfo_AddData.this,public_UseManagementActivity.class);
                intent.putExtra("page",1);
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
                    addData.addData("MFJOrderDet",jsonObjectstring);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}