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
import android.widget.TextView;
import android.widget.Toast;

import com.example.parts_sales_system.data.api_connection.delData;
import com.example.parts_sales_system.data.api_connection.modifyData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class public_BasicSetting_ProductData_MFJList_SetData extends Activity {
    private TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,MFJID,UseDeptID;
    private EditText MFJName,MFJXing,MFJWaiJing,MFJGuang,MFJYuJiGeng,MFJDes,MFJZaiTu,MFJTuiHuo,MFJZaiKu,MFJChuKu,MFJZaiYong,MFJModelNo,MFJModelName,MFJModelDes,
            MFJModelIfYou,MFJModelDate,MFJModelDan,MFJModelIfShou;
    private Button modify_button,del_button,close_button;
    private String UseDeptIDString,MFJNameString,MFJXingString,MFJWaiJingString,MFJGuangString,MFJYuJiGengString,MFJDesString,MFJZaiTuString,MFJTuiHuoString,MFJZaiKuString,
            MFJChuKuString,MFJZaiYongString,MFJModelNoString,MFJModelNameString,MFJModelDesString,
            MFJModelIfYouString,MFJModelDateString,MFJModelDanString,MFJModelIfShouString;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_basic_setting_product_data_mfjlist_setdata);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        CreateBy=findViewById(R.id.CreateBy);
        CreateBy.setText((String)data.get("CreateBy"));
        CreateDateTime=findViewById(R.id.CreateDateTime);
        CreateDateTime.setText((String)data.get("CreateDateTime"));
        UpdateBy=findViewById(R.id.UpdateBy);
        UpdateBy.setText((String)data.get("UpdateBy"));
        UpdateDateTime=findViewById(R.id.UpdateDateTime);
        UpdateDateTime.setText((String)data.get("UpdateDateTime"));
        MFJID=findViewById(R.id.MFJID);
        MFJID.setText((String)data.get("MFJID"));
        UseDeptID=findViewById(R.id.UseDeptID);
        UseDeptID.setText((String)data.get("UseDeptID"));

        MFJName=findViewById(R.id.MFJName);
        MFJName.setText((String)data.get("MFJName"));
        MFJXing=findViewById(R.id.MFJXing);
        MFJXing.setText((String)data.get("MFJXing"));
        MFJWaiJing=findViewById(R.id.MFJWaiJing);
        MFJWaiJing.setText((String)data.get("MFJWaiJing"));
        MFJGuang=findViewById(R.id.MFJGuang);
        MFJGuang.setText((String)data.get("MFJGuang"));
        MFJYuJiGeng=findViewById(R.id.MFJYuJiGeng);
        MFJYuJiGeng.setText((String)data.get("MFJYuJiGeng"));
        MFJDes=findViewById(R.id.MFJDes);
        MFJDes.setText((String)data.get("MFJDes"));
        MFJZaiTu=findViewById(R.id.MFJZaiTu);
//        MFJZaiTu.setText((String)data.get("MFJZaiTu"));
        MFJZaiTu.setText("数据库中无此字段");
        MFJTuiHuo=findViewById(R.id.MFJTuiHuo);
        MFJTuiHuo.setText((String)data.get("MFJTuiHuo"));
        MFJZaiKu=findViewById(R.id.MFJZaiKu);
        MFJZaiKu.setText((String)data.get("MFJZaiKu"));
        MFJChuKu=findViewById(R.id.MFJChuKu);
//        MFJChuKu.setText((String)data.get("MFJChuKu"));
        MFJChuKu.setText("此字段无法写入和更改");
        MFJZaiYong=findViewById(R.id.MFJZaiYong);
        MFJZaiYong.setText((String)data.get("MFJZaiYong"));
        MFJModelNo=findViewById(R.id.MFJModelNo);
        MFJModelNo.setText((String)data.get("MFJModelNo"));
        MFJModelName=findViewById(R.id.MFJModelName);
        MFJModelName.setText((String)data.get("MFJModelName"));
        MFJModelDes=findViewById(R.id.MFJModelDes);
        MFJModelDes.setText((String)data.get("MFJModelDes"));
        MFJModelIfYou=findViewById(R.id.MFJModelIfYou);
        MFJModelIfYou.setText((String)data.get("MFJModelIfYou"));
        MFJModelDate=findViewById(R.id.MFJModelDate);
        MFJModelDate.setText((String)data.get("MFJModelDate"));
        MFJModelDan=findViewById(R.id.MFJModelDan);
        MFJModelDan.setText((String)data.get("MFJModelDan"));
        MFJModelIfShou=findViewById(R.id.MFJModelIfShou);
        MFJModelIfShou.setText((String)data.get("MFJModelIfShou"));

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
                    UseDeptIDString = String.valueOf(UseDeptID.getText());
                    MFJNameString= String.valueOf(MFJName.getText());
                    MFJXingString=String.valueOf(MFJXing.getText());
                    MFJWaiJingString=String.valueOf(MFJWaiJing.getText());
                    MFJGuangString= String.valueOf(MFJGuang.getText());
                    MFJYuJiGengString=String.valueOf(MFJYuJiGeng.getText());
                    MFJDesString=String.valueOf(MFJDes.getText());
//                    MFJZaiTuString= String.valueOf(MFJZaiTu.getText());
                    MFJTuiHuoString=String.valueOf(MFJTuiHuo.getText());
                    MFJZaiKuString=String.valueOf(MFJZaiKu.getText());
//                    MFJChuKuString= String.valueOf(MFJChuKu.getText());
                    MFJZaiYongString=String.valueOf(MFJZaiYong.getText());
                    MFJModelNoString=String.valueOf(MFJModelNo.getText());
                    MFJModelNameString= String.valueOf(MFJModelName.getText());
                    MFJModelDesString=String.valueOf(MFJModelDes.getText());
                    MFJModelIfYouString=String.valueOf(MFJModelIfYou.getText());
                    MFJModelDateString= String.valueOf(MFJModelDate.getText());
                    MFJModelDanString=String.valueOf(MFJModelDan.getText());
                    MFJModelIfShouString=String.valueOf(MFJModelIfShou.getText());
                    try {
                        jsonObject.put("ID",String.valueOf(MFJID.getText())).put("UseDeptID",UseDeptIDString)
                                .put("MFJName",MFJNameString)
                                .put("MFJXing",MFJXingString).put("MFJWaiJing",MFJWaiJingString)
                                .put("MFJGuang",MFJGuangString).put("MFJYuJiGeng",MFJYuJiGengString)
                                .put("MFJDes",MFJDesString).put("MFJTuiHuo",MFJTuiHuoString)
                                .put("MFJZaiKu",MFJZaiKuString).put("MFJZaiYong",MFJZaiYongString)
                                .put("MFJModelNo",MFJModelNoString).put("MFJModelName",MFJModelNameString)
                                .put("MFJModelDes",MFJModelDesString).put("MFJModelIfYou",MFJModelIfYouString)
                                .put("MFJModelDate",MFJModelDateString).put("MFJModelDan",MFJModelDanString)
                                .put("MFJModelIfShou",MFJModelIfShouString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                modifyData.modifyData("MFJ",jsonObjectstring);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    intent=new Intent(public_BasicSetting_ProductData_MFJList_SetData.this,public_BasicSettingActivity.class);
                    intent.putExtra("page",2);
                    startActivity(intent);
                    break;
                case R.id.del_info:
                    try {
                        jsonObject.put("ID",String.valueOf(MFJID.getText()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    System.out.println(jsonObjectstring);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                delData.delData("MFJ",jsonObjectstring);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    intent=new Intent(public_BasicSetting_ProductData_MFJList_SetData.this,public_BasicSettingActivity.class);
                    intent.putExtra("page",2);
                    startActivity(intent);
                    break;
                case R.id.close_item:
                    intent=new Intent(public_BasicSetting_ProductData_MFJList_SetData.this,public_BasicSettingActivity.class);
                    intent.putExtra("page",2);
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