package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.parts_sales_system.ui.basic_setting.HomeFragment;

public class public_BasicSettingActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    Boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_basic_setting);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        flag=getIntent().getBooleanExtra("flag",true);
        homeFragment = new HomeFragment();
        if(flag==false){
            homeFragment.setFlagin(true);
        }
        else {
            homeFragment.setFlagin(false);
        }
        transaction.add(R.id.fragment_container, homeFragment);
        transaction.commit();
    }
}