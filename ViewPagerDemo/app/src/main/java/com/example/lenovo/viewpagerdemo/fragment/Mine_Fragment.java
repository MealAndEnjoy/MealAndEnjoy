package com.example.lenovo.viewpagerdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.viewpagerdemo.R;
import com.example.lenovo.viewpagerdemo.activities.LoginActivity;
import com.example.lenovo.viewpagerdemo.activities.RegisterActivity;
import com.example.lenovo.viewpagerdemo.entity.User;

/**
 * Created by lenovo on 2018/5/23.
 */

public class Mine_Fragment extends Fragment {
    private Button btnRegister;
    private Button btnLogin;
    private TextView tvInfo;
    private TextView tvUserInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //加载选项卡对应的选项页面
        View view = inflater.inflate(R.layout.activity_mine,container,false);
        //获取布局文件中控件对象
        btnRegister=view.findViewById(R.id.bt_2);
        btnLogin=view.findViewById(R.id.bt_1);
        tvInfo=view.findViewById(R.id.tv_info);
        tvUserInfo=view.findViewById(R.id.tv_user_info);
        tvUserInfo.setVisibility(View.GONE);
        //为注册按钮添加点击事件监听器
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), RegisterActivity.class);
                //进行跳转
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                //进行跳转
                startActivity(intent);
            }
        });
        Intent intent=getActivity().getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle != null){
            User user=(User)bundle.getSerializable("user");
            btnRegister.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
            tvInfo.setVisibility(View.GONE);
            tvUserInfo.setText(user.getUserName());
            tvUserInfo.setVisibility(View.VISIBLE);
            /*btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"您已登录",Toast.LENGTH_SHORT).show();
                }
            });*/
        }
        //返回选项卡对应的选项页面
        return view;
    }
}
