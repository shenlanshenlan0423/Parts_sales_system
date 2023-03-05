package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.parts_sales_system.data.api_connection.addData;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.databinding.ActivityPublicFinancialManagementBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class public_FinancialManagementActivity extends AppCompatActivity {
    private ActivityPublicFinancialManagementBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPublicFinancialManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView CreateByDatetime = binding.CreateDateTime1;
        TextView UpdateByDatetime = binding.UpdateDateTime1;
        Button OrderPaymentListButton = binding.OrderPaymentListButton;
        CreateByDatetime.setText("2023-03-05");
        UpdateByDatetime.setText("2023-03-06");
        OrderPaymentListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
//                            addData.addData("UseDept","{'UseDeptName':'test'}");
//                            getData.getData("MFJDingKuan","{'searchname':'hmy'}");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

//        binding.itemAddFloatButton.fab_add_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}