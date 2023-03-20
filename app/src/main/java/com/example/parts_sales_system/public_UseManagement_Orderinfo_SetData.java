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
import android.widget.TextView;
import android.widget.Toast;

import com.example.parts_sales_system.data.api_connection.addData;
import com.example.parts_sales_system.data.api_connection.delData;
import com.example.parts_sales_system.data.api_connection.modifyData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class public_UseManagement_Orderinfo_SetData extends Activity {
    private TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,ID,MFJOrderID,MFJID;
    private EditText MFJOrderDetShu,MFJOrderDetPrise,MFJOrderDetDes,MFJOrderDetShuShou;
    private Button modify_button,del_button,close_button;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_use_management_orderinfo_set_data);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        CreateBy=findViewById(R.id.CreateBy);
        CreateBy.setText((String)data.get("CreateBy"));
        CreateDateTime=findViewById(R.id.CreateDateTime);
        CreateDateTime.setText((String)data.get("CreateDateTime"));
        UpdateBy=findViewById(R.id.UpdateBy);
        UpdateBy.setText((String)data.get("UpdateBy"));
        UpdateDateTime=findViewById(R.id.UpdateDateTime);
        UpdateDateTime.setText((String)data.get("UpdateDateTime"));
        ID=findViewById(R.id.ID);
        ID.setText((String)data.get("ID"));
        MFJOrderID=findViewById(R.id.MFJOrderID);
        MFJOrderID.setText((String)data.get("MFJOrderID"));
        MFJID=findViewById(R.id.MFJID);
        MFJID.setText((String)data.get("MFJID"));
        MFJOrderDetShu=findViewById(R.id.MFJOrderDetShu);
        MFJOrderDetShu.setText((String)data.get("MFJOrderDetShu"));
        MFJOrderDetPrise=findViewById(R.id.MFJOrderDetPrise);
        MFJOrderDetPrise.setText((String)data.get("MFJOrderDetPrise"));
        MFJOrderDetDes=findViewById(R.id.MFJOrderDetDes);
        MFJOrderDetDes.setText((String)data.get("MFJOrderDetDes"));
        MFJOrderDetShuShou=findViewById(R.id.MFJOrderDetShuShou);
        MFJOrderDetShuShou.setText((String)data.get("MFJOrderDetShuShou"));
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
                    try {
                        jsonObject.put("ID",String.valueOf(ID.getText())).put("MFJOrderID",MFJOrderID.getText().toString())
                                .put("MFJID",MFJID.getText().toString())
                                .put("",MFJOrderDetShu.getText().toString()).put("MFJOrderDetPrise",MFJOrderDetPrise.getText().toString())
                                .put("MFJOrderDetDes",MFJOrderDetDes.getText().toString()).put("MFJOrderDetShuShou",MFJOrderDetShuShou.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                modifyData.modifyData("MFJOrderDet",jsonObjectstring);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    intent=new Intent(public_UseManagement_Orderinfo_SetData.this,public_UseManagementActivity.class);
                    intent.putExtra("page",1);
                    startActivity(intent);
                    break;
                case R.id.del_info:
                    try {
                        jsonObject.put("ID",String.valueOf(ID.getText()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                delData.delData("MFJOrderDet",jsonObjectstring);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    intent=new Intent(public_UseManagement_Orderinfo_SetData.this,public_UseManagementActivity.class);
                    intent.putExtra("page",1);
                    startActivity(intent);
                    break;
                case R.id.close_item:
                    intent=new Intent(public_UseManagement_Orderinfo_SetData.this,public_UseManagementActivity.class);
                    intent.putExtra("page",1);
                    startActivity(intent);
                    break;
            }
        }
    }//禁止侧滑返回方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Toast.makeText(this, "当前页面禁止侧滑返回", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}