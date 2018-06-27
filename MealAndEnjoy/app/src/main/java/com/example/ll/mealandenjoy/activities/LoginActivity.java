package com.example.ll.mealandenjoy.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ll.mealandenjoy.R;
import com.google.gson.Gson;

import java.io.IOException;

import entity.User;
import entity.UserApplication;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;


public class LoginActivity extends AppCompatActivity {
    //定义用户名和密码控件属性
    private EditText edtUserName;
    private EditText edtPassword;
    private TextView tvInfo;
    private Button btnLogin;
    private ImageButton imgbtn_rtn;
    private String userName;
    private String password;
    private TextView login_tx;
    //自定义一个Handler类
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //处理消息，做相应的操作
            switch (msg.what) {
                //获取消息内容  msg.arg1   msg.arg2   msg.getData()
                case 1:
                    //如果用户名或密码为空，提示
                   /* tvInfo.setText("用户名或密码不能为空");*/
                    Toast.makeText(getApplicationContext(),"用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //如果用户名或密码不正确，提示
                    /*tvInfo.setText("您输入的用户名或密码不正确");*/
                    Toast.makeText(getApplicationContext(),"您输入的用户名或密码不正确", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    //如果用户名或密码正确，跳转到另一个页面
                    Bundle b=msg.getData();
                    User user=(User)b.getSerializable("user");
                    UserApplication userApplication=(UserApplication) getApplicationContext();
                    userApplication.setUser(user);
                    Intent intent=new Intent();
                    intent.setClass(getApplicationContext(),MainActivity.class);
                    /*intent.putExtras(b);*/
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    //定义OKHTTPClient对象
    private OkHttpClient okHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_login);
        //获取用户名,密码和登录按钮控件
        edtUserName=findViewById(R.id.username);
        edtPassword=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btn_login);
        tvInfo=findViewById(R.id.new_user);
        imgbtn_rtn = findViewById(R.id.imgbtn_rtn);
        //获取控件里面的内容
        userName=edtUserName.getText().toString();
        password=edtPassword.getText().toString();
        login_tx = findViewById(R.id.login);
        /*AssetManager mgr = getAssets();
        Typeface tf = Typeface.createFromAsset(mgr,"fonts/bmjy.ttf");
        login_tx.setTypeface(tf);*/
        //初始化OKHTTPClient对象
        okHttpClient = new OkHttpClient();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //2. 创建Request请求对象
                // 构造向服务器提交的键值对数据信息
                new Thread() {
                    @Override
                    public void run() {
                        FormBody.Builder formBuilder = new FormBody.Builder();
                        //添加键值对数据
                        formBuilder.add("userName", edtUserName.getText().toString());
                        formBuilder.add("password", edtPassword.getText().toString());
                        FormBody formBody = formBuilder.build();
                        //创建Request请求对象
                        Request request = new Request.Builder()
                                .url("http://"+ip+":8080/MealAndEnjoyServer/user/logintext.action")
                                .post(formBody)
                                .build();
                        //3. 创建用于提交请求的Call对象
                        Call call = okHttpClient.newCall(request);
                        //4. 提交请求并获取服务器返回的响应信息（Response对象封装）
                        try {
                            Response response=call.execute();
                            String str = response.body().string();
                            Log.i("ceshi",str);
                            Message message=handler.obtainMessage();
                            if(str.equals("用户名或密码不能为空")){
                                message.what=1;
                                Log.i("hh","第一种情况");
                            }else if(str.equals("您输入的用户名或密码不正确")){
                                message.what=2;
                            }else{
                                Gson gson=new Gson();
                                User user=gson.fromJson(str,User.class);
                                Bundle b=new Bundle();
                                b.putSerializable("user",user);
                                message.setData(b);
                                message.what=3;
                            }
                            //利用Handler对象发送消息
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        /*call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i("yyh", "请求失败");
                                Log.i("yyyyy",e.toString());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                        *//*Log.i("yyh", response.body().string());*//*
                                String str = response.body().string();
                                Message message=handler.obtainMessage();
                                if(str == "用户名或密码不能为空"){
                                    message.what=1;
                                    Log.i("hh","第一种情况");
                                }else if(str == "您输入的用户名或密码不正确"){
                                    message.what=2;
                                }
                                //利用Handler对象发送消息
                                handler.sendMessage(message);
                            }
                        }*/
                    }

                }.start();

            }
        });
     tvInfo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent();
             intent.setClass(LoginActivity.this,RegisterActivity.class);
             startActivity(intent);
         }
     });
        imgbtn_rtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //POST请求方式提交键值对数据给服务器
    /*private void mapDataRequestForPost() {


    }*/

    //点击空白处隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
