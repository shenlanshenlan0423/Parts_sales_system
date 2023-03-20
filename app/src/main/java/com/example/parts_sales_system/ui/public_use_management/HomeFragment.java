package com.example.parts_sales_system.ui.public_use_management;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.parts_sales_system.R;
import com.example.parts_sales_system.ui.top_nav_fragment_invent.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private ArrayList fragmentList;
    private TabLayout tabLayout;
    //实例化对象名要改
    private InstManagement InstManagement=new InstManagement();
    private RequirementManagement RequirementManagement=new RequirementManagement();
    private FeedBackManagement FeedBackManagement=new FeedBackManagement();
    private OrderInfoFragment OrderinfoFragment=new OrderInfoFragment();
    private UseAlertFragment UseAlertFragment=new UseAlertFragment();
    private List<String> mTitles;
    //title要改
    private String [] title={"需求计划","订货信息","安装进度","使用预警","反馈管理"};
    Boolean flag_replan;
    Boolean flag_orinfo;
    Boolean flag_instpro;
    Boolean flag_usealert;
    Boolean flag_feedback;
    int page;
    public void setFlag_replan(Boolean flag){
        this.flag_replan=flag;
    }
    public void setFlag_orinfo(Boolean flag){this.flag_orinfo=flag;}
    public void setFlag_instpro(Boolean flag){this.flag_instpro=flag;}
    public void setFlag_usealert(Boolean flag){this.flag_usealert=flag;}
    public void setFlag_feedback(Boolean flag){this.flag_feedback=flag;}
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
        //PatrolManagement要改，几个二级功能就添加几次
        InstManagement.setFlag(flag_instpro);
        RequirementManagement.setFlag(flag_replan);
        FeedBackManagement.setFlag(flag_feedback);
        OrderinfoFragment.setFlag(flag_orinfo);
        UseAlertFragment.setFlag(flag_usealert);
        fragmentList.add(RequirementManagement);
        fragmentList.add(OrderinfoFragment);
        fragmentList.add(InstManagement);
        fragmentList.add(UseAlertFragment);
        fragmentList.add(FeedBackManagement);
        //title不止一个字符时，还要添加title[1]、title[2]等
        mTitles.add(title[0]);
        mTitles.add(title[1]);
        mTitles.add(title[2]);
        mTitles.add(title[3]);
        mTitles.add(title[4]);
        fragmentAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
        pager.setCurrentItem(page);
    }
}
