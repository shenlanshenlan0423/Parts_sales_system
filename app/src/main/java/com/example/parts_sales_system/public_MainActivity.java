package com.example.parts_sales_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.example.parts_sales_system.data.api_connection.ad_alData;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //测试代码
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    ad_alData.ad_alDate("UseDept","{searchname:\"hmy\"}");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //使用管理的跳转按钮
        Button UseManagementButton;
        Button InventoryManagementButton;
        Button FinancialManagementButton;
        Button AccAnalyzeButton;

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

        UseManagementButton = findViewById(R.id.UseManagementButton);
        UseManagementButton.setOnClickListener(new UseManagement());

        InventoryManagementButton=findViewById(R.id.InventoryManagementButton);
        InventoryManagementButton.setOnClickListener(new InventoryManagement());

        FinancialManagementButton=findViewById(R.id.FinancialManagementButton);
        FinancialManagementButton.setOnClickListener(new FinancialManagement());

        AccAnalyzeButton=findViewById(R.id.AccAnalyzeButton);
        AccAnalyzeButton.setOnClickListener(new AccAnalyze());
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
    //使用管理的跳转方法
    private class UseManagement implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            intent = new Intent(public_MainActivity.this,private_UseManagementActivity.class);
            startActivity(intent);
        }
    }

    private class AccAnalyze implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            intent = new Intent(public_MainActivity.this,private_UseManagementActivity.class);
            startActivity(intent);
        }
    }

    private class InventoryManagement implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            intent = new Intent(public_MainActivity.this,private_UseManagementActivity.class);
            startActivity(intent);
        }
    }

    private class FinancialManagement implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View view) {
            intent = new Intent(public_MainActivity.this,private_UseManagementActivity.class);
            startActivity(intent);
        }
    }

}