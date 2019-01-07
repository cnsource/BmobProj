package com.bmobproj.demo.bmobproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {
private EditText user_name,user_pwd;
private Button user_login_btn;
private TextView user_registe,user_lost_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_registe=findViewById(R.id.user_registe);
        user_lost_pwd=findViewById(R.id.user_lostpwd);
        user_name=findViewById(R.id.user_name);
        user_pwd=findViewById(R.id.user_pwd);
        user_login_btn=findViewById(R.id.user_login_btn);
        user_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=new User();
                user.setUsername(user_name.getText().toString());
                user.setPassword(user_pwd.getText().toString());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e==null){
                            startActivity(new Intent(getApplicationContext(),UserMain.class));
                        } else
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        user_registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisteActivity.class));
            }
        });
    }
}
