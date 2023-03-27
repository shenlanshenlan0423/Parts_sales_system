package com.example.parts_sales_system.ui.public_use_management;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import com.example.parts_sales_system.R;
import com.example.parts_sales_system.data.api_connection.delData;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.public_UseManagementActivity;
import com.example.parts_sales_system.public_UseManagement_UseAlertAddData_AlertDialog;
import com.example.parts_sales_system.public_UseManagement_UseAlertSetData_AlertDialog;
import com.example.parts_sales_system.ui.top_nav_fragment_invent.Model_check;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UseAlert extends Fragment {

    public com.getbase.floatingactionbutton.FloatingActionButton add,del,manage;
    Boolean mflag=false;
    //外键的数组名要改
    private String[] MFJUseIDStringArray;
    public boolean mIsFromItem = false;
    ListView listView;
    CheckBox mMainCkb;
    cbx_Adapter_UseAlertList cbxAdapter;
    private List<Model_check> models;
    List<HashMap<String, Object>> data;
    List<String> ID;
    //实例化的对象要改
    public UseAlert(){}
    public void setFlag(Boolean flag){
        this.mflag=flag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        getFKData();
        //布局文件要改
        View view=inflater.inflate(R.layout.activity_public_use_management_install_management_use_alert,container,false);
        add=view.findViewById(R.id.add);
        add.setOnClickListener(new UseAlert.Add());
        del=view.findViewById(R.id.del);
        del.setOnClickListener(new UseAlert.Del());
        manage=view.findViewById(R.id.manage);
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转的Activity要改
                Intent intent=new Intent(getActivity(), public_UseManagementActivity.class);
                intent.putExtra("flag_usealert",mflag);
                intent.putExtra("page",3);
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
            Intent intent=new Intent(getActivity(), public_UseManagement_UseAlertAddData_AlertDialog.class);
            Bundle bundle=new Bundle();
            //外键的数组名要改
            bundle.putSerializable("array",MFJUseIDStringArray);
            intent.putExtra("page",3);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private class Del implements View.OnClickListener{
        @Override
        public void onClick(View view){
            for (int i = 0; i< cbx_Adapter_UseAlertList.index.size(); i++){
                String id = ID.get(Integer.parseInt((String) cbxAdapter.index.get(i)));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //访问的数据库表名要改
                            delData.delData("MFJUseYuJing", "{\"ID\":\"" + id + "\"}");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
            //跳转的AddActivity要改
            Intent intent=new Intent(getActivity(),public_UseManagementActivity.class);
            intent.putExtra("page",3);
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
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.public_usemanagement_useralertlist_item,
                            new String[]{"itemNumber","CreateBy","CreateDateTime","UpdateBy","UpdateDateTime","MFJUseYuJingCodeID"}, new int[]{R.id.itemNumber,R.id.creator,R.id.creatTime,R.id.updater,R.id.updateTime,R.id.MFJUseYuJingCodeID});
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new ItemClickListener());
                }
                class ItemClickListener implements AdapterView.OnItemClickListener {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ListView listView = (ListView) parent;
                        HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                        //跳转的SetActivity要改
                        Intent intent=new Intent(getActivity(), public_UseManagement_UseAlertSetData_AlertDialog.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("data",data);
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
                        JSONArray jsonArray= getData.getData("MFJUseYuJing","");
                        data = new ArrayList<HashMap<String,Object>>();
                        for (int i=0;i<jsonArray.length();i++){
                            HashMap<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                            item.put("itemNumber",(i+1));
                            item.put("CreateBy",jsonObject.getString("createBy"));
                            item.put("CreateDateTime",jsonObject.getString("createDateTime"));
                            item.put("UpdateBy",jsonObject.getString("updateBy"));
                            item.put("UpdateDateTime",jsonObject.getString("updateDateTime"));
                            item.put("MFJUseYuJingCodeID",jsonObject.getString("ID"));
                            item.put("MFJUseID",jsonObject.getString("MFJUseID"));
                            item.put("MFJUseYuJingDate",jsonObject.getString("MFJUseYuJingDate"));
                            item.put("MFJUseYuJingStatus",jsonObject.getString("MFJUseYuJingStatus"));
                            item.put("MFJUseYuJingDes",jsonObject.getString("MFJUseYuJingDes"));
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
            del.setEnabled(true);
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
                        JSONArray jsonArray= getData.getData("MFJUseYuJing","");
                        data = new ArrayList<HashMap<String,Object>>();
                        for (int i=0;i<jsonArray.length();i++){
                            HashMap<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                            item.put("itemNumber",(i+1));
                            item.put("CreateBy",jsonObject.getString("createBy"));
                            item.put("CreateDateTime",jsonObject.getString("createDateTime"));
                            item.put("UpdateBy",jsonObject.getString("updateBy"));
                            item.put("UpdateDateTime",jsonObject.getString("updateDateTime"));
                            item.put("MFJUseYuJingCodeID",jsonObject.getString("ID"));
                            item.put("MFJUseID",jsonObject.getString("MFJUseID"));
                            item.put("MFJUseYuJingDate",jsonObject.getString("MFJUseYuJingDate"));
                            item.put("MFJUseYuJingStatus",jsonObject.getString("MFJUseYuJingStatus"));
                            item.put("MFJUseYuJingDes",jsonObject.getString("MFJUseYuJingDes"));
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
            //这里的InstaCodeID也要改成表中的主键
            ID.add((String) data.get(i).get("MFJUseYuJingCodeID"));
        }
    }
    private void initViewOper(List<HashMap<String, Object>> data) {
        cbxAdapter = new cbx_Adapter_UseAlertList(data,models, getActivity(), new AllCheckListener() {
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
                    Log.e("mainCheckBox", "此时我不可以触发");
                    return;
                }
                //改变数据
                for (Model_check model : models) {
                    model.setIscheck(b);
                }
                cbx_Adapter_UseAlertList.index=new ArrayList<>();
                for (Model_check model: models) {
                    if (model.ischeck()) {
                        cbx_Adapter_UseAlertList.index.add(model.getSt());
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
        //需要访问多个外键所在表，请使用多个子线程，不然只会完成第一个外键的访问和赋值
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    //访问的数据库表名和字段要改
                    //外键需要访问几个表就生成几个StringArray
                    JSONArray jdataUseID = getData.getData("MFJUseYuJing","");
                    int jsonArrayUseIDlength = jdataUseID.length();
                    //字符串数组,用于存储目标字段的全部可取值
                    //先加1是为了写进固定的测试例子
                    String[] MFJUseIDString = new String[jsonArrayUseIDlength+1];
                    for (int i=0;i<jsonArrayUseIDlength;i++){
                        JSONObject SubjsonObject = jdataUseID.getJSONObject(i);
                        MFJUseIDString [i] = SubjsonObject.getString("ID");
                    }
                    //固定的测试例子
                    MFJUseIDString[jsonArrayUseIDlength] = "20230306110908984";
                    MFJUseIDStringArray = MFJUseIDString;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}