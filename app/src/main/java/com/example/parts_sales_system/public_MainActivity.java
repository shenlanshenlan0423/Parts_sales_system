package com.example.parts_sales_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parts_sales_system.databinding.ActivityMainBinding;

public class public_MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Button BasicConfigurationButton, PublicUseManagementButton, FinancialManagementButton, StatisticsAnalyzeButton, PrivateUseManagementButton, InventManagementButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用binding就不用findViewByID了
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //公共功能和私有功能的按钮声明
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        BasicConfigurationButton = findViewById(R.id.BasicConfigurationButton);
        BasicConfigurationButton.setOnClickListener(new BasicSetting());
        PublicUseManagementButton = findViewById(R.id.PublicUseManagementButton);
        PublicUseManagementButton.setOnClickListener(new PublicUseManagement());
        PrivateUseManagementButton = findViewById(R.id.PrivateUseManagementButton);
        PrivateUseManagementButton.setOnClickListener(new PrivateUseManagement());
        FinancialManagementButton = findViewById(R.id.FinancialManagementButton);
        FinancialManagementButton.setOnClickListener(new FinancialManagement());
        InventManagementButton=findViewById(R.id.InventoryManagementButton);
        InventManagementButton.setOnClickListener(new InventoryManagement());
        StatisticsAnalyzeButton=findViewById(R.id.PrivateStatisticalAnalysisButton);
        StatisticsAnalyzeButton.setOnClickListener(new StatisticsAnalyze());
    }

    //禁止侧滑返回方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Toast.makeText(this, "当前页面禁止侧滑返回", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    //基础配置跳转方法
    private class BasicSetting implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            intent = new Intent(public_MainActivity.this, public_BasicSettingActivity.class);
            startActivity(intent);
        }
    }
    //共有使用管理跳转方法
    private class PublicUseManagement implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            intent = new Intent(public_MainActivity.this, public_UseManagementActivity.class);
            startActivity(intent);
        }
    }
    //财务管理的跳转方法
    private class FinancialManagement implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            intent = new Intent(public_MainActivity.this, public_FinancialManagementActivity.class);
            startActivity(intent);
        }
    }
    //使用管理的跳转方法
    private class PrivateUseManagement implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            intent = new Intent(public_MainActivity.this,private_UseManagementActivity.class);
            startActivity(intent);
        }
    }
    //库存管理的跳转方法
    private class InventoryManagement implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            intent = new Intent(public_MainActivity.this,private_InventManageActivity.class);
            startActivity(intent);
        }
    }
    //统计分析的跳转方法
    private class StatisticsAnalyze implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            intent = new Intent(public_MainActivity.this,private_StatisticsAnalyzeActivity.class);
            startActivity(intent);
        }
    }

}