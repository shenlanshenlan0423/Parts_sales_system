package com.example.parts_sales_system.ui.public_use_management;

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

public class cbx_Adapter extends BaseAdapter{
    private List<Model_check> data;
    private Context context;
    private RequirementManagement.AllCheckListener allCheckListener;
    private List<HashMap<String, Object>> data_data;
    //需求管理（需求计划列表、订货信息列表）安装管理（安装进度列表、使用预警列表）反馈管理（现场反馈列表）
    private String creator,creatime,updater,updatetime,Requirement_PlanningCodeID;
    public static List index;
    public cbx_Adapter(List<HashMap<String, Object>> data_data, List<Model_check> data, Context context, RequirementManagement.AllCheckListener allCheckListener) {
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
            hd = new cbx_Adapter.ViewHoder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            //对应的布局文件要改
            view = layoutInflater.inflate(R.layout.public_use_management_rpllist_item_cbx, null);
            hd.itemNumber = view.findViewById(R.id.itemNumber);
            hd.creator= view.findViewById(R.id.creator);
            hd.createTime= view.findViewById(R.id.creatTime);
            hd.updater= view.findViewById(R.id.updater);
            hd.updateTime= view.findViewById(R.id.updateTime);
            //PatrolRecordCodeID要改
            hd.ID= view.findViewById(R.id.Requirement_PlanningCodeID);
            hd.checkBox = view.findViewById(R.id.cbx);
            view.setTag(hd);
        }
        Model_check mModel = data.get(i);
        hd = (cbx_Adapter.ViewHoder) view.getTag();
        hd.itemNumber.setText(String.valueOf(Integer.parseInt(mModel.getSt())+1));
        creator=String.valueOf(data_data.get(i).get("CreateBy"));
        hd.creator.setText(creator);
        creatime=String.valueOf(data_data.get(i).get("CreateDateTime"));
        hd.createTime.setText(creatime);
        updater=String.valueOf(data_data.get(i).get("UpdateBy"));
        hd.updater.setText(updater);
        updatetime=String.valueOf(data_data.get(i).get("UpdateDateTime"));
        hd.updateTime.setText(updatetime);
        //PatrolRecordCodeID要改
        Requirement_PlanningCodeID=String.valueOf(data_data.get(i).get("Requirement_PlanningCodeID"));
        hd.ID.setText(Requirement_PlanningCodeID);
        final cbx_Adapter.ViewHoder hdFinal = hd;
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
        TextView creator;
        TextView createTime;
        TextView updater;
        TextView updateTime;
        TextView ID;
    }
}
