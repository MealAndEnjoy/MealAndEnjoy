package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class ChangeUsernameActivity extends AppCompatActivity {
    private EditText edtUsername;
    private Button btnSubmit;
    //定义OKHTTPClient对象
    private OkHttpClient okHttpClient;
    //自定义一个Handler类
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //处理消息，做相应的操作
            switch (msg.what) {
                //获取消息内容  msg.arg1   msg.arg2   msg.getData()
                case 1:
                    //如果已存在该用户名，提示
                    Toast.makeText(getApplicationContext(),"该用户名已存在，请换一个吧~", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //如果用户名或密码正确，跳转到另一个页面
                    Bundle b=msg.getData();
                    UserApplication userApplication=(UserApplication) getApplicationContext();
                    userApplication.setUser((User)b.getSerializable("user"));
                    Intent intent=new Intent();
                    intent.setClass(getApplicationContext(),PersonalInfoActivity.class);
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
        //获取控件
        edtUsername=findViewById(R.id.edt_username);
        btnSubmit=findViewById(R.id.btn_submit);
        //初始化OKHTTPClient对象
        okHttpClient = new OkHttpClient();
        //获取传输的user对象
        UserApplication userApplication= (UserApplication)getApplicationContext();
        final User user=userApplication.getUser();
        //为控件赋值
        if(user != null) {
            edtUsername.setText(user.getUsername());
        }
        //为确定控件添加点击事件监听器，请求服务器，更新数据库username信息
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断是否输入的用户名为空
                if(edtUsername.getText().toString()==null || edtUsername.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"用户名不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    //2. 创建Request请求对象
                    // 构造向服务器提交的键值对数据信息
                    new Thread() {
                        @Override
                        public void run() {
                            FormBody.Builder formBuilder = new FormBody.Builder();
                            //添加键值对数据
                            formBuilder.add("priUsername", user.getUsername());
                            formBuilder.add("changeUsername", edtUsername.getText().toString());
                            FormBody formBody = formBuilder.build();
                            //创建Request请求对象
                            Request request = new Request.Builder()
                                    .url("http://"+ip+":8080/demo001/user/changeUsername.action")
                                    .post(formBody)
                                    .build();
                            //3. 创建用于提交请求的Call对象
                            Call call = okHttpClient.newCall(request);
                            //4. 提交请求并获取服务器返回的响应信息（Response对象封装）
                            try {
                                Response response=call.execute();
                                Message message=handler.obtainMessage();
                                String str = response.body().string();
                                if(str.equals("该用户名已存在，请换一个吧~")){
                                    message.what=1;
                                    Log.i("yyh","第一种情况");
                                }else{
                                    Gson gson=new Gson();
                                    User user1=gson.fromJson(str,User.class);
                                    Bundle bundle1=new Bundle();
                                    bundle1.putSerializable("user",user1);
                                    message.setData(bundle1);
                                    message.what=2;
                                }
                                //利用Handler对象发送消息
                                handler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }.start();
                }

            }
        });
    }
}
