package com.example.parts_sales_system.ui.basic_setting;

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
import com.example.parts_sales_system.public_BasicSettingActivity;
import com.example.parts_sales_system.public_BasicSetting_UserManagement_UserDeptList_AddData;
import com.example.parts_sales_system.public_BasicSetting_UserManagement_UserDeptList_SetData;
import com.example.parts_sales_system.ui.top_nav_fragment_invent.Model_check;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserDeptList extends Fragment {
    public com.getbase.floatingactionbutton.FloatingActionButton add,del,manage;
    Boolean mflag=false;
    //外键的数组名要改
    private String[] SuperiorUseDeptIDStringArray;
    public boolean mIsFromItem = false;
    ListView listView;
    CheckBox mMainCkb;
    cbx_Adapter_UserDeptList cbxAdapter;
    private List<Model_check> models;
    List<HashMap<String, Object>> data;
    List<String> ID;
    public UserDeptList(){}
    public void setFlag(Boolean flag){
        this.mflag=flag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        getFKData();
        View view=inflater.inflate(R.layout.activity_public_basic_setting_usermanagement_userdeptlist,container,false);
        add=view.findViewById(R.id.add);
        add.setOnClickListener(new UserDeptList.Add());
        del=view.findViewById(R.id.del);
        del.setOnClickListener(new UserDeptList.Del());
        if (mflag){
            del.setEnabled(true);
        }
        manage=view.findViewById(R.id.manage);
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转的Activity要改
                Intent intent=new Intent(getActivity(), public_BasicSettingActivity.class);
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
            Intent intent=new Intent(getActivity(), public_BasicSetting_UserManagement_UserDeptList_AddData.class);
            Bundle bundle=new Bundle();
            //外键的数组名要改
            bundle.putSerializable("array",SuperiorUseDeptIDStringArray);
            intent.putExtra("page",0);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private class Del implements View.OnClickListener{
        @Override
        public void onClick(View view){
            for (int i = 0; i< cbxAdapter.index.size(); i++){
                String id = ID.get(Integer.parseInt((String) cbxAdapter.index.get(i)));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //访问的数据库表名要改
                            delData.delData("UseDept", "{\"ID\":\"" + id + "\"}");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
            //跳转的AddActivity要改
            Intent intent=new Intent(getActivity(),public_BasicSettingActivity.class);
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
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.public_basic_setting_usermanagement_userdeptlist_item,
                            new String[]{"itemNumber","CreateBy","CreateDateTime","UpdateBy","UpdateDateTime","UserDeptCodeID"}, new int[]{R.id.itemNumber,R.id.creator,R.id.creatTime,R.id.updater,R.id.updateTime,R.id.UserDeptCodeID});
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new ItemClickListener());
                }
                class ItemClickListener implements AdapterView.OnItemClickListener {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ListView listView = (ListView) parent;
                        HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                        //跳转的SetActivity要改
                        Intent intent=new Intent(getActivity(), public_BasicSetting_UserManagement_UserDeptList_SetData.class);
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
                        JSONArray jsonArray= getData.getData("UseDept","");
                        data = new ArrayList<HashMap<String,Object>>();
                        for (int i=0;i<jsonArray.length();i++){
                            HashMap<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                            //当前item的序号
                            item.put("itemNumber",(i+1));
                            //以下为对应表的字段信息
                            item.put("CreateBy",jsonObject.getString("createBy"));
                            item.put("CreateDateTime",jsonObject.getString("createDateTime"));
                            item.put("UpdateBy",jsonObject.getString("updateBy"));
                            item.put("UpdateDateTime",jsonObject.getString("updateDateTime"));
                            item.put("UserDeptCodeID",jsonObject.getString("ID"));
                            item.put("UseDeptCode",jsonObject.getString("UseDeptCode"));
                            item.put("UseDeptName",jsonObject.getString("UseDeptName"));
                            item.put("UseDeptAllName",jsonObject.getString("UseDeptAllName"));
                            item.put("UseDeptDes",jsonObject.getString("UseDeptDes"));
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
                        JSONArray jsonArray= getData.getData("UseDept","");
                        data = new ArrayList<HashMap<String,Object>>();
                        for (int i=0;i<jsonArray.length();i++){
                            HashMap<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                            //当前item的序号
                            item.put("itemNumber",(i+1));
                            //以下为对应表的字段信息
                            item.put("CreateBy",jsonObject.getString("createBy"));
                            item.put("CreateDateTime",jsonObject.getString("createDateTime"));
                            item.put("UpdateBy",jsonObject.getString("updateBy"));
                            item.put("UpdateDateTime",jsonObject.getString("updateDateTime"));
                            item.put("UserDeptCodeID",jsonObject.getString("ID"));
                            item.put("UseDeptCode",jsonObject.getString("UseDeptCode"));
                            item.put("UseDeptName",jsonObject.getString("UseDeptName"));
                            item.put("UseDeptAllName",jsonObject.getString("UseDeptAllName"));
                            item.put("UseDeptDes",jsonObject.getString("UseDeptDes"));
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
            ID.add((String) data.get(i).get("UserDeptCodeID"));
        }
    }
    private void initViewOper(List<HashMap<String, Object>> data) {
        cbxAdapter = new cbx_Adapter_UserDeptList(data,models, getActivity(), new ProductData.AllCheckListener() {
            @Override
            public void onCheckedChanged(boolean b) {
                //根据不同的情况对maincheckbox做处理
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
        //全选的点击监听
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
                cbxAdapter.index=new ArrayList<>();
                for (Model_check model: models) {
                    if (model.ischeck()) {
                        cbxAdapter.index.add(model.getSt());
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
                    JSONArray jdataUseDeptID = getData.getData("UseDept","");
                    //字符串数组,用于存储目标字段的全部可取值
                    //先加1是为了写进固定的测试例子
                    String[] SuperiorUseDeptIDString = new String[jdataUseDeptID.length()];
                    for (int i=0;i<jdataUseDeptID.length();i++){
                        JSONObject SubjsonObject = jdataUseDeptID.getJSONObject(i);
                        SuperiorUseDeptIDString[i] = SubjsonObject.getString("ID");
                    }
                    SuperiorUseDeptIDStringArray = SuperiorUseDeptIDString;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
