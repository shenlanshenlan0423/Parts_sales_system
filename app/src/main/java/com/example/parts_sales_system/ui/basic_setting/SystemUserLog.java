package com.example.parts_sales_system.ui.basic_setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parts_sales_system.R;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.public_BasicSetting_UserManagement_SystemUserLog_SetData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SystemUserLog extends Fragment {
    Boolean mflag=false;
    public boolean mIsFromItem = false;
    ListView listView;
    List<HashMap<String, Object>> data;
    public SystemUserLog(){}
    public void setFlag(Boolean flag){
        this.mflag=flag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.activity_public_basic_setting_usermanagement_systemuserlog,container,false);
        if (mflag==null){mflag=false;}
        initList(mflag,view);
        return view;
    }


    public void initList(boolean flag,View view){
        listView=view.findViewById(R.id.listView);
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
                SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.public_basic_setting_usermanagement_systemuserlog_item,
                        new String[]{"itemNumber","CreateBy","CreateDateTime","UpdateBy","UpdateDateTime","UserOperateLogCodeID"}, new int[]{R.id.itemNumber,R.id.creator,R.id.creatTime,R.id.updater,R.id.updateTime,R.id.UserOperateLogCodeID});
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new ItemClickListener());
            }
            class ItemClickListener implements AdapterView.OnItemClickListener {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListView listView = (ListView) parent;
                    HashMap<String, Object> data = (HashMap<String, Object>) listView.getItemAtPosition(position);
                    //跳转的SetActivity要改
                    Intent intent=new Intent(getActivity(), public_BasicSetting_UserManagement_SystemUserLog_SetData.class);
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
                    JSONArray jsonArray= getData.getData("UserOperLog","");
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
                        item.put("UserOperateLogCodeID",jsonObject.getString("ID"));
                        item.put("UserID",jsonObject.getString("UserID"));
                        item.put("UserOperIP",jsonObject.getString("UserOperIP"));
                        item.put("UserOperLogDes",jsonObject.getString("UserOperLogDes"));
                        item.put("UserOperSource",jsonObject.getString("UserOperSource"));
                        item.put("UserOperName",jsonObject.getString("UserOperName"));
                        item.put("UserOperType",jsonObject.getString("UserOperType"));
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
    interface AllCheckListener {
        void onCheckedChanged(boolean b);
    }
}
