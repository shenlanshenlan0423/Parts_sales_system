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
import com.example.parts_sales_system.ui.use_management.PatrolManagement;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private ArrayList fragmentList;
    private TabLayout tabLayout;
    //实例化对象名要改
    private RequirementManagement RequirementManagement=new RequirementManagement();
    private InstManagement InstManagement=new InstManagement();
//    private FeedbackManagement FeedbackManagement =new FeedbackManagement();
    private List<String> mTitles;
    //title要改
    private String [] title={"需求管理","安装管理","使用管理"};
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
        //PatrolManagement要改，几个二级功能就添加几次
        RequirementManagement.setFlag(flag_in);
        InstManagement.setFlag(flag_in);
//        FeedbackManagement.setFlag(flag_in);
        fragmentList.add(RequirementManagement);
        fragmentList.add(InstManagement);
//        fragmentList.add(FeedbackManagement);
        //title不止一个字符时，还要添加title[1]、title[2]等
        mTitles.add(title[0]);
        mTitles.add(title[1]);
//        mTitles.add(title[2]);

        fragmentAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
        pager.setCurrentItem(page);
    }

}
