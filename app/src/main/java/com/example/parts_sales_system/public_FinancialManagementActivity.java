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
        //????????????????????????????????????????????????
        itemaddButton.setEnabled(false);
        //????????????
        FirstPage = binding.FirstPage;
        PreviousPage = binding.PreviousPage;
        NextPage = binding.NextPage;
        LastPage = binding.LastPage;
        //??????????????????,get?????????????????????,????????????jsonArray
        PaymentManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentManagementButton.setBackgroundColor(Color.parseColor("#0033FF"));
                getJsonArrayData();
            }
        });
        //???????????????????????????jsonArray?????????????????????????????????
        OrderPaymentListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderPaymentListButton.setBackgroundColor(Color.parseColor("#0033FF"));
                setArrayData(jsonArray,currentPage);
                setPages();
                itemaddButton.setEnabled(true);
            }
        });
        //??????item????????????????????????
        item1.setOnClickListener(new itemClick());
        item2.setOnClickListener(new itemClick());
        item3.setOnClickListener(new itemClick());
        item4.setOnClickListener(new itemClick());
        item5.setOnClickListener(new itemClick());

        //?????????????????????
        FirstPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage==1){
                    Toast.makeText(public_FinancialManagementActivity.this, "??????????????????~", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(public_FinancialManagementActivity.this, "??????????????????~", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(public_FinancialManagementActivity.this, "??????????????????~", Toast.LENGTH_SHORT).show();
                }
            }
        });
        LastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage==TotalPage){
                    Toast.makeText(public_FinancialManagementActivity.this, "??????????????????~", Toast.LENGTH_SHORT).show();
                }else{
                    currentPage = TotalPage;
                    setArrayData(jsonArray,currentPage);
                    setPages();
                }
            }
        });

        //?????????????????????????????????item
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
                dialog.setTitle("????????????");
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

                //??????????????????????????????
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(public_FinancialManagementActivity.this, R.layout.order_id_list, OrderIDStringArray);
                OrderCodeID.setAdapter(adapter); // ?????????????????????????????????
                OrderCodeID.setSelection(OrderIDStringArray.length-1); // ??????????????????????????????????????????????????????

                //??????ID?????????Intent????????????
                UserCodeID.setText("1");

                //??????????????????
                confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //?????????????????????????????????????????????JSONObject???
                        JSONObject jsonObject = new JSONObject();
                        //???????????????MFJOrderID???UserID??????????????????????????????????????????????????????????????????????????????
                        try {
                            jsonObject.put("ID","").put("MFJOrderID",OrderCodeID.getSelectedItem().toString()).put("UserID",UserCodeID.getText().toString())
                                    .put("MFJDingKuanDate",OrderPaymentDateTime.getText().toString())
                                    .put("MFJDingKuanNum",OrderPaymentAmount.getText().toString()).put("MFJDingKuanDes",OrderPaymentRemark.getText().toString())
                                    .put("MFJDingKuanOrder",OrderPaymentOrder.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //jsonObject???String
                        String jsonObjectstring = String.valueOf(jsonObject);
                        //??????????????????
                        addJsonArrayData(jsonObjectstring);
                        dialog.dismiss();
                        PaymentManagementButton.setBackgroundColor(Color.parseColor("#0099FF"));
                        OrderPaymentListButton.setBackgroundColor(Color.parseColor("#0099FF"));
                    }
                });
                //??????????????????
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
    //???????????????????????????
    void getJsonArrayData(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    jsonArray = getData.getData("MFJDingKuan","");
                    JSONArray jdataOrderID = getData.getData("MFJOrder","");
                    int jsonArrayOrderIDlength = jdataOrderID.length();
                    //???????????????,??????????????????????????????????????????
                    //??????1????????????????????????????????????
                    String[] OrderIDString = new String[jsonArrayOrderIDlength+1];
                    for (int i=0;i<jsonArrayOrderIDlength;i++){
                        JSONObject SubjsonObject = jdataOrderID.getJSONObject(i);
                        OrderIDString[i] = SubjsonObject.getString("ID");
                    }
                    //?????????????????????
                    OrderIDString[jsonArrayOrderIDlength] = "2023030609293529357";
                    OrderIDStringArray = OrderIDString;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //???????????????item
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
    //?????????????????????
    void setJsonObjectData(String jsonObjectstring){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    modifyData.modifyData("MFJDingKuan", jsonObjectstring);//???ID?????????????????????????????????
                    //????????????????????????????????????????????????????????????
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //?????????????????????
    void delJsonObjectData(String jsonObjectstring){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    delData.delData("MFJDingKuan", jsonObjectstring);//???ID???????????????
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //?????????view?????????????????????
    void setArrayData(JSONArray jdata,int curPage){
        try {
            //??????json???????????????????????????
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
                    // ????????????
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
    //??????????????????
    void setPages(){
        Pages.setText(currentPage+" / "+TotalPage);
    }
    //??????item????????????
    class itemClick implements View.OnClickListener {
        EditText OrderPaymentDateTime, OrderPaymentAmount, OrderPaymentRemark, OrderPaymentOrder;
        TextView CreateBy, CreateDateTime, UpdateBy, UpdateDateTime, OrderPaymentCodeID, OrderCodeID, UserCodeID;
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(public_FinancialManagementActivity.this);
            final AlertDialog dialog = builder.create();
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.setTitle("????????????");
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
            //??????item
            modify_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        modifyArrayData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    //???????????????????????????0308hhw?????????????????????
                    getJsonArrayData();
                    //?????????modify?????????get???????????????jsonArray?????????????????????????????????????????????????????????
//                    System.out.println(jsonArray);
                    setArrayData(jsonArray,currentPage);
                    PaymentManagementButton.setBackgroundColor(Color.parseColor("#0099FF"));
                    OrderPaymentListButton.setBackgroundColor(Color.parseColor("#0099FF"));
                }
            });
            //??????item
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
            //??????item
            close_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            // ?????????i???????????????
            switch (view.getId()){
                case R.id.item1:
                    System.out.println(R.id.item1);
                    //??????????????????????????????????????????
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
        //???????????????????????????
        void setData(JSONArray jdata, int i){
            try {
                String CreateByString, CreateDateTimeString, UpdateByString, UpdateDateTimeString,
                        OrderPaymentCodeIDString, OrderCodeIDString, UserCodeIDString, OrderPaymentDateTimeString,
                        OrderPaymentAmountString, OrderPaymentRemarkString, OrderPaymentOrderString;
                //??????json?????????item??????????????????
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
        // ?????????????????????????????????
        void modifyArrayData() throws JSONException {
            try {
                String  OrderPaymentDateTimeString, OrderPaymentAmountString, OrderPaymentRemarkString, OrderPaymentOrderString;
                //????????????????????????????????????
                //??????????????????
                OrderPaymentDateTimeString = OrderPaymentDateTime.getText().toString();
                OrderPaymentAmountString = OrderPaymentAmount.getText().toString();
                OrderPaymentRemarkString = OrderPaymentRemark.getText().toString();
                OrderPaymentOrderString = OrderPaymentOrder.getText().toString();
                //?????????????????????????????????????????????JSONObject???
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ID",OrderPaymentCodeID.getText().toString()).put("MFJDingKuanDate",OrderPaymentDateTimeString)
                        .put("MFJDingKuanNum",OrderPaymentAmountString).put("MFJDingKuanDes",OrderPaymentRemarkString)
                        .put("MFJDingKuanOrder",OrderPaymentOrderString);
                //jsonObject???String
                String jsonObjectstring = String.valueOf(jsonObject);
                //??????????????????
                setJsonObjectData(jsonObjectstring);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //???????????????????????????
        void deleteArrayData() throws JSONException{
            try {
                //???ID??????????????????Json?????????
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ID",OrderPaymentCodeID.getText().toString());
                //jsonObject???String
                String jsonObjectstring = String.valueOf(jsonObject);
                //??????????????????
                delJsonObjectData(jsonObjectstring);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}