package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parts_sales_system.data.api_connection.addData;
import com.example.parts_sales_system.data.api_connection.delData;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.data.api_connection.modifyData;
import com.example.parts_sales_system.databinding.ActivityPublicFinancialManagementBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class public_FinancialManagementActivity extends AppCompatActivity {
    private ActivityPublicFinancialManagementBinding binding;
    private TextView Pages,
            CreateDateTime1, UpdateDateTime1, OrderPaymentCodeID1, OrderCodeID1, UserCodeID1,
            CreateDateTime2, UpdateDateTime2, OrderPaymentCodeID2, OrderCodeID2, UserCodeID2,
            CreateDateTime3, UpdateDateTime3, OrderPaymentCodeID3, OrderCodeID3, UserCodeID3,
            CreateDateTime4, UpdateDateTime4, OrderPaymentCodeID4, OrderCodeID4, UserCodeID4,
            CreateDateTime5, UpdateDateTime5, OrderPaymentCodeID5, OrderCodeID5, UserCodeID5;
    private Button PaymentManagementButton, OrderPaymentListButton, FirstPage, PreviousPage, NextPage, LastPage;
    private FloatingActionButton itemaddButton;
    private TableLayout item1, item2, item3, item4, item5;
    private JSONArray jsonArray;
    private String[] OrderIDStringArray;
    int TotalPage=1, currentPage=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Pages = binding.Pages;
        OrderPaymentListButton = binding.OrderPaymentListButton;
        PaymentManagementButton = binding.PaymentManagementButton;
        itemaddButton = binding.itemAddFloatButton;
        //点击三级列表后才可以点击新增按钮
        itemaddButton.setEnabled(false);
        //翻页按钮
        FirstPage = binding.FirstPage;
        PreviousPage = binding.PreviousPage;
        NextPage = binding.NextPage;
        LastPage = binding.LastPage;
        //点击二级功能,get表中的全部数据,并赋值给jsonArray
        PaymentManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentManagementButton.setBackgroundColor(Color.parseColor("#0033FF"));
                getJsonArrayData();
            }
        });
        //点击三级功能，读取jsonArray中的数据并显示在组件上
        OrderPaymentListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderPaymentListButton.setBackgroundColor(Color.parseColor("#0033FF"));
                setArrayData(jsonArray,currentPage);
                setPages();
                itemaddButton.setEnabled(true);
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
                if (currentPage==1){
                    Toast.makeText(public_FinancialManagementActivity.this, "已经是首页了~", Toast.LENGTH_SHORT).show();
                }else{
                    currentPage = 1;
                    setArrayData(jsonArray,currentPage);
                    setPages();
                }
            }
        });
        PreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage>1){
                    currentPage-=1;
                    setArrayData(jsonArray,currentPage);
                    setPages();
                }else{
                    Toast.makeText(public_FinancialManagementActivity.this, "已经是首页了~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        NextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage<TotalPage){
                    currentPage+=1;
                    setArrayData(jsonArray,currentPage);
                    setPages();
                }else{
                    Toast.makeText(public_FinancialManagementActivity.this, "已经是尾页了~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        LastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage==TotalPage){
                    Toast.makeText(public_FinancialManagementActivity.this, "已经是尾页了~", Toast.LENGTH_SHORT).show();
                }else{
                    currentPage = TotalPage;
                    setArrayData(jsonArray,currentPage);
                    setPages();
                }
            }
        });

        //悬浮按钮，用于用户新增item
        itemaddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Spinner OrderCodeID;
                final TextView UserCodeID;
                final EditText OrderPaymentDateTime, OrderPaymentAmount, OrderPaymentRemark, OrderPaymentOrder;
                final Button confirm_button, cancel_button;
                AlertDialog.Builder builder = new AlertDialog.Builder(public_FinancialManagementActivity.this);
                final AlertDialog dialog = builder.create();
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setTitle("新增项目");
                View dialogView = View.inflate(public_FinancialManagementActivity.this, R.layout.orderpaymentlist_item_add, null);
                dialog.setView(dialogView);
                OrderCodeID = (Spinner)dialogView.findViewById(R.id.OrderCodeID);
                UserCodeID = dialogView.findViewById(R.id.UserCodeID);
                OrderPaymentDateTime = dialogView.findViewById(R.id.OrderPaymentDateTime);
                OrderPaymentAmount = dialogView.findViewById(R.id.OrderPaymentAmount);
                OrderPaymentRemark = dialogView.findViewById(R.id.OrderPaymentRemark);
                OrderPaymentOrder = dialogView.findViewById(R.id.OrderPaymentOrder);
                confirm_button = dialogView.findViewById(R.id.confirm_button);
                cancel_button = dialogView.findViewById(R.id.cancel_button);

                //下拉列表的数组适配器
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(public_FinancialManagementActivity.this, R.layout.order_id_list, OrderIDStringArray);
                OrderCodeID.setAdapter(adapter); // 设置下拉框的数组适配器
                OrderCodeID.setSelection(OrderIDStringArray.length-1); // 设置下拉框默认显示最后一项的测试例子

                //用户ID后面用Intent传值获取
                UserCodeID.setText("1");

                //确认按钮响应
                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //将无法修改和可修改的字段赋值到JSONObject中
                        JSONObject jsonObject = new JSONObject();
                        //前两个外键MFJOrderID、UserID先固定，后面再和当前登录的用户、选中的密封件编号绑定
                        try {
                            jsonObject.put("ID","").put("MFJOrderID",OrderCodeID.getSelectedItem().toString()).put("UserID",UserCodeID.getText().toString())
                                    .put("MFJDingKuanDate",OrderPaymentDateTime.getText().toString())
                                    .put("MFJDingKuanNum",OrderPaymentAmount.getText().toString()).put("MFJDingKuanDes",OrderPaymentRemark.getText().toString())
                                    .put("MFJDingKuanOrder",OrderPaymentOrder.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //jsonObject转String
                        String jsonObjectstring = String.valueOf(jsonObject);
                        //调用新增方法
                        addJsonArrayData(jsonObjectstring);
                        dialog.dismiss();
                        PaymentManagementButton.setBackgroundColor(Color.parseColor("#0099FF"));
                        OrderPaymentListButton.setBackgroundColor(Color.parseColor("#0099FF"));
                    }
                });
                //取消按钮响应
                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    //获取表中的最新数据
    void getJsonArrayData(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    jsonArray = getData.getData("MFJDingKuan","");
                    JSONArray jdataOrderID = getData.getData("MFJOrder","");
                    int jsonArrayOrderIDlength = jdataOrderID.length();
                    //字符串数组,用于存储目标字段的全部可取值
                    //先加1是为了写进固定的测试例子
                    String[] OrderIDString = new String[jsonArrayOrderIDlength+1];
                    for (int i=0;i<jsonArrayOrderIDlength;i++){
                        JSONObject SubjsonObject = jdataOrderID.getJSONObject(i);
                        OrderIDString[i] = SubjsonObject.getString("ID");
                    }
                    //固定的测试例子
                    OrderIDString[jsonArrayOrderIDlength] = "2023030609293529357";
                    OrderIDStringArray = OrderIDString;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
    //删除表中的数据
    void delJsonObjectData(String jsonObjectstring){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    delData.delData("MFJDingKuan", jsonObjectstring);//按ID查询并删除
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //向界面view中写入五条数据
    void setArrayData(JSONArray jdata,int curPage){
        try {
            //寻访json中包含的所有键值对
            int jdata_length = jdata.length();
            if (jdata_length % 5 == 0){
                TotalPage = jdata_length / 5;
            }else{
                TotalPage = jdata_length / 5 + 1;
            }
            for (int i=(curPage-1)*5; i<curPage*5&i<jdata_length; i++){
                String CreateDateTimeString, UpdateDateTimeString, OrderPaymentCodeIDString, OrderCodeIDString, UserCodeIDString;
                JSONObject SubjsonObject = jdata.getJSONObject(i);
                CreateDateTimeString = SubjsonObject.getString("createDateTime");
                UpdateDateTimeString = SubjsonObject.getString("updateDateTime");
                OrderPaymentCodeIDString = SubjsonObject.getString("ID");
                OrderCodeIDString = SubjsonObject.getString("MFJOrderID");
                UserCodeIDString = SubjsonObject.getString("UserID");
                if (i % 5 == 0){
                    // 赋值语句
                    CreateDateTime1.setText(CreateDateTimeString);
                    UpdateDateTime1.setText(UpdateDateTimeString);
                    OrderPaymentCodeID1.setText(OrderPaymentCodeIDString);
                    OrderCodeID1.setText(OrderCodeIDString);
                    UserCodeID1.setText(UserCodeIDString);
                    CreateDateTime2.setText("");
                    UpdateDateTime2.setText("");
                    OrderPaymentCodeID2.setText("");
                    OrderCodeID2.setText("");
                    UserCodeID2.setText("");
                    CreateDateTime3.setText("");
                    UpdateDateTime3.setText("");
                    OrderPaymentCodeID3.setText("");
                    OrderCodeID3.setText("");
                    UserCodeID3.setText("");
                    CreateDateTime4.setText("");
                    UpdateDateTime4.setText("");
                    OrderPaymentCodeID4.setText("");
                    OrderCodeID4.setText("");
                    UserCodeID4.setText("");
                    CreateDateTime5.setText("");
                    UpdateDateTime5.setText("");
                    OrderPaymentCodeID5.setText("");
                    OrderCodeID5.setText("");
                    UserCodeID5.setText("");
                }else if(i % 5 == 1){
                    CreateDateTime2.setText(CreateDateTimeString);
                    UpdateDateTime2.setText(UpdateDateTimeString);
                    OrderPaymentCodeID2.setText(OrderPaymentCodeIDString);
                    OrderCodeID2.setText(OrderCodeIDString);
                    UserCodeID2.setText(UserCodeIDString);
                    CreateDateTime3.setText("");
                    UpdateDateTime3.setText("");
                    OrderPaymentCodeID3.setText("");
                    OrderCodeID3.setText("");
                    UserCodeID3.setText("");
                    CreateDateTime4.setText("");
                    UpdateDateTime4.setText("");
                    OrderPaymentCodeID4.setText("");
                    OrderCodeID4.setText("");
                    UserCodeID4.setText("");
                    CreateDateTime5.setText("");
                    UpdateDateTime5.setText("");
                    OrderPaymentCodeID5.setText("");
                    OrderCodeID5.setText("");
                    UserCodeID5.setText("");
                }else if(i % 5 == 2){
                    CreateDateTime3.setText(CreateDateTimeString);
                    UpdateDateTime3.setText(UpdateDateTimeString);
                    OrderPaymentCodeID3.setText(OrderPaymentCodeIDString);
                    OrderCodeID3.setText(OrderCodeIDString);
                    UserCodeID3.setText(UserCodeIDString);
                    CreateDateTime4.setText("");
                    UpdateDateTime4.setText("");
                    OrderPaymentCodeID4.setText("");
                    OrderCodeID4.setText("");
                    UserCodeID4.setText("");
                    CreateDateTime5.setText("");
                    UpdateDateTime5.setText("");
                    OrderPaymentCodeID5.setText("");
                    OrderCodeID5.setText("");
                    UserCodeID5.setText("");
                }else if(i % 5 == 3){
                    CreateDateTime4.setText(CreateDateTimeString);
                    UpdateDateTime4.setText(UpdateDateTimeString);
                    OrderPaymentCodeID4.setText(OrderPaymentCodeIDString);
                    OrderCodeID4.setText(OrderCodeIDString);
                    UserCodeID4.setText(UserCodeIDString);
                    CreateDateTime5.setText("");
                    UpdateDateTime5.setText("");
                    OrderPaymentCodeID5.setText("");
                    OrderCodeID5.setText("");
                    UserCodeID5.setText("");
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
    //设置页面显示
    void setPages(){
        Pages.setText(currentPage+" / "+TotalPage);
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
            View dialogView = View.inflate(public_FinancialManagementActivity.this, R.layout.orderpaymentlist_item_detail, null);
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
            //修改item
            modify_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        modifyArrayData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    //这里还有一点问题，0308hhw，状态：未解决
                    getJsonArrayData();
                    //按理说modify之后再get，就已经将jsonArray赋值为表中的最新数据了，但是为啥没变？
//                    System.out.println(jsonArray);
                    setArrayData(jsonArray,currentPage);
                    PaymentManagementButton.setBackgroundColor(Color.parseColor("#0099FF"));
                    OrderPaymentListButton.setBackgroundColor(Color.parseColor("#0099FF"));
                }
            });
            //删除item
            del_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        deleteArrayData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    PaymentManagementButton.setBackgroundColor(Color.parseColor("#0099FF"));
                    OrderPaymentListButton.setBackgroundColor(Color.parseColor("#0099FF"));
                }
            });
            //关闭item
            close_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            // 这里的i后面要替换
            switch (view.getId()){
                case R.id.item1:
                    System.out.println(R.id.item1);
                    //再点开的时候这里却已经更新了
//                    System.out.println(jsonArray);
                    setData(jsonArray,currentPage*5-5);
                    break;
                case R.id.item2:
                    setData(jsonArray,currentPage*5-4);
                    break;
                case R.id.item3:
                    setData(jsonArray,currentPage*5-3);
                    break;
                case R.id.item4:
                    setData(jsonArray,currentPage*5-2);
                    break;
                case R.id.item5:
                    setData(jsonArray,currentPage*5-1);
                    break;
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
        //用户删除数据的方法
        void deleteArrayData() throws JSONException{
            try {
                //将ID的信息封装到Json对象中
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ID",OrderPaymentCodeID.getText().toString());
                //jsonObject转String
                String jsonObjectstring = String.valueOf(jsonObject);
                //调用删除方法
                delJsonObjectData(jsonObjectstring);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}