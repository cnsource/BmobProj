package com.bmobproj.demo.bmobproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {
    public static MainActivity minstence=null;
private   List<String> permissionsList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        minstence=this;
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "6efee115cb0d6e46cedd5a556ae2b9a0");
        //需要申请的权限列表
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //判断版本号是否大于23
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) //判断该权限是否获得授权
                permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permissionsList.size() == 0) {

            } else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        1); //进行权限申请
            }
        }
        if (BmobUser.isLogin()){
            startActivity(new Intent(getApplicationContext(),UserMain.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
