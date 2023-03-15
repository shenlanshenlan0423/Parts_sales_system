package com.example.parts_sales_system.ui.basic_setting;

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
    private TabLayout tabLayout1,tabLayout2;
    private ProductData ProductData=new ProductData();
    private AuthorityManagement AuthorityManagement=new AuthorityManagement();
    private UserManagement UserManagement=new UserManagement();
    private List<String> mTitles;
    private String [] title={"权限管理","用户管理","产品数据"};
//    private String [] title2={"使用单位列表","系统用户列表","系统用户日志"};
    Boolean flag_in;
    public void setFlagin(Boolean flag){
        this.flag_in=flag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.common_fragment,container,false);
        pager=view.findViewById(R.id.common_page);
        tabLayout1=view.findViewById(R.id.tab_layout1);
        tabLayout2=view.findViewById(R.id.tab_layout2);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        fragmentList=new ArrayList<>();
        mTitles=new ArrayList<>();
        AuthorityManagement.setFlag(flag_in);
        UserManagement.setFlag(flag_in);
        ProductData.setFlag(flag_in);
        fragmentList.add(AuthorityManagement);
        fragmentList.add(UserManagement);
        fragmentList.add(ProductData);
        mTitles.add(title[0]);
        mTitles.add(title[1]);
        mTitles.add(title[2]);

        fragmentAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout1.setupWithViewPager(pager);//与ViewPage建立关系
        tabLayout2.setupWithViewPager(pager);
    }
}
