package com.example.parts_sales_system.ui.financial_management;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parts_sales_system.R;
import com.example.parts_sales_system.data.api_connection.delData;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.public_FinancialManagementActivity;
import com.example.parts_sales_system.public_FinancialManagement_PaymentManagement_OrderList_AddData;
import com.example.parts_sales_system.public_FinancialManagement_PaymentManagement_OrderList_SetData;
import com.example.parts_sales_system.ui.use_management.Model_check;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PaymentManagement extends Fragment {
    public com.getbase.floatingactionbutton.FloatingActionButton add,del,manage;
    Boolean mflag=false;
    //外键的数组名要改
    private String[] OrderCodeIDStringArray,UserCodeIDStringArray;
    public boolean mIsFromItem = false;
    ListView listView;
    CheckBox mMainCkb;
    cbx_Adapter cbxAdapter;
    private List<Model_check> models;
    List<HashMap<String, Object>> data;
    List<String> ID;
    //实例化的对象要改
    public PaymentManagement(){}
    public void setFlag(Boolean flag){
        this.mflag=flag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        getFKData();
        //布局文件要改
        View view=inflater.inflate(R.layout.activity_public_financial_management_payment_management,container,false);
        add=view.findViewById(R.id.add);
        add.setOnClickListener(new Add());
        del=view.findViewById(R.id.del);
        del.setOnClickListener(new Del());
        if (mflag){
            del.setEnabled(true);
        }
        manage=view.findViewById(R.id.manage);
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转的Activity要改
                Intent intent=new Intent(getActivity(), public_FinancialManagementActivity.class);
                intent.putExtra("flag",mflag);
                intent.putExtra("page",0);
                startActivity(intent);
            }
        });
        if (mflag==null){mflag=false;}
        initList(mflag,view);
        return view;
    }
    private class Add implements View.OnClickListener{
        @Override
        public void onClick(View view){
            //跳转的AddActivity要改
            Intent intent=new Intent(getActivity(), public_FinancialManagement_PaymentManagement_OrderList_AddData.class);
            Bundle bundle=new Bundle();
            //外键的数组名要改
            bundle.putSerializable("array1",OrderCodeIDStringArray);
            bundle.putSerializable("array2",UserCodeIDStringArray);
            intent.putExtra("page",0);
            intent.putExtras(bundle);
//            System.out.println(intent.getSerializableExtra("array1"));
//            System.out.println(intent.getSerializableExtra("array2"));
            startActivity(intent);
        }
    }

    private class Del implements View.OnClickListener{
        @Override
        public void onClick(View view){
            for (int i = 0; i< cbx_Adapter.index.size(); i++){
                String id = ID.get(Integer.parseInt((String) cbx_Adapter.index.get(i)));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //访问的数据库表名要改
                            delData.delData("MFJDingKuan", "{\"ID\":\"" + id + "\"}");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
            //跳转的AddActivity要改
            Intent intent=new Intent(getActivity(), public_FinancialManagementActivity.class);
            intent.putExtra("page",0);
            startActivity(intent);
        }
    }

    public void initList(boolean flag,View view){
        if (!flag){//管理按钮没有按下的初始化列表
            listView=view.findViewById(R.id.listView);
            CheckBox checkAllBox=view.findViewById(R.id.checkAllBox);
            checkAllBox.setVisibility(View.INVISIBLE);
            del.setEnabled(false);
            Handler mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:{
                            data=(List<HashMap<String, Object>>)msg.obj;
                        }
                    }
                    //最后一个字段名和对应的布局对象要改
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.public_financial_management_orderpaymentlist_item,
                            new String[]{"itemNumber","CreateBy","CreateDateTime","UpdateBy","UpdateDateTime","OrderPaymentCodeID"}, new int[]{R.id.itemNumber,R.id.creator,R.id.creatTime,R.id.updater,R.id.updateTime,R.id.OrderPaymentCodeID});
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new ItemClickListener());
                }
                class ItemClickListener implements AdapterView.OnItemClickListener {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ListView listView = (ListView) parent;
                        HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                        //跳转的SetActivity要改
                        Intent intent=new Intent(getActivity(), public_FinancialManagement_PaymentManagement_OrderList_SetData.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("data",data);
                        //如果访问了多个外键表，生成了多个外键可选值字符数组，这里就分成array1,array2,...
                        bundle.putSerializable("array1",OrderCodeIDStringArray);
                        bundle.putSerializable("array2",UserCodeIDStringArray);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            };

            new Thread(new Runnable(){
                @Override
                public void run() {
                    try{
                        //访问的数据库表名和字段要改
                        JSONArray jsonArray= getData.getData("MFJDingKuan","");
                        data = new ArrayList<HashMap<String,Object>>();
                        for (int i=0;i<jsonArray.length();i++){
                            HashMap<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                            item.put("itemNumber",(i+1));
                            item.put("CreateBy",jsonObject.getString("createBy"));
                            item.put("CreateDateTime",jsonObject.getString("createDateTime"));
                            item.put("UpdateBy",jsonObject.getString("updateBy"));
                            item.put("UpdateDateTime",jsonObject.getString("updateDateTime"));
                            item.put("OrderPaymentCodeID",jsonObject.getString("ID"));
                            item.put("MFJOrderID",jsonObject.getString("MFJOrderID"));
                            item.put("UserID",jsonObject.getString("UserID"));
                            item.put("MFJDingKuanDate",jsonObject.getString("MFJDingKuanDate"));
                            item.put("MFJDingKuanNum",jsonObject.getString("MFJDingKuanNum"));
                            item.put("MFJDingKuanDes",jsonObject.getString("MFJDingKuanDes"));
                            item.put("MFJDingKuanOrder",jsonObject.getString("MFJDingKuanOrder"));
                            data.add(item);
                        }
                        Message msg=new Message();
                        msg.obj=data;
                        msg.what=1;
                        mHandler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        else{//管理按钮按下的初始化列表
            listView=view.findViewById(R.id.listView);
            mMainCkb = (CheckBox) view.findViewById(R.id.checkAllBox);
            mMainCkb.setVisibility(View.VISIBLE);
            Handler mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:{
                            data=(List<HashMap<String, Object>>)msg.obj;
                        }
                    }
                    initData(data);
                    initViewOper(data);
                }
            };

            new Thread(new Runnable(){
                @Override
                public void run() {
                    try{
                        //访问的数据库表名和字段要改
                        JSONArray jsonArray= getData.getData("MFJDingKuan","");
                        data = new ArrayList<HashMap<String,Object>>();
                        for (int i=0;i<jsonArray.length();i++){
                            HashMap<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                            item.put("itemNumber",(i+1));
                            item.put("CreateBy",jsonObject.getString("createBy"));
                            item.put("CreateDateTime",jsonObject.getString("createDateTime"));
                            item.put("UpdateBy",jsonObject.getString("updateBy"));
                            item.put("UpdateDateTime",jsonObject.getString("updateDateTime"));
                            item.put("OrderPaymentCodeID",jsonObject.getString("ID"));
                            item.put("MFJOrderID",jsonObject.getString("MFJOrderID"));
                            item.put("UserID",jsonObject.getString("UserID"));
                            item.put("MFJDingKuanDate",jsonObject.getString("MFJDingKuanDate"));
                            item.put("MFJDingKuanNum",jsonObject.getString("MFJDingKuanNum"));
                            item.put("MFJDingKuanDes",jsonObject.getString("MFJDingKuanDes"));
                            item.put("MFJDingKuanOrder",jsonObject.getString("MFJDingKuanOrder"));
                            data.add(item);
                        }
                        Message msg=new Message();
                        msg.obj=data;
                        msg.what=1;
                        mHandler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    private void initData(List<HashMap<String, Object>> data) {
        models = new ArrayList<>();
        ID=new ArrayList<>();
        Model_check model;
        for (int i = 0; i < data.size(); i++) {
            model = new Model_check();
            model.setSt(String.valueOf(i));
            model.setIscheck(false);
            models.add(model);
            //这里的PatrolRecordCodeID也要改成表中的主键
            ID.add((String) data.get(i).get("OrderPaymentCodeID"));
        }
    }
    private void initViewOper(List<HashMap<String, Object>> data) {
        cbxAdapter = new cbx_Adapter(data,models, getActivity(), new AllCheckListener() {
            @Override
            public void onCheckedChanged(boolean b) {
                if (!b && !mMainCkb.isChecked()) {
                    return;
                } else if (!b && mMainCkb.isChecked()) {
                    mIsFromItem = true;
                    mMainCkb.setChecked(false);
                } else if (b) {
                    mIsFromItem = true;
                    mMainCkb.setChecked(true);
                }
            }
        });
        listView.setAdapter(cbxAdapter);
        mMainCkb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //当监听来源为点击item改变maincbk状态时不在监听改变，防止死循环
                if (mIsFromItem) {
                    mIsFromItem = false;
//                    Log.e("mainCheckBox", "此时我不可以触发");
                    return;
                }
                //改变数据
                for (Model_check model : models) {
                    model.setIscheck(b);
                }
                cbx_Adapter.index=new ArrayList<>();
                for (Model_check model: models) {
                    if (model.ischeck()) {
                        cbx_Adapter.index.add(model.getSt());
                    }
                    else {
                        continue;
                    }
                }
                //刷新listview
                cbxAdapter.notifyDataSetChanged();
            }
        });
    }
    interface AllCheckListener {
        void onCheckedChanged(boolean b);
    }
    //从外键所在表中获取外键可取值的最新数据
    void getFKData(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    //访问的数据库表名和字段要改
                    //外键需要访问几个表就生成几个StringArray
                    JSONArray jdataOrderCodeID1 = getData.getData("MFJOrder","");
                    //字符串数组,用于存储目标字段的全部可取值
                    //先加1是为了写进固定的测试例子
                    String[] OrderCodeIDString = new String[jdataOrderCodeID1.length()+1];
                    for (int i=0;i<jdataOrderCodeID1.length();i++){
                        JSONObject SubjsonObject = jdataOrderCodeID1.getJSONObject(i);
                        OrderCodeIDString[i] = SubjsonObject.getString("ID");
                    }
                    //固定的测试例子
                    OrderCodeIDString[jdataOrderCodeID1.length()] = "2023030609293529357";
                    OrderCodeIDStringArray = OrderCodeIDString;

                    JSONArray jdataUserCodeID2 = getData.getData("User","");
                    String[] UserCodeIDString = new String[jdataUserCodeID2.length()+1];
                    for (int i=0;i<jdataUserCodeID2.length();i++){
                        JSONObject SubjsonObject = jdataUserCodeID2.getJSONObject(i);
                        UserCodeIDString[i] = SubjsonObject.getString("ID");
                    }
                    //固定的测试例子
                    UserCodeIDString[jdataUserCodeID2.length()] = "1";
                    UserCodeIDStringArray = UserCodeIDString;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
