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


public class public_FinancialManagement_PaymentManagement_OrderList_AddData extends Activity {
    private EditText MFJDingKuanDate,MFJDingKuanNum,MFJDingKuanDes,MFJDingKuanOrder;
    private String[] OrderCodeIDStringArray,UserCodeIDStringArray;
    private Spinner OrderCodeID,UserCodeID;
    private String OrderCodeIDString,UserCodeIDString,MFJDingKuanDateString,MFJDingKuanNumString,MFJDingKuanDesString,MFJDingKuanOrderString;
    private Button add_info,close_item;
    Intent intent;
    JSONObject jsonObject = new JSONObject();
    String jsonObjectstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_financial_management_orderpaymentlist_adddata);
        OrderCodeIDStringArray = (String[]) getIntent().getSerializableExtra("array1");
        OrderCodeID=findViewById(R.id.OrderCodeID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(public_FinancialManagement_PaymentManagement_OrderList_AddData.this, R.layout.common_spinner_list, OrderCodeIDStringArray);
        OrderCodeID.setAdapter(adapter1); // 设置下拉框的数组适配器
        OrderCodeID.setSelection(OrderCodeIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子

        UserCodeIDStringArray = (String[]) getIntent().getSerializableExtra("array2");
        UserCodeID=findViewById(R.id.UserCodeID);
        //下拉列表的数组适配器
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(public_FinancialManagement_PaymentManagement_OrderList_AddData.this, R.layout.common_spinner_list, UserCodeIDStringArray);
        UserCodeID.setAdapter(adapter2);
        UserCodeID.setSelection(UserCodeIDStringArray.length-1);

        MFJDingKuanDate=findViewById(R.id.MFJDingKuanDate);
        MFJDingKuanNum=findViewById(R.id.MFJDingKuanNum);
        MFJDingKuanDes=findViewById(R.id.MFJDingKuanDes);
        MFJDingKuanOrder=findViewById(R.id.MFJDingKuanOrder);

        add_info=findViewById(R.id.add_info);
        add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderCodeIDString = OrderCodeID.getSelectedItem().toString();
                UserCodeIDString = UserCodeID.getSelectedItem().toString();
                MFJDingKuanDateString= MFJDingKuanDate.getText().toString();
                MFJDingKuanNumString=MFJDingKuanNum.getText().toString();
                MFJDingKuanDesString=MFJDingKuanDes.getText().toString();
                MFJDingKuanOrderString=MFJDingKuanOrder.getText().toString();
                try {
                    jsonObject.put("ID","").put("MFJOrderID",OrderCodeIDString).put("UserID",UserCodeIDString)
                            .put("MFJDingKuanDate",MFJDingKuanDateString).put("MFJDingKuanOrder",MFJDingKuanNumString)
                            .put("MFJDingKuanDes",MFJDingKuanDesString).put("MFJDingKuanNum",MFJDingKuanOrderString);
                    jsonObjectstring = String.valueOf(jsonObject);
                    addJsonArrayData(jsonObjectstring);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent=new Intent(public_FinancialManagement_PaymentManagement_OrderList_AddData.this,public_FinancialManagementActivity.class);
                intent.putExtra("page",0);
                startActivity(intent);
            }
        });
        close_item=findViewById(R.id.close_item);
        close_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(public_FinancialManagement_PaymentManagement_OrderList_AddData.this,public_FinancialManagementActivity.class);
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
                    addData.addData("MFJDingKuan",jsonObjectstring);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}