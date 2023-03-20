package com.example.parts_sales_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.parts_sales_system.ui.public_use_management.HomeFragment;


public class public_UseManagementActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    Boolean flag_replan;
    Boolean flag_orinfo;
    Boolean flag_instpro;
    Boolean flag_usealert;
    Boolean flag_feedback;
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_use_management);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        flag_replan=getIntent().getBooleanExtra("flag_replan",true);
        flag_orinfo=getIntent().getBooleanExtra("flag_orinfo",true);
        flag_instpro=getIntent().getBooleanExtra("flag_instpro",true);
        flag_usealert=getIntent().getBooleanExtra("flag_usealert",true);
        flag_feedback=getIntent().getBooleanExtra("flag_feedback",true);
        page=getIntent().getIntExtra("page",0);
        homeFragment = new HomeFragment();
        if(flag_replan==false){
            homeFragment.setFlag_replan(true);
        }
        else {
            homeFragment.setFlag_replan(false);
        }
        if(flag_orinfo==false){
            homeFragment.setFlag_orinfo(true);
        }
        else{
            homeFragment.setFlag_orinfo(false);
        }
        if(flag_instpro==false){
            homeFragment.setFlag_instpro(true);
        }
        else{
            homeFragment.setFlag_instpro(false);
        }
        if(flag_usealert==false){
            homeFragment.setFlag_usealert(true);
        }
        else{
            homeFragment.setFlag_usealert(false);
        }
        if(flag_feedback==false){
            homeFragment.setFlag_feedback(true);
        }
        else{
            homeFragment.setFlag_feedback(false);
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
                Intent intent=new Intent(public_UseManagementActivity.this,public_MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}