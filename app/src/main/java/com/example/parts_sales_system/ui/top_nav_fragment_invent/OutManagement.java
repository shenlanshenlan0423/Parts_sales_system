package com.example.parts_sales_system.ui.top_nav_fragment_invent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parts_sales_system.R;

//出库管理界面
public class OutManagement extends Fragment {
    public OutManagement(){}
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.activity_private_invent_manage_out_management,container,false);
        return view;
    }

}
