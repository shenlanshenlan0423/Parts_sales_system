package com.example.parts_sales_system;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.parts_sales_system.data.api_connection.addData;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SetInData_Alertdialog extends Activity {
    TextView mfjyan_creator;
    TextView mfjyan_cdt;
    TextView mfjyan_udb;
    TextView mfjyan_udt;
    TextView mfjyan_receiptid;
    TextView mfjyan_orderid;
    TextView mfjyan_userid;
    EditText mfjyan_order;
    EditText mfjyan_date;
    EditText mfjyan_des;
    TextView username;
    Button set;
    Button cancel;
    String id;
    String order;
    String date;
    String des;
    String orderid;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_in_data_alertdialog);
        Intent intent=getIntent();
        HashMap<String, Object> data= (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        mfjyan_creator=findViewById(R.id.mfjyan_creator);
        mfjyan_creator.setText((String)data.get("creator"));
        mfjyan_cdt=findViewById(R.id.mfjyan_cdt);
        mfjyan_cdt.setText((String)data.get("createTime"));
        mfjyan_udb=findViewById(R.id.mfjyan_udb);
        mfjyan_udb.setText((String)data.get("updater"));
        mfjyan_udt=findViewById(R.id.mfjyan_udt);
        mfjyan_udt.setText((String)data.get("updatetime"));
        mfjyan_receiptid=findViewById(R.id.mfjyan_receiptid);
        mfjyan_receiptid.setText((String)data.get("ID"));
        id=String.valueOf(mfjyan_receiptid.getText());
        mfjyan_orderid=findViewById(R.id.mfjyan_orderid);
        mfjyan_orderid.setText((String)data.get("orderid"));
        orderid=String.valueOf(mfjyan_orderid.getText());
        mfjyan_userid=findViewById(R.id.mfjyan_userid);
        mfjyan_userid.setText((String)data.get("UserID"));
        userid=String.valueOf(mfjyan_userid.getText());
        mfjyan_order=findViewById(R.id.mfjyan_order);
        mfjyan_order.setText((String)data.get("MFJYanOrder"));
        mfjyan_date=findViewById(R.id.mfjyan_date);
        mfjyan_date.setText((String)data.get("MFJYanDate"));
        mfjyan_des=findViewById(R.id.mfjyan_des);
        mfjyan_des.setText((String)data.get("MFJYanDes"));
        username=findViewById(R.id.username);
        username.setText((String)data.get("Username"));
        set=findViewById(R.id.button);
        set.setOnClickListener(new set());
        cancel=findViewById(R.id.button2);
        cancel.setOnClickListener(new cancel());

    }
    public class set implements View.OnClickListener{
        @Override
        public void onClick(View view){
            order= String.valueOf(mfjyan_order.getText());
            date=String.valueOf(mfjyan_date.getText());
            des=String.valueOf(mfjyan_des.getText());
            new Thread(new Runnable(){
                @Override
                public void run() {
                    System.out.println(order);
                    try {
                        System.out.println("{\"ID\":\""+id+"\","+ "\"MFJOrderID\":\""+orderid+"\","+ "\"MFJYanDate\":\""+date+"\",\n" +
                                "\"MFJYanOrder\":\""+order+"\","+
                                "\"MFJYanDes\":\""+des+"\"," +
                                "\"UserID\":\""+userid+"\"" +
                                "}");
                        addData.addData("MFJYan","{\"ID\":\""+id+"\","+ "\"MFJOrderID\":\""+orderid+"\","+ "\"MFJYanDate\":\""+date+"\",\n" +
                                "\"MFJYanOrder\":\""+order+"\","+
                                "\"MFJYanDes\":\""+des+"\"," +
                                "\"UserID\":\""+userid+"\"" +
                                "}");
                        Intent intent=new Intent(SetInData_Alertdialog.this,private_InventManageActivity.class);
                        startActivity(intent);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();

        }
    }
    public class cancel implements  View.OnClickListener{
        @Override
        public void onClick(View view){
            Intent intent=new Intent(SetInData_Alertdialog.this,private_InventManageActivity.class);
            startActivity(intent);
        }
    }
}