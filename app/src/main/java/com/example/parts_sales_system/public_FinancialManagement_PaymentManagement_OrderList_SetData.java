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
import java.util.Arrays;
import java.util.HashMap;

public class public_FinancialManagement_PaymentManagement_OrderList_SetData extends Activity {
    private TextView CreateBy,CreateDateTime,UpdateBy,UpdateDateTime,OrderPaymentCodeID;
    private EditText MFJDingKuanDate,MFJDingKuanNum,MFJDingKuanDes,MFJDingKuanOrder;
    private Button modify_button,del_button,close_button;
    private String[] OrderCodeIDStringArray,UserCodeIDStringArray;
    private Spinner OrderCodeID,UserCodeID;
    private String OrderCodeIDString,UserCodeIDString,MFJDingKuanDateString,MFJDingKuanNumString,MFJDingKuanDesString,MFJDingKuanOrderString;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_financial_management_orderpaymentlist_setdata);
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        CreateBy=findViewById(R.id.CreateBy);
        CreateBy.setText((String)data.get("CreateBy"));
        CreateDateTime=findViewById(R.id.CreateDateTime);
        CreateDateTime.setText((String)data.get("CreateDateTime"));
        UpdateBy=findViewById(R.id.UpdateBy);
        UpdateBy.setText((String)data.get("UpdateBy"));
        UpdateDateTime=findViewById(R.id.UpdateDateTime);
        UpdateDateTime.setText((String)data.get("UpdateDateTime"));
        OrderPaymentCodeID=findViewById(R.id.OrderPaymentCodeID);
        OrderPaymentCodeID.setText((String)data.get("OrderPaymentCodeID"));

        OrderCodeIDStringArray = (String[]) getIntent().getSerializableExtra("array1");
        OrderCodeID=findViewById(R.id.OrderCodeID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(public_FinancialManagement_PaymentManagement_OrderList_SetData.this, R.layout.common_spinner_list, OrderCodeIDStringArray);
        OrderCodeID.setAdapter(adapter1); // 设置下拉框的数组适配器
        OrderCodeID.setSelection(OrderCodeIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子

        UserCodeIDStringArray = (String[]) getIntent().getSerializableExtra("array2");
        //有时候会突然为空？？？
        System.out.println(Arrays.toString(UserCodeIDStringArray));
        UserCodeID=findViewById(R.id.UserCodeID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(public_FinancialManagement_PaymentManagement_OrderList_SetData.this, R.layout.common_spinner_list, UserCodeIDStringArray);
        UserCodeID.setAdapter(adapter2);
        UserCodeID.setSelection(UserCodeIDStringArray.length-1);

        MFJDingKuanDate=findViewById(R.id.MFJDingKuanDate);
        MFJDingKuanDate.setText((String)data.get("MFJDingKuanDate"));
        MFJDingKuanNum=findViewById(R.id.MFJDingKuanNum);
        MFJDingKuanNum.setText((String)data.get("MFJDingKuanNum"));
        MFJDingKuanDes=findViewById(R.id.MFJDingKuanDes);
        MFJDingKuanDes.setText((String)data.get("MFJDingKuanDes"));
        MFJDingKuanOrder=findViewById(R.id.MFJDingKuanOrder);
        MFJDingKuanOrder.setText((String)data.get("MFJDingKuanOrder"));

        modify_button=findViewById(R.id.modify_info);
        modify_button.setOnClickListener(new buttonClick());
        del_button=findViewById(R.id.del_info);
        del_button.setOnClickListener(new buttonClick());
        close_button = findViewById(R.id.close_item);
        close_button.setOnClickListener(new buttonClick());

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
    public class buttonClick implements  View.OnClickListener{
        @Override
        public void onClick(View view){
            //将ID和可修改的字段赋值到JSONObject中
            JSONObject jsonObject = new JSONObject();
            String jsonObjectstring;
            switch (view.getId()){
                case R.id.modify_info:
                    OrderCodeIDString = OrderCodeID.getSelectedItem().toString();
                    UserCodeIDString = UserCodeID.getSelectedItem().toString();
                    MFJDingKuanDateString= MFJDingKuanDate.getText().toString();
                    MFJDingKuanNumString=MFJDingKuanNum.getText().toString();
                    MFJDingKuanDesString=MFJDingKuanDes.getText().toString();
                    MFJDingKuanOrderString=MFJDingKuanOrder.getText().toString();
                    try {
                        jsonObject.put("ID",OrderPaymentCodeID.getText().toString()).put("MFJOrderID",OrderCodeIDString).put("UserID",UserCodeIDString)
                                .put("MFJDingKuanDate",MFJDingKuanDateString).put("MFJDingKuanOrder",MFJDingKuanNumString)
                                .put("MFJDingKuanDes",MFJDingKuanDesString).put("MFJDingKuanNum",MFJDingKuanOrderString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                modifyData.modifyData("MFJDingKuan",jsonObjectstring);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();
                    intent=new Intent(public_FinancialManagement_PaymentManagement_OrderList_SetData.this,public_FinancialManagementActivity.class);
                    startActivity(intent);
                    break;
                case R.id.del_info:
                    try {
                        jsonObject.put("ID",String.valueOf(OrderPaymentCodeID.getText()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //jsonObject转String
                    jsonObjectstring = String.valueOf(jsonObject);
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                delData.delData("MFJDingKuan",jsonObjectstring);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    intent=new Intent(public_FinancialManagement_PaymentManagement_OrderList_SetData.this,public_FinancialManagementActivity.class);
                    startActivity(intent);
                    break;
                case R.id.close_item:
                    intent=new Intent(public_FinancialManagement_PaymentManagement_OrderList_SetData.this,public_FinancialManagementActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}