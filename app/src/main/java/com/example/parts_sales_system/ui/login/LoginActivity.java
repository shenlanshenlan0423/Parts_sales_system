package com.example.parts_sales_system.ui.login;

import android.app.Activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.example.parts_sales_system.data.api_connection.getData;

import com.example.parts_sales_system.public_MainActivity;
import com.example.parts_sales_system.R;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private JSONArray jsonArray1;
    private Button toregisterButton,loginButton;
    private EditText usernameEditText,passwordEditText;
    String[] usernames=new String[20];
    String[] userpwds=new String[20];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        toregisterButton = findViewById(R.id.register);
        Intent[] intent = new Intent[1];
        getJsonArrayData();


//        passwordEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//                //loginButton.setEnabled;
//            }});


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setArrayData(jsonArray1);
                List<String> list = Arrays.asList(usernames);
                System.out.println(list);
                String tip=usernameEditText.getText().toString();

                if (list.contains(tip)) {
                    if (passwordEditText.getText().toString().equals(userpwds[list.indexOf(tip)])) {
                        intent[0] = new Intent(LoginActivity.this, public_MainActivity.class);
                        startActivity(intent[0]);
                    }else {
                        Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
                }
            }
        });
        toregisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[0] = new Intent(LoginActivity.this, Register.class);
                startActivity(intent[0]);
            }
        });
    }
    void getJsonArrayData(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    jsonArray1 = getData.getData("User","");//此处不需要按条件查询，返回全表信息即可
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "连接失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }).start();
    };
    void setArrayData(JSONArray jdata){
        try{
            for (int i=0;i<jdata.length();i++) {
                JSONObject jobject = jdata.getJSONObject(i);
                usernames[i]=jobject.getString("UserName");
                userpwds[i]=jobject.getString("UserLoginPwd");
            }
            System.out.println(usernames);
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "写入出错", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}