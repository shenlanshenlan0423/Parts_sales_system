package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.parts_sales_system.data.api_connection.addData;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class public_BasicSetting_ProductData_MFJList_SetData extends Activity {
    TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,MFJID;
    EditText MFJName,MFJXing,MFJWaiJing,MFJGuang,MFJYuJiGeng,MFJDes,MFJZaiTu,MFJTuiHuo,MFJZaiKu,MFJChuKu,MFJZaiYong,MFJModelNo,MFJModelName,MFJModelDes,
            MFJModelIfYou,MFJModelDate,MFJModelDan,MFJModelIfShou;
    Button modify_button,del_button,close_button;
    String id;
    String order;
    String date;
    String des;
    String orderid;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_basic_setting_product_data_mfjlist_detailed);
        Intent intent=getIntent();
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
        MFJZaiTu.setText((String)data.get("MFJZaiTu"));
        MFJTuiHuo=findViewById(R.id.MFJTuiHuo);
        MFJTuiHuo.setText((String)data.get("MFJTuiHuo"));
        MFJZaiKu=findViewById(R.id.MFJZaiKu);
        MFJZaiKu.setText((String)data.get("MFJZaiKu"));
        MFJChuKu=findViewById(R.id.MFJChuKu);
        MFJChuKu.setText((String)data.get("MFJChuKu"));
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
            Intent intent=new Intent(public_BasicSetting_ProductData_MFJList_SetData.this,public_BasicSettingActivity.class);
            startActivity(intent);
        }
    }
//    public class set implements View.OnClickListener{
//        @Override
//        public void onClick(View view){
//            order= String.valueOf(mfjyan_order.getText());
//            date=String.valueOf(mfjyan_date.getText());
//            des=String.valueOf(mfjyan_des.getText());
//            new Thread(new Runnable(){
//                @Override
//                public void run() {
//                    try {
//                        addData.addData("MFJYan","{\"ID\":\""+id+"\","+ "\"MFJOrderID\":\""+orderid+"\","+ "\"MFJYanDate\":\""+date+"\",\n" +
//                                "\"MFJYanOrder\":\""+order+"\","+
//                                "\"MFJYanDes\":\""+des+"\"," +
//                                "\"UserID\":\""+userid+"\"" +
//                                "}");
//                        Intent intent=new Intent(public_BasicSetting_ProductData_MFJList_SetData.this,public_BasicSettingActivity.class);
//                        startActivity(intent);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }).start();
//
//        }
//    }
//    public class cancel implements  View.OnClickListener{
//        @Override
//        public void onClick(View view){
//            Intent intent=new Intent(public_BasicSetting_ProductData_MFJList_SetData.this,public_BasicSettingActivity.class);
//            startActivity(intent);
//        }
//    }
}