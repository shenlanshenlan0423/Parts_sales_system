package com.example.parts_sales_system.ui.top_nav_fragment_basic_setting;

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
    private ProductData ProductData=new ProductData();
    private AuthorityManagement AuthorityManagement=new AuthorityManagement();
    private UserManagement UserManagement=new UserManagement();

    private List<String> mTitles;
    private String [] title={"权限管理","用户管理","产品数据"};
    Boolean flag_in;
    public void setFlagin(Boolean flag){
        this.flag_in=flag;
    }
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
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
//        System.out.println(flag_in);
    }
}
