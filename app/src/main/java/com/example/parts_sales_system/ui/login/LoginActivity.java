package com.example.parts_sales_system.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parts_sales_system.data.Result;
import com.example.parts_sales_system.data.api_connection.getData;
import com.example.parts_sales_system.data.model.LoggedInUser;
import com.example.parts_sales_system.public_MainActivity;
import com.example.parts_sales_system.R;
import com.example.parts_sales_system.ui.login.LoginViewModel;
import com.example.parts_sales_system.ui.login.LoginViewModelFactory;
import com.example.parts_sales_system.databinding.ActivityLoginBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private JSONArray jsonArray;
    String[] usernames,userpwds=new String[20];

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());//实例化布局文件
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final Button toregisterButton = binding.register;
        final ProgressBar loadingProgressBar = binding.loading;
        final Intent[] intent = new Intent[1];

        getJsonArrayData();


        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;

                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setArrayData(jsonArray);
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());

                //Toast.makeText(getApplicationContext(), userpwds[1], Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), usernameEditText.getText().toString(), Toast.LENGTH_LONG).show();
                //List<String> list = Arrays.asList(usernames);

                //Toast.makeText(getApplicationContext(), list.indexOf(usernameEditText.getText().toString()), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), userpwds[list.indexOf(usernameEditText.getText().toString())], Toast.LENGTH_LONG).show();
                //if (list.contains(usernameEditText.getText().toString())) {
                    //if (passwordEditText.getText().toString().equals(userpwds[list.indexOf(usernameEditText.getText().toString())])) {
                        intent[0] = new Intent(LoginActivity.this, public_MainActivity.class);
                        startActivity(intent[0]);
                    //}
                //}else{
                    //Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_LONG).show();
                }
            //}
        });
        toregisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
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
            JSONArray jdata = getData.getData("User","");//此处不需要按条件查询，返回全表信息即可
            jsonArray = jdata;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "UserLoginPwd", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        }
        }).start();
    };
    void setArrayData(JSONArray jdata){
        try{
            for (int i=0;i<jdata.length();i++) {
                JSONObject jobject = jdata.getJSONObject(i);
                Toast.makeText(getApplicationContext(), jobject.getString("UserLoginPwd"), Toast.LENGTH_LONG).show();
                usernames[i]=jobject.getString("UserName");
                userpwds[i]=jobject.getString("UserLoginPwd");
            }
            System.out.print(usernames);
        }catch (Exception e) {

            e.printStackTrace();
        }
    }
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}