package com.example.parts_sales_system.data;

import android.widget.Toast;

import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.data.model.LoggedInUser;
import com.example.parts_sales_system.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private JSONArray jsonArray;
    String[] usernames,userpwds=new String[20];

    public Result<LoggedInUser> login(String username, String password) {

        try {
            getJsonArrayData();
            setArrayData(jsonArray);
            List<String> list= Arrays.asList(usernames);
            if(list.contains(username)){
                if(password.equals(userpwds[list.indexOf(username)])){
                    LoggedInUser fakeUser =
                            new LoggedInUser(
                                    java.util.UUID.randomUUID().toString(),
                                    "---");
                    return new Result.Success<>(fakeUser);
                }else{
                    return new Result.Error(new IOException("账号或密码错误"));
                }

            }else{
                return new Result.Error(new IOException("无该账号"));
            }

            // TODO: handle loggedInUser authentication

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
    void getJsonArrayData(){
        //new Thread(new Runnable(){
        //@Override
        //public void run() {
        try {
            JSONArray jdata = getData.getData("User","");//此处不需要按条件查询，返回全表信息即可
            jsonArray = jdata;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}
        //}).start();
    };
    void setArrayData(JSONArray jdata){
        try{
            for (int i=0;i<jdata.length();i++) {
                JSONObject jobject = jdata.getJSONObject(i);
                usernames[i]=jobject.getString("UserName");
                userpwds[i]=jobject.getString("UserLoginPwd");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}