package com.example.lenovo.viewpagerdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lenovo.viewpagerdemo.R;
import com.example.lenovo.viewpagerdemo.entity.User;
import com.example.lenovo.viewpagerdemo.entity.UserApplication;

public class PersonalInfoActivity extends AppCompatActivity {
    private User user;
    private TextView tvName;
    private TextView tvPhone;
    private ImageButton imgbtn2;
    private ImageButton imgbtn3;
    private ImageButton imgbtn4;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        //获取控件对象
        tvName=findViewById(R.id.tv_uname);
        tvPhone=findViewById( R.id.tv_uphone);
        imgbtn2=findViewById(R.id.imgbtn_r2);
        imgbtn3=findViewById(R.id.imgbtn_r3);
        imgbtn4=findViewById(R.id.imgbtn_r4);
        //接收传输数据
        UserApplication userApplication= (UserApplication)getApplicationContext();
        final User user=userApplication.getUser();
        final Intent intent=new Intent();
        if(user != null) {
            tvName.setText(user.getUsername());
            tvPhone.setText(user.getPhone());
        }
        //为控件设置点击事件监听器
        imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getApplicationContext(),ChangeUsernameActivity.class);
                startActivity(intent);
            }
        });
        imgbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getApplicationContext(),ChangeUserPhoneActivity.class);
                startActivity(intent);
            }
        });
        imgbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getApplicationContext(),ChangeUserPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
