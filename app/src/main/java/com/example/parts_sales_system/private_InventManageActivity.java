package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.parts_sales_system.ui.top_nav_fragment_invent.HomeFragment;
import com.example.parts_sales_system.ui.top_nav_fragment_invent.InManagement;

public class private_InventManageActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    Boolean flag;
    Boolean flag_out;
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_invent_manage);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        flag=getIntent().getBooleanExtra("flag",true);
        flag_out=getIntent().getBooleanExtra("flag_out",true);
        page=getIntent().getIntExtra("page",0);
        homeFragment = new HomeFragment();
        if(flag==false){
            homeFragment.setFlagin(true);
        }
        else {
            homeFragment.setFlagin(false);
        }
        if(flag_out==false){
            homeFragment.setFlagout(true);

        }
        else{
            homeFragment.setFlagout(false);
        }
        homeFragment.setpage(page);
        transaction.add(R.id.fragment_container, homeFragment);
        transaction.commit();
    }
}