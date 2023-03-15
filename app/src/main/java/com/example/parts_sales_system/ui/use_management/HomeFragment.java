package com.example.parts_sales_system.ui.use_management;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.parts_sales_system.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private ArrayList fragmentList;
    private TabLayout tabLayout;
    private PatrolManagement PatrolManagement=new PatrolManagement();
    private List<String> mTitles;
    private String [] title={"巡检管理"};
    Boolean flag_in;
    Boolean flag_out;
    int page;
    public void setFlagin(Boolean flag){
        this.flag_in=flag;
    }
    public void setFlagout(Boolean flag){this.flag_out=flag;}
    public void setpage(int page){this.page=page;}
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.homefragment,container,false);
        pager=view.findViewById(R.id.page);
        tabLayout=view.findViewById(R.id.tab_layout);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        fragmentList=new ArrayList<>();
        mTitles=new ArrayList<>();
        PatrolManagement.setFlag(flag_in);
        fragmentList.add(PatrolManagement);
        mTitles.add(title[0]);

        fragmentAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
        pager.setCurrentItem(page);
    }
}
