package com.example.parts_sales_system.ui.basic_setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.parts_sales_system.R;
import com.example.parts_sales_system.ui.top_nav_fragment_invent.Model_check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class cbs_Adapter_User extends BaseAdapter {
    private List<Model_check> data;
    private Context context;
    private SystemUserList.AllCheckListener allCheckListener;
    private List<HashMap<String, Object>> data_data;
    //最后一个字符串命名要改
    private String ID,userNumber,userName,userTel,userType;
    public static List index;
    public cbs_Adapter_User(List<HashMap<String, Object>> data_data, List<Model_check> data, Context context, SystemUserList.AllCheckListener allCheckListener) {
        this.data_data=data_data;
        this.data = data;
        this.context = context;
        this.allCheckListener = allCheckListener;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHoder hd;
        if (view == null) {
            hd = new ViewHoder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            //对应的布局文件要改
            view = layoutInflater.inflate(R.layout.public_basic_setting_systemuserlist_item_cbx, null);
            hd.itemNumber = view.findViewById(R.id.itemNumber);
            hd.ID= view.findViewById(R.id.userID);
            hd.userNumber= view.findViewById(R.id.userNumber);
            hd.userName= view.findViewById(R.id.user);
            hd.userTel= view.findViewById(R.id.phone);
            hd.userType= view.findViewById(R.id.usertype);
            hd.checkBox = view.findViewById(R.id.cbx);
            view.setTag(hd);
        }
        Model_check mModel = data.get(i);
        hd = (ViewHoder) view.getTag();
        hd.itemNumber.setText(String.valueOf(Integer.parseInt(mModel.getSt())+1));
        ID=String.valueOf(data_data.get(i).get("UserCodeID"));
        hd.ID.setText(ID);
        userNumber=String.valueOf(data_data.get(i).get("UserNo"));
        hd.userNumber.setText(userNumber);
        userName=String.valueOf(data_data.get(i).get("UserName"));
        hd.userName.setText(userName);
        userTel=String.valueOf(data_data.get(i).get("UserTel"));
        hd.userTel.setText(userTel);
        //PatrolRecordCodeID要改
        userType=String.valueOf(data_data.get(i).get("UserType"));
        hd.userType.setText(userType);
        final ViewHoder hdFinal = hd;
        hd.checkBox.setChecked(mModel.ischeck());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = hdFinal.checkBox;
                if (checkBox.isChecked()){
                    checkBox.setChecked(false);
                    data.get(i).setIscheck(false);

                } else {
                    checkBox.setChecked(true);
                    data.get(i).setIscheck(true);
                }
                index=new ArrayList<>();
                for (Model_check model: data) {
                    if (model.ischeck()) {
                        index.add(model.getSt());
                    }
                    else {
                        continue;
                    }
                }
                //监听每个item，若所有checkbox都为选中状态则更改main的全选checkbox状态
                for (Model_check model: data) {
                    if (!model.ischeck()) {
                        allCheckListener.onCheckedChanged(false);
                        return;
                    }
                }
                allCheckListener.onCheckedChanged(true);
            }
        });
        return view;
    }

    class ViewHoder {
        TextView itemNumber;
        CheckBox checkBox;
        TextView ID,userNumber,userName,userTel,userType;
    }

}