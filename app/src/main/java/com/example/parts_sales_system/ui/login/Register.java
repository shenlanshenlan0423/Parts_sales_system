package com.example.parts_sales_system.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parts_sales_system.R;
import com.example.parts_sales_system.data.api_connection.addData;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.public_MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    private Spinner companySpinner=null;
    private Button register,login;
    private EditText username,password,confirm_password;
    JSONArray jsonArray;
    String[] deptname=new String[15];//假设只有15个单位
    String[] deptid=new String[15];
    int dept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toast toast=Toast.makeText(getApplicationContext(),"start",Toast.LENGTH_LONG);
        toast.show();

        companySpinner =(Spinner)findViewById(R.id.company);
        register=(Button)findViewById(R.id.register);
        login=(Button)findViewById(R.id.login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
        final Intent[] intent = new Intent[1];

        //写入已有单位
        getJsonArrayData();
        //返回登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[0] = new Intent(Register.this, LoginActivity.class);
                startActivity(intent[0]);
            }
        });

        //注册信息动态变化

        confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                setArrayData(jsonArray);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(Register.this,R.layout.common_spinner_list,deptname);
                companySpinner.setAdapter(adapter);
                companySpinner.setSelection(0);
                companySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        dept=companySpinner.getSelectedItemPosition();
                    }
                    public void onNothingSelected(AdapterView<?> adapterView){
                    }
                });
                if (password.getText().toString().equals( confirm_password.getText().toString())) {
                    register.setEnabled(username.getText().toString() != null&&password.getText().toString() != null);
                    return;
                }else{
                    Toast toast5=Toast.makeText(getApplicationContext(),"确认密码是否相同",Toast.LENGTH_LONG);
                    toast5.show();}
            }
        });


        //读取注册信息
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //将无法修改和可修改的字段赋值到JSONObject中
                JSONObject jsonObject = new JSONObject();
                //前两个外键MFJOrderID、UserID先固定，后面再和当前登录的用户、选中的密封件编号绑定
                try {
                    jsonObject.put("ID","").put("UserName",username.getText().toString())
                            .put("UserLoginPwd",password.getText().toString())
                            .put("UseDeptID",deptid[dept])
                            .put("DeptID","1");//现只有一个信息中心，所以指定。
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //jsonObject转String
                String jsonObjectstring = String.valueOf(jsonObject);
                //调用新增方法
                addJsonArrayData(jsonObjectstring);
                //登录界面
                intent[0] = new Intent(Register.this, public_MainActivity.class);
                startActivity(intent[0]);
            }
        });


    };
    void getJsonArrayData(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    jsonArray = getData.getData("UseDept","");//此处不需要按条件查询，返回全表信息即可
                } catch (Exception e) {
                    Toast toast2=Toast.makeText(getApplicationContext(),"数据库连接失败",Toast.LENGTH_LONG);
                    toast2.show();
                    e.printStackTrace();
                }
            }
        }).start();
    };
    void setArrayData(JSONArray jdata){
        try{
            for (int i=0;i<jdata.length();i++) {
                JSONObject jobject = jdata.getJSONObject(i);
                deptname[i]=jobject.getString("UseDeptName");
                deptid[i]=jobject.getString("ID");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    void addJsonArrayData(String jsonObjectstring){
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    addData.addData("User",jsonObjectstring);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast3=Toast.makeText(getApplicationContext(),"成功注册",Toast.LENGTH_LONG);
                    toast3.show();
                }
            }
        }).start();
    }
}
