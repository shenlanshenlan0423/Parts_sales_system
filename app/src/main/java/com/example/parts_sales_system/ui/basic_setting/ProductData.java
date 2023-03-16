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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parts_sales_system.R;
import com.example.parts_sales_system.data.api_connection.delData;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.public_BasicSettingActivity;
import com.example.parts_sales_system.public_BasicSetting_ProductData_MFJList_AddData;
import com.example.parts_sales_system.public_BasicSetting_ProductData_MFJList_SetData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//入库管理界面
public class ProductData extends Fragment {
    public TextView add, del, set;
    Button manage;
    private String[] UseDeptIDStringArray;
    Boolean mflag;
    public boolean mIsFromItem = false;
    ListView listView;
    CheckBox mMainCkb;
    cbx_Adapter cbxAdapter;
    private List<Model_check> models;
    List<HashMap<String, Object>> data;
    List<String> ID;
    public ProductData(){}
    public void setFlag(Boolean flag){
        this.mflag=flag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        getFKData();
        View view=inflater.inflate(R.layout.activity_public_basic_setting_product_data,container,false);
        add=view.findViewById(R.id.add);
        add.setOnClickListener(new Add());
        del=view.findViewById(R.id.del);
        del.setOnClickListener(new Del());
        set=view.findViewById(R.id.set);
        manage=view.findViewById(R.id.manage);
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), public_BasicSettingActivity.class);
                intent.putExtra("flag",mflag);
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
            Intent intent;
            intent=new Intent(getActivity(), public_BasicSetting_ProductData_MFJList_AddData.class);
            startActivity(intent);
        }
    }

    private class Del implements View.OnClickListener{
        @Override
        public void onClick(View view){
            for (int i=0;i<cbx_Adapter.index.size();i++){
                String id = ID.get(Integer.parseInt((String) cbx_Adapter.index.get(i)));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            delData.delData("MFJYan", "{\"ID\":\"" + id + "\"}");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
            Intent intent=new Intent(getActivity(), public_BasicSettingActivity.class);
        }
    }

    public void initList(boolean flag,View view){
        if (!flag){//管理按钮没有按下的初始化列表
            listView=view.findViewById(R.id.MFJList);
            Handler mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 1:{
                            data=(List<HashMap<String, Object>>)msg.obj;
                        }
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.mfjlist_item,
                            new String[]{"itemnumber","CreateBy","CreateDateTime","UpdateBy","UpdateDateTime","MFJID"}, new int[]{R.id.itemNumber,R.id.creator,R.id.creatTime,R.id.updater,R.id.updateTime,R.id.MFJID});
                    //实现列表的显示
                    listView.setAdapter(adapter);
                    //条目点击事件
                    listView.setOnItemClickListener(new ItemClickListener());
                }
                class ItemClickListener implements AdapterView.OnItemClickListener {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ListView listView = (ListView) parent;
                        HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                        Intent intent=new Intent(getActivity(), public_BasicSetting_ProductData_MFJList_SetData.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("data",data);
                        bundle.putSerializable("array",UseDeptIDStringArray);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            };

            new Thread(new Runnable(){
                @Override
                public void run() {
                    try{
                        JSONArray jsonArray= getData.getData("MFJ","");
                        data = new ArrayList<HashMap<String,Object>>();
                        for (int i=0;i<jsonArray.length();i++){
                            HashMap<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                            //当前item的序号
                            item.put("itemnumber",(i + 1));
                            //以下为对应表的字段信息
                            item.put("CreateBy",jsonObject.getString("createBy"));
                            item.put("CreateDateTime",jsonObject.getString("createDateTime"));
                            item.put("UpdateBy",jsonObject.getString("updateBy"));
                            item.put("UpdateDateTime",jsonObject.getString("updateDateTime"));
                            item.put("MFJID",jsonObject.getString("ID"));
                            item.put("UseDeptID",jsonObject.getString("UseDeptID"));
                            item.put("MFJName",jsonObject.getString("MFJName"));
                            item.put("MFJXing",jsonObject.getString("MFJXing"));
                            item.put("MFJWaiJing",jsonObject.getString("MFJWaiJing"));
                            item.put("MFJGuang",jsonObject.getString("MFJGuang"));
                            item.put("MFJYuJiGeng",jsonObject.getString("MFJYuJiGeng"));
                            item.put("MFJDes",jsonObject.getString("MFJDes"));
                            item.put("MFJZaiTu","");
                            item.put("MFJTuiHuo",jsonObject.getString("MFJTuiHuo"));
                            item.put("MFJZaiKu",jsonObject.getString("MFJZaiKu"));
                            item.put("MFJChuKu",jsonObject.getString("MFJChuKu"));
                            item.put("MFJZaiYong",jsonObject.getString("MFJZaiYong"));
                            item.put("MFJModelNo",jsonObject.getString("MFJModelNo"));
                            item.put("MFJModelName",jsonObject.getString("MFJModelName"));
                            item.put("MFJModelDes",jsonObject.getString("MFJModelDes"));
                            item.put("MFJModelIfYou",jsonObject.getString("MFJModelIfYou"));
                            item.put("MFJModelDate",jsonObject.getString("MFJModelDate"));
                            item.put("MFJModelDan",jsonObject.getString("MFJModelDan"));
                            item.put("MFJModelIfShou",jsonObject.getString("MFJModelIfShou"));
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
            listView=view.findViewById(R.id.MFJList);
            mMainCkb = (CheckBox) view.findViewById(R.id.checkAllBox);
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
                        JSONArray jsonArray= getData.getData("MFJYan","");
                        data = new ArrayList<HashMap<String,Object>>();
                        for (int i=0;i<jsonArray.length();i++){
                            HashMap<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                            item.put("CreateBy",jsonObject.getString("createBy"));
                            item.put("CreateDateTime",jsonObject.getString("createDateTime"));
                            item.put("UpdateBy",jsonObject.getString("updateBy"));
                            item.put("UpdateDateTime",jsonObject.getString("updateDateTime"));
                            item.put("MFJID",jsonObject.getString("ID"));
                            item.put("UseDeptID",jsonObject.getString("UseDeptID"));
                            item.put("MFJName",jsonObject.getString("MFJName"));
                            item.put("MFJXing",jsonObject.getString("MFJXing"));
                            item.put("MFJWaiJing",jsonObject.getString("MFJWaiJing"));
                            item.put("MFJGuang",jsonObject.getString("MFJGuang"));
                            item.put("MFJYuJiGeng",jsonObject.getString("MFJYuJiGeng"));
                            item.put("MFJDes",jsonObject.getString("MFJDes"));
                            item.put("MFJTuiHuo",jsonObject.getString("MFJTuiHuo"));
                            item.put("MFJZaiKu",jsonObject.getString("MFJZaiKu"));
                            item.put("MFJChuKu",jsonObject.getString("MFJChuKu"));
                            item.put("MFJZaiYong",jsonObject.getString("MFJZaiYong"));
                            item.put("MFJModelNo",jsonObject.getString("MFJModelNo"));
                            item.put("MFJModelName",jsonObject.getString("MFJModelName"));
                            item.put("MFJModelDes",jsonObject.getString("MFJModelDes"));
                            item.put("MFJModelIfYou",jsonObject.getString("MFJModelIfYou"));
                            item.put("MFJModelDate",jsonObject.getString("MFJModelDate"));
                            item.put("MFJModelDan",jsonObject.getString("MFJModelDan"));
                            item.put("MFJModelIfShou",jsonObject.getString("MFJModelIfShou"));
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
        //模拟数据
        models = new ArrayList<>();
        ID=new ArrayList<>();
        Model_check model;
        for (int i = 0; i < data.size(); i++) {
            model = new Model_check();
            model.setSt(String.valueOf(i));
            model.setIscheck(false);
            models.add(model);
            ID.add((String) data.get(i).get("ID"));
        }
    }
    private void initViewOper(List<HashMap<String, Object>> data) {
        cbxAdapter = new cbx_Adapter(data,models, getActivity(), new AllCheckListener() {
            @Override
            public void onCheckedChanged(boolean b) {
                //根据不同的情况对maincheckbox做处理
//                System.out.println(cbx_Adapter.index);
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
                    JSONArray jdataUseDeptID = getData.getData("UseDept","");
                    int jsonArrayOrderIDlength = jdataUseDeptID.length();
                    //字符串数组,用于存储目标字段的全部可取值
                    //先加1是为了写进固定的测试例子
                    String[] UseDeptIDString = new String[jsonArrayOrderIDlength+1];
                    for (int i=0;i<jsonArrayOrderIDlength;i++){
                        JSONObject SubjsonObject = jdataUseDeptID.getJSONObject(i);
                        UseDeptIDString[i] = SubjsonObject.getString("ID");
                    }
                    //固定的测试例子
                    UseDeptIDString[jsonArrayOrderIDlength] = "20230227110803832";
                    UseDeptIDStringArray = UseDeptIDString;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
