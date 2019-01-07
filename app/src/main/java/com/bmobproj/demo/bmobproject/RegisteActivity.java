package com.bmobproj.demo.bmobproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegisteActivity extends AppCompatActivity {
    private ImageView reg_Icon;
    private EditText reg_name,reg_pwd,reg_pwd2;
    private Button reg_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        reg_Icon=findViewById(R.id.reg_icon);
        reg_name=findViewById(R.id.reg_name);
        reg_pwd=findViewById(R.id.reg_pwd);
        reg_btn=findViewById(R.id.reg_btn);
        reg_pwd2=findViewById(R.id.reg_pwd2);
        reg_Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        });
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reg_pwd.getText().toString().equals(reg_pwd2.getText().toString())){
                    User user=new User();
                    user.setUsername(reg_name.getText().toString());
                    user.setPassword(reg_pwd.getText().toString());
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(final User user, BmobException e) {
                            if (e==null){
                                final BmobFile bmobFile=new BmobFile(new File(getExternalCacheDir()+"/userIcon"));
                                bmobFile.uploadblock(new UploadFileListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        user.setUserIcon(bmobFile);
                                        user.update(user.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e!=null){
                                                    Toast.makeText(RegisteActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                                Toast.makeText(RegisteActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),UserMain.class));
                                finish();
                            } else {
                                Toast.makeText(RegisteActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisteActivity.this, "两次输入的密码不一样，请检查您的密码", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        ContentResolver cr=getContentResolver();
        try {
            Bitmap bitmap=BitmapFactory.decodeStream(cr.openInputStream(uri));
            reg_Icon.setImageBitmap(bitmap);
            File file=new File(getExternalCacheDir().toString(),"userIcon");
            FileOutputStream fos=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "没有选择文件", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "文件保存失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
