package com.bmobproj.demo.bmobproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "6efee115cb0d6e46cedd5a556ae2b9a0");
        requestPermissions(Init().toArray(new String[Init().size()]),1);
        if (BmobUser.isLogin()){
            startActivity(new Intent(this,UserMain.class));
            finish();
        } else {

        }
    }
    private List<String> Init(){
        List<String> list=new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            list.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        return list;
    }
}
