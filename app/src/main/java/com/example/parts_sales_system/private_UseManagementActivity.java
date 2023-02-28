package com.example.parts_sales_system;

import android.os.Bundle;

import com.example.parts_sales_system.databinding.ActivityScrollingBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class private_UseManagementActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;
    // 巡检管理按钮
    private Button PatrolManagementButton;
    private TextView record_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        // 巡检管理
        PatrolManagementButton = findViewById(R.id.PatrolManagementButton);
        record_list = findViewById(R.id.record_list);
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        PatrolManagementButton.setOnClickListener(new PatrolManagement());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //单击二级功能弹出子菜单
    private class PatrolManagement implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(private_UseManagementActivity.this,PatrolManagementButton);
            popupMenu.getMenuInflater().inflate(R.menu.popmenu_patrolmanagement,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.item1:
                            record_list.setText("这是返回的记录列表");
                            break;
                        case R.id.item2:
                            break;
                        case R.id.item3:
                            break;
                        case R.id.item4:
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        }
    }
}