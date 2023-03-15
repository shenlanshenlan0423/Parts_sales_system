package com.example.parts_sales_system.ui.use_management;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.parts_sales_system.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class cbx_Adapter extends BaseAdapter {
    private List<Model_check> data;
    private Context context;
    private PatrolManagement.AllCheckListener allCheckListener;
    private List<HashMap<String, Object>> data_data;
    private String creator;
    private String creatime;
    private String updater;
    private String updatetime;
    private String mfjyanid;
    private String itemnumber;
    public static List index;
    public cbx_Adapter(List<HashMap<String, Object>> data_data, List<Model_check> data, Context context, PatrolManagement.AllCheckListener allCheckListener) {
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
            view = layoutInflater.inflate(R.layout.item_cbx, null);
            hd.textView = (TextView) view.findViewById(R.id.text_title);
            hd.creator=(TextView)view.findViewById(R.id.creator);
            hd.createTime=(TextView)view.findViewById(R.id.creatTime);
            hd.updater=(TextView)view.findViewById(R.id.updater);
            hd.updateTime=(TextView)view.findViewById(R.id.updateTime);
            hd.mfjyanid=(TextView)view.findViewById(R.id.receipts_Id);
            hd.checkBox = (CheckBox) view.findViewById(R.id.cbx);
            view.setTag(hd);
        }
        Model_check mModel = data.get(i);
        hd = (ViewHoder) view.getTag();
        hd.textView.setText(String.valueOf(Integer.parseInt(mModel.getSt())+1));
        creator=String.valueOf(data_data.get(i).get("creator"));
        hd.creator.setText(creator);
        creatime=String.valueOf(data_data.get(i).get("createTime"));
        hd.createTime.setText(creatime);
        updater=String.valueOf(data_data.get(i).get("updater"));
        hd.updater.setText(updater);
        updatetime=String.valueOf(data_data.get(i).get("updatetime"));
        hd.updateTime.setText(updatetime);
        mfjyanid=String.valueOf(data_data.get(i).get("ID"));
        hd.mfjyanid.setText(mfjyanid);
        Log.e("myadapter", mModel.getSt()+ "------" + mModel.ischeck());
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
                System.out.println(index);
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
        TextView textView;
        CheckBox checkBox;
        TextView creator;
        TextView createTime;
        TextView updater;
        TextView updateTime;
        TextView mfjyanid;
    }

}

