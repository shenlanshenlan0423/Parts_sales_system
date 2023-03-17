package com.example.parts_sales_system.ui.statistics_analyze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parts_sales_system.R;

import java.util.List;
import java.util.Map;

public class CardListViewAdapter extends BaseAdapter {
    List<Map<String,Object>> list;
    private Context context;
    public CardListViewAdapter(List<Map<String,Object>> list,Context context){this.list=list;this.context=context;}
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder mHolder;
        if(convertView==null){
            view= LayoutInflater.from(context).inflate(R.layout.private_statisticsanalyze_card_listitem,null);
            if (position==0){
                view.setBackgroundResource(R.drawable.carlist_background1);
            }
            if (position==1){
                view.setBackgroundResource(R.drawable.cardlist_background2);
            }
            if (position==2){
                view.setBackgroundResource(R.drawable.cardlist_background3);
            }
            if (position==3){
                view.setBackgroundResource(R.drawable.cardlist_background4);
            }
            mHolder=new ViewHolder();
            mHolder.card_title=(TextView)view.findViewById(R.id.cardTitle);
            mHolder.card_image=(ImageView)view.findViewById(R.id.cardImg);
            mHolder.number=(TextView)view.findViewById(R.id.statistics_number);
            mHolder.money=(TextView)view.findViewById(R.id.statistics_money);
            mHolder.type=(TextView)view.findViewById(R.id.statistics_types);
            view.setTag(mHolder);  //将ViewHolder存储在View中
        }else {
            view=convertView;
            mHolder=(ViewHolder)view.getTag();  //重新获得ViewHolder
        }
        mHolder.card_title.setText(list.get(position).get("title").toString());
        mHolder.card_image.setImageResource((int)list.get(position).get("img"));
        mHolder.number.setText(list.get(position).get("number").toString());
        mHolder.money.setText(list.get(position).get("money").toString());
        mHolder.type.setText(list.get(position).get("type").toString());
        return view;
    }
}

class ViewHolder{
    TextView card_title;
    ImageView card_image;
    TextView number;
    TextView money;
    TextView type;
}

