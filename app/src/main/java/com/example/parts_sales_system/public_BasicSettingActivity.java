package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.parts_sales_system.ui.basic_setting.HomeFragment;

public class public_BasicSettingActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    Boolean flag;
    Boolean flag_out;
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_basic_setting);
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
    //选项菜单跳转主界面
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.MainActivity:
                Intent intent=new Intent(public_BasicSettingActivity.this,public_MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}