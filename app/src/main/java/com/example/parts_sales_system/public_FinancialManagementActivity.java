package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parts_sales_system.data.api_connection.addData;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.databinding.ActivityPublicFinancialManagementBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

public class public_FinancialManagementActivity extends AppCompatActivity {
    private ActivityPublicFinancialManagementBinding binding;
    private TextView
            CreateDateTime1, UpdateDateTime1, OrderPaymentCodeID1, OrderCodeID1, UserCodeID1,
            CreateDateTime2, UpdateDateTime2, OrderPaymentCodeID2, OrderCodeID2, UserCodeID2,
            CreateDateTime3, UpdateDateTime3, OrderPaymentCodeID3, OrderCodeID3, UserCodeID3,
            CreateDateTime4, UpdateDateTime4, OrderPaymentCodeID4, OrderCodeID4, UserCodeID4,
            CreateDateTime5, UpdateDateTime5, OrderPaymentCodeID5, OrderCodeID5, UserCodeID5;
    private Button PaymentManagementButton, OrderPaymentListButton, FirstPage, PreviousPage, NextPage, LastPage;
    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "请选择二级功能！", Toast.LENGTH_SHORT).show();
        binding = ActivityPublicFinancialManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CreateDateTime1 = binding.CreateDateTime1;
        UpdateDateTime1 = binding.UpdateDateTime1;
        OrderPaymentCodeID1 = binding.OrderPaymentCodeID1;
        OrderCodeID1 = binding.OrderCodeID1;
        UserCodeID1 = binding.UserCodeID1;

        CreateDateTime2 = binding.CreateDateTime2;
        UpdateDateTime2 = binding.UpdateDateTime2;
        OrderPaymentCodeID2 = binding.OrderPaymentCodeID2;
        OrderCodeID2 = binding.OrderCodeID2;
        UserCodeID2 = binding.UserCodeID2;

        CreateDateTime3 = binding.CreateDateTime3;
        UpdateDateTime3 = binding.UpdateDateTime3;
        OrderPaymentCodeID3 = binding.OrderPaymentCodeID3;
        OrderCodeID3 = binding.OrderCodeID3;
        UserCodeID3 = binding.UserCodeID3;

        CreateDateTime4 = binding.CreateDateTime4;
        UpdateDateTime4 = binding.UpdateDateTime4;
        OrderPaymentCodeID4 = binding.OrderPaymentCodeID4;
        OrderCodeID4 = binding.OrderCodeID4;
        UserCodeID4 = binding.UserCodeID4;

        CreateDateTime5 = binding.CreateDateTime5;
        UpdateDateTime5 = binding.UpdateDateTime5;
        OrderPaymentCodeID5 = binding.OrderPaymentCodeID5;
        OrderCodeID5 = binding.OrderCodeID5;
        UserCodeID5 = binding.UserCodeID5;


        OrderPaymentListButton = binding.OrderPaymentListButton;
        PaymentManagementButton = binding.PaymentManagementButton;
        //翻页按钮
        FirstPage = binding.FirstPage;
        PreviousPage = binding.PreviousPage;
        NextPage = binding.NextPage;
        LastPage = binding.LastPage;
        //点击二级功能,get表中的全部数据,并赋值给jsonArray
        PaymentManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            JSONArray jdata = getData.getData("MFJDingKuan","");//此处不需要按条件查询，返回全表信息即可
                            jsonArray = jdata;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        //点击三级功能，读取jsonArray中的数据并显示在组件上
        OrderPaymentListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setArrayData(jsonArray);
            }
        });
        //翻页的方法绑定
        FirstPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(public_FinancialManagementActivity.this, "已经是首页了~", Toast.LENGTH_SHORT).show();
            }
        });
        PreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(public_FinancialManagementActivity.this, "已经是首页了~", Toast.LENGTH_SHORT).show();
            }
        });
        NextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(public_FinancialManagementActivity.this, "已经是尾页了~", Toast.LENGTH_SHORT).show();
            }
        });
        LastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(public_FinancialManagementActivity.this, "已经是尾页了~", Toast.LENGTH_SHORT).show();
            }
        });
        //悬浮按钮，用于用户新增item，待完善
//        binding.itemAddFloatButton.fab_add_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addData.addData("UseDept","{'UseDeptName':'test'}");
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    void setArrayData(JSONArray jdata){
        try {
            String CreateDateTimeString, UpdateDateTimeString, OrderPaymentCodeIDString, OrderCodeIDString, UserCodeIDString;
            //寻访json中包含的所有键值对
            int jdata_length = jdata.length();
            for (int i=0; i<jdata_length; i++){
                JSONObject SubjsonObject = jdata.getJSONObject(i);
                CreateDateTimeString = SubjsonObject.getString("createDateTime");
                UpdateDateTimeString = SubjsonObject.getString("updateDateTime");
                OrderPaymentCodeIDString = SubjsonObject.getString("ID");
                OrderCodeIDString = SubjsonObject.getString("MFJOrderID");
                UserCodeIDString = SubjsonObject.getString("UserID");
                // 如果i=5，又会再次进行赋值，再调试调试
                if (i % 5 == 0){
                    // 赋值语句
                    CreateDateTime1.setText(CreateDateTimeString);
                    UpdateDateTime1.setText(UpdateDateTimeString);
                    OrderPaymentCodeID1.setText(OrderPaymentCodeIDString);
                    OrderCodeID1.setText(OrderCodeIDString);
                    UserCodeID1.setText(UserCodeIDString);
                }else if(i % 5 == 1){
                    CreateDateTime2.setText(CreateDateTimeString);
                    UpdateDateTime2.setText(UpdateDateTimeString);
                    OrderPaymentCodeID2.setText(OrderPaymentCodeIDString);
                    OrderCodeID2.setText(OrderCodeIDString);
                    UserCodeID2.setText(UserCodeIDString);
                }else if(i % 5 == 2){
                    CreateDateTime3.setText(CreateDateTimeString);
                    UpdateDateTime3.setText(UpdateDateTimeString);
                    OrderPaymentCodeID3.setText(OrderPaymentCodeIDString);
                    OrderCodeID3.setText(OrderCodeIDString);
                    UserCodeID3.setText(UserCodeIDString);
                }else if(i % 5 == 3){
                    CreateDateTime4.setText(CreateDateTimeString);
                    UpdateDateTime4.setText(UpdateDateTimeString);
                    OrderPaymentCodeID4.setText(OrderPaymentCodeIDString);
                    OrderCodeID4.setText(OrderCodeIDString);
                    UserCodeID4.setText(UserCodeIDString);
                }else if(i % 5 == 4){
                    CreateDateTime5.setText(CreateDateTimeString);
                    UpdateDateTime5.setText(UpdateDateTimeString);
                    OrderPaymentCodeID5.setText(OrderPaymentCodeIDString);
                    OrderCodeID5.setText(OrderCodeIDString);
                    UserCodeID5.setText(UserCodeIDString);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}