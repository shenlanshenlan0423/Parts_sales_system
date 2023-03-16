package com.example.parts_sales_system.ui.top_nav_fragment_invent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parts_sales_system.private_inventmanage_AddInData_Alertdialog;
import com.example.parts_sales_system.R;
import com.example.parts_sales_system.private_inventmanage_SetInData_Alertdialog;
import com.example.parts_sales_system.data.api_connection.delData;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.private_InventManageActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//入库管理界面
public class InManagement extends Fragment {
    public com.getbase.floatingactionbutton.FloatingActionButton add;
    public com.getbase.floatingactionbutton.FloatingActionButton del;
    com.getbase.floatingactionbutton.FloatingActionButton manage;
    Boolean mflag;
    public boolean mIsFromItem = false;
    ListView listView;
    CheckBox mMainCkb;
    cbx_Adapter cbxAdapter;
    private List<Model_check> models;
    List<HashMap<String, Object>> data;
    List<String> ID;
    public InManagement(){}
    public void setFlag(Boolean flag){
        this.mflag=flag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.activity_private_invent_manage_in_management,container,false);
        add=view.findViewById(R.id.add);
        add.setOnClickListener(new Add());
        del=view.findViewById(R.id.del);
        del.setOnClickListener(new Del());
        manage=view.findViewById(R.id.manage);
        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),private_InventManageActivity.class);
                intent.putExtra("flag",mflag);
                intent.putExtra("page",0);
                startActivity(intent);
            }
        });
//        System.out.println(mflag);
        if (mflag==null){mflag=false;}
        initList(mflag,view);
        return view;
    }
    private class Add implements View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent;
            intent=new Intent(getActivity(), private_inventmanage_AddInData_Alertdialog.class);
            intent.putExtra("page",0);
            startActivity(intent);
        }
    }

    private class Del implements View.OnClickListener{
        @Override
        public void onClick(View view){
            for (int i=0;i<cbx_Adapter.index.size();i++){
                String id = ID.get(Integer.parseInt((String) cbx_Adapter.index.get(i)));
//                System.out.println(id);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            System.out.println("{\"ID\":\"" + id + "\"}");
                            delData.delData("MFJYan", "{\"ID\":\"" + id + "\"}");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
            Intent intent=new Intent(getActivity(),private_InventManageActivity.class);
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
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.item,
                            new String[]{"creator","createTime","updater","updatetime","ID","itemNumber"}, new int[]{R.id.creator,R.id.creatTime,R.id.updater,R.id.updateTime,R.id.receipts_Id,R.id.itemNumber});
                    //实现列表的显示
                    listView.setAdapter(adapter);
                    //条目点击事件
                    listView.setOnItemClickListener(new ItemClickListener());
                }
                class ItemClickListener implements AdapterView.OnItemClickListener {

                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ListView listView = (ListView) parent;
                        HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
//                        System.out.println(data);//点击跳出弹窗，显示数据
                        Intent intent=new Intent(getActivity(), private_inventmanage_SetInData_Alertdialog.class);
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
                        JSONArray jsonArray= getData.getData("MFJYan","null");
//                    System.out.println(jsonArray);
                        data = new ArrayList<HashMap<String,Object>>();
                        for (int i=0;i<jsonArray.length();i++){
                            HashMap<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                            item.put("creator",jsonObject.getString("createBy"));
                            item.put("createTime",jsonObject.getString("createDateTime"));
                            item.put("updater",jsonObject.getString("updateBy"));
                            item.put("updatetime",jsonObject.getString("updateDateTime"));
                            item.put("ID",jsonObject.getString("ID"));
                            item.put("orderid",jsonObject.getString("MFJOrderID"));
                            item.put("UserID",jsonObject.getString("UserID"));
                            item.put("MFJYanOrder",jsonObject.getString("MFJYanOrder"));
                            item.put("MFJYanDate",jsonObject.getString("MFJYanDate"));
                            item.put("MFJYanDes",jsonObject.getString("MFJYanDes"));
                            item.put("Username",jsonObject.getString("UserName"));
                            item.put("itemNumber"," "+(i+1)+" ");
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
                        JSONArray jsonArray= getData.getData("MFJYan","null");
//                    System.out.println(jsonArray);
                        data = new ArrayList<HashMap<String,Object>>();
                        for (int i=0;i<jsonArray.length();i++){
                            HashMap<String, Object> item = new HashMap<String, Object>();
                            JSONObject jsonObject=new JSONObject(jsonArray.getString(i));
                            item.put("creator",jsonObject.getString("createBy"));
                            item.put("createTime",jsonObject.getString("createDateTime"));
                            item.put("updater",jsonObject.getString("updateBy"));
                            item.put("updatetime",jsonObject.getString("updateDateTime"));
                            item.put("ID",jsonObject.getString("ID"));
                            item.put("orderid",jsonObject.getString("MFJOrderID"));
                            item.put("UserID",jsonObject.getString("UserID"));
                            item.put("MFJYanOrder",jsonObject.getString("MFJYanOrder"));
                            item.put("MFJYanDate",jsonObject.getString("MFJYanDate"));
                            item.put("MFJYanDes",jsonObject.getString("MFJYanDes"));
                            item.put("Username",jsonObject.getString("UserName"));
                            item.put("itemNumber"," "+String.valueOf(i+1)+" ");
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
//        System.out.println(ID);
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
//                System.out.println(cbx_Adapter.index);
                //刷新listview
                cbxAdapter.notifyDataSetChanged();
            }
        });
    }
    interface AllCheckListener {
        void onCheckedChanged(boolean b);
    }
}
