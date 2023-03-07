package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parts_sales_system.data.api_connection.addData;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.data.api_connection.modifyData;
import com.example.parts_sales_system.databinding.ActivityPublicFinancialManagementBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class public_FinancialManagementActivity extends AppCompatActivity {
    private ActivityPublicFinancialManagementBinding binding;
    private TextView
            CreateDateTime1, UpdateDateTime1, OrderPaymentCodeID1, OrderCodeID1, UserCodeID1,
            CreateDateTime2, UpdateDateTime2, OrderPaymentCodeID2, OrderCodeID2, UserCodeID2,
            CreateDateTime3, UpdateDateTime3, OrderPaymentCodeID3, OrderCodeID3, UserCodeID3,
            CreateDateTime4, UpdateDateTime4, OrderPaymentCodeID4, OrderCodeID4, UserCodeID4,
            CreateDateTime5, UpdateDateTime5, OrderPaymentCodeID5, OrderCodeID5, UserCodeID5;
    private Button PaymentManagementButton, OrderPaymentListButton, FirstPage, PreviousPage, NextPage, LastPage;
    private TableLayout item1, item2, item3, item4, item5;
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
        item1 = binding.item1;

        CreateDateTime2 = binding.CreateDateTime2;
        UpdateDateTime2 = binding.UpdateDateTime2;
        OrderPaymentCodeID2 = binding.OrderPaymentCodeID2;
        OrderCodeID2 = binding.OrderCodeID2;
        UserCodeID2 = binding.UserCodeID2;
        item2 = binding.item2;

        CreateDateTime3 = binding.CreateDateTime3;
        UpdateDateTime3 = binding.UpdateDateTime3;
        OrderPaymentCodeID3 = binding.OrderPaymentCodeID3;
        OrderCodeID3 = binding.OrderCodeID3;
        UserCodeID3 = binding.UserCodeID3;
        item3 = binding.item3;

        CreateDateTime4 = binding.CreateDateTime4;
        UpdateDateTime4 = binding.UpdateDateTime4;
        OrderPaymentCodeID4 = binding.OrderPaymentCodeID4;
        OrderCodeID4 = binding.OrderCodeID4;
        UserCodeID4 = binding.UserCodeID4;
        item4 = binding.item4;

        CreateDateTime5 = binding.CreateDateTime5;
        UpdateDateTime5 = binding.UpdateDateTime5;
        OrderPaymentCodeID5 = binding.OrderPaymentCodeID5;
        OrderCodeID5 = binding.OrderCodeID5;
        UserCodeID5 = binding.UserCodeID5;
        item5 = binding.item5;


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
                getJsonArrayData();
            }
        });
        //点击三级功能，读取jsonArray中的数据并显示在组件上
        OrderPaymentListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setArrayData(jsonArray);
            }
        });
        //点击item弹出详细信息菜单
        item1.setOnClickListener(new itemClick());
        item2.setOnClickListener(new itemClick());
        item3.setOnClickListener(new itemClick());
        item4.setOnClickListener(new itemClick());
        item5.setOnClickListener(new itemClick());

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
    //获取表中的最新数据
    void getJsonArrayData(){
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
    //修改表中的数据
    void setJsonObjectData(String jsonObjectstring){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    modifyData.modifyData("MFJDingKuan", jsonObjectstring);//按ID查询，将修改的信息传入
                    //是不是因为警告所以此方法没有执行完毕？？
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //向界面view中写入五条数据
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
    //单击item事件响应
    class itemClick implements View.OnClickListener {
        EditText OrderPaymentDateTime, OrderPaymentAmount, OrderPaymentRemark, OrderPaymentOrder;
        TextView CreateBy, CreateDateTime, UpdateBy, UpdateDateTime, OrderPaymentCodeID, OrderCodeID, UserCodeID;
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(public_FinancialManagementActivity.this);
            final AlertDialog dialog = builder.create();
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.setTitle("详细信息");
            View dialogView = View.inflate(public_FinancialManagementActivity.this, R.layout.item_detail, null);
            dialog.setView(dialogView);
            CreateBy = dialogView.findViewById(R.id.CreateBy);
            CreateDateTime = dialogView.findViewById(R.id.CreateDateTime);
            UpdateBy = dialogView.findViewById(R.id.UpdateBy);
            UpdateDateTime = dialogView.findViewById(R.id.UpdateDateTime);
            OrderPaymentCodeID = dialogView.findViewById(R.id.OrderPaymentCodeID);
            OrderCodeID = dialogView.findViewById(R.id.OrderCodeID);
            UserCodeID = dialogView.findViewById(R.id.UserCodeID);
            OrderPaymentDateTime = dialogView.findViewById(R.id.OrderPaymentDateTime);
            OrderPaymentAmount = dialogView.findViewById(R.id.OrderPaymentAmount);
            OrderPaymentRemark = dialogView.findViewById(R.id.OrderPaymentRemark);
            OrderPaymentOrder = dialogView.findViewById(R.id.OrderPaymentOrder);

            final Button modify_info = dialogView.findViewById(R.id.modify_info);
            final Button del_info = dialogView.findViewById(R.id.del_info);
            final Button close_item = dialogView.findViewById(R.id.close_item);
            //修改方法实现
            modify_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        modifyArrayData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    switch (view.getId()){
//                        case R.id.item1:
//                            try {
//                                modifyArrayData(jsonArray, 0);//currentPage*5-5,
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        case R.id.item2:
//                            try {
//                                modifyArrayData(jsonArray,1);//currentPage*5-4,
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        case R.id.item3:
//                            try {
//                                modifyArrayData(jsonArray,2);//currentPage*5-3,
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        case R.id.item4:
//                            try {
//                                modifyArrayData(jsonArray,3);//currentPage*5-2,
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        case R.id.item5:
//                            try {
//                                modifyArrayData(jsonArray,4);//currentPage*5-1,
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                    }
                    dialog.dismiss();
                    getJsonArrayData();
                    //按理说modify之后再get，就已经将jsonArray赋值为表中的最新数据了，但是为啥没变？
                    System.out.println(jsonArray);
                    setArrayData(jsonArray);
                }
            });
            //关闭item详细界面
            close_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            // 这里的i后面要替换
            switch (view.getId()){
                case R.id.item1:
                    //再点开的时候这里却已经更新了
                    System.out.println(jsonArray);
                    setData(jsonArray,0);//currentPage*5-5,
                case R.id.item2:
                    setData(jsonArray,1);//currentPage*5-4,
                case R.id.item3:
                    setData(jsonArray,2);//currentPage*5-3,
                case R.id.item4:
                    setData(jsonArray,3);//currentPage*5-2,
                case R.id.item5:
                    setData(jsonArray,4);//currentPage*5-1,
            }
            dialog.show();

        }
        //显示详细数据的方法
        void setData(JSONArray jdata, int i){
            try {
                String CreateByString, CreateDateTimeString, UpdateByString, UpdateDateTimeString,
                        OrderPaymentCodeIDString, OrderCodeIDString, UserCodeIDString, OrderPaymentDateTimeString,
                        OrderPaymentAmountString, OrderPaymentRemarkString, OrderPaymentOrderString;
                //寻访json中对应item的所有键值对
                JSONObject SubjsonObject = jdata.getJSONObject(i);
                CreateByString = SubjsonObject.getString("createBy");
                CreateDateTimeString = SubjsonObject.getString("createDateTime");
                UpdateByString = SubjsonObject.getString("updateBy");
                UpdateDateTimeString = SubjsonObject.getString("updateDateTime");
                OrderPaymentCodeIDString = SubjsonObject.getString("ID");
                OrderCodeIDString = SubjsonObject.getString("MFJOrderID");
                UserCodeIDString = SubjsonObject.getString("UserID");
                OrderPaymentDateTimeString = SubjsonObject.getString("MFJDingKuanDate");
                OrderPaymentAmountString = SubjsonObject.getString("MFJDingKuanNum");
                OrderPaymentRemarkString = SubjsonObject.getString("MFJDingKuanDes");
                OrderPaymentOrderString = SubjsonObject.getString("MFJDingKuanOrder");

                CreateBy.setText(CreateByString);
                CreateDateTime.setText(CreateDateTimeString);
                UpdateBy.setText(UpdateByString);
                UpdateDateTime.setText(UpdateDateTimeString);
                OrderPaymentCodeID.setText(OrderPaymentCodeIDString);
                OrderCodeID.setText(OrderCodeIDString);
                UserCodeID.setText(UserCodeIDString);
                OrderPaymentDateTime.setText(OrderPaymentDateTimeString);
                OrderPaymentAmount.setText(OrderPaymentAmountString);
                OrderPaymentRemark.setText(OrderPaymentRemarkString);
                OrderPaymentOrder.setText(OrderPaymentOrderString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 用户修改数据的响应方法
        void modifyArrayData() throws JSONException {
            try {
                String  OrderPaymentDateTimeString, OrderPaymentAmountString, OrderPaymentRemarkString, OrderPaymentOrderString;
                //此表中只有四个字段可修改
                //修改后的字段
                OrderPaymentDateTimeString = OrderPaymentDateTime.getText().toString();
                OrderPaymentAmountString = OrderPaymentAmount.getText().toString();
                OrderPaymentRemarkString = OrderPaymentRemark.getText().toString();
                OrderPaymentOrderString = OrderPaymentOrder.getText().toString();
                //将无法修改和可修改的字段赋值到JSONObject中
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ID",OrderPaymentCodeID.getText().toString()).put("MFJDingKuanDate",OrderPaymentDateTimeString)
                        .put("MFJDingKuanNum",OrderPaymentAmountString).put("MFJDingKuanDes",OrderPaymentRemarkString)
                        .put("MFJDingKuanOrder",OrderPaymentOrderString);
                //jsonObject转String
                String jsonObjectstring = String.valueOf(jsonObject);
                //调用修改方法
                setJsonObjectData(jsonObjectstring);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}