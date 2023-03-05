package com.example.parts_sales_system;

import android.os.Bundle;

import com.example.parts_sales_system.databinding.ActivityPrivateUseManagementBinding;
import com.example.parts_sales_system.databinding.ActivityScrollingBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class private_UseManagementActivity extends AppCompatActivity {

    private ActivityPrivateUseManagementBinding binding;
    // 巡检管理按钮
    private Button PatrolManagementButton;
    private EditText CreateByEditText, CreateDateTimeEditText, UpdateByEditText, UpdateDateTimeEditText;

    //与数据库里巡检记录列表的字段相对应
//            CreateDateTime,
//            UpdateBy,
//            UpdateDateTime,
//            PatrolRecordEncodingID,
//            BuildRecordEncodingID,
//            PatrolRecordDate,
//            PatrolRecordContent,
//            PatrolRecorder,
//            PatrolRecordPhoto1,
//            PatrolRecordPhoto2,
//            PatrolRecordPhoto3,
//            PatrolRecordPhoto4,
//            PatrolRecordPhoto5;
    JSONObject jsonObject;
//    JSONObject jCreateBy,
//            jCreateDateTime,
//            jUpdateBy,
//            jUpdateDateTime,
//            jPatrolRecordEncodingID,
//            jBuildRecordEncodingID,
//            jPatrolRecordDate,
//            jPatrolRecordContent,
//            jPatrolRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrivateUseManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Title 使用管理四个大字
//        Toolbar toolbar = binding.toolbar;
//        setSupportActionBar(toolbar);
//        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
//        toolBarLayout.setTitle(getTitle());

        // 巡检管理
        PatrolManagementButton = binding.PatrolManagementButton;
        CreateByEditText = binding.CreateBy;
        CreateDateTimeEditText = binding.CreateDateTime;
        UpdateByEditText = binding.UpdateBy;
        UpdateDateTimeEditText = binding.UpdateDateTime;
//        CreateDateTime,
//        UpdateBy,
//        UpdateDateTime,
//        FloatingActionButton fab = binding.fab;
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        // 巡检管理事件响应
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
                        case R.id.PatrolRecordList:
                            setArrayData();
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

//    void setJsonData (){
//        try {
//            jCreateBy = new JSONObject();
//            jCreateBy.put("CreateBy","hhw");
//            String CreateByString = jCreateBy.getString("CreateBy");
//            CreateBy.setText(CreateByString);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    void setArrayData(){
        try {
            JSONArray jdata = new JSONArray();
            jsonObject = new JSONObject();
            jsonObject.put("CreateBy","hhw").put("CreateDateTime","0302")
                    .put("UpdateBy","wwh").put("UpdateDateTime","0303");
            jdata.put(jsonObject);
            String CreateByString, CreateDateTimeString, UpdateByString, UpdateDateTimeString;

            //寻访json中包含的所有键值对
            int jdata_length = jdata.length();
            for (int i=0; i< jdata_length; i++){
                JSONObject SubjsonObject = jdata.getJSONObject(i);
                CreateByString = SubjsonObject.getString("CreateBy");
                CreateDateTimeString = SubjsonObject.getString("CreateDateTime");
                UpdateByString = SubjsonObject.getString("UpdateBy");
                UpdateDateTimeString = SubjsonObject.getString("UpdateDateTime");

                // 赋值语句
                CreateByEditText.setText(CreateByString);
                CreateDateTimeEditText.setText(CreateDateTimeString);
                UpdateByEditText.setText(UpdateByString);
                UpdateDateTimeEditText.setText(UpdateDateTimeString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}