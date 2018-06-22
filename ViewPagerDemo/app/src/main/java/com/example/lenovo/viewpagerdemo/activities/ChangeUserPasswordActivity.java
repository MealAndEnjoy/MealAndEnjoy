package com.example.lenovo.viewpagerdemo.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.viewpagerdemo.R;
import com.example.lenovo.viewpagerdemo.entity.User;
import com.example.lenovo.viewpagerdemo.entity.UserApplication;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.lenovo.viewpagerdemo.fragment.Home_Fragment.ip;

public class ChangeUserPasswordActivity extends AppCompatActivity {
    private EditText edtPassword;
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
                    //如果
                    Toast.makeText(getApplicationContext(),"未修改成功，请稍后再试~",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //如果密码修改成功，
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
        setContentView(R.layout.activity_change_user_password);
        //获取控件
        edtPassword=findViewById(R.id.edt_password);
        btnSubmit=findViewById(R.id.btn_submit);
        //初始化OKHTTPClient对象
        okHttpClient = new OkHttpClient();
        //获取传输的user对象
        UserApplication userApplication= (UserApplication)getApplicationContext();
        final User user=userApplication.getUser();
        //为控件赋值（password）
        if(user != null) {
            edtPassword.setText(user.getPassword());
        }
        //为确定按钮设置点击事件监听器
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断输入的密码是不是少于六位
                if(edtPassword.getText().toString().length()>= 6){
                    //2. 创建Request请求对象
                    // 构造向服务器提交的键值对数据信息
                    new Thread() {
                        @Override
                        public void run() {
                            FormBody.Builder formBuilder = new FormBody.Builder();
                            //添加键值对数据
                            formBuilder.add("username", user.getUsername());
                            formBuilder.add("changePassword", edtPassword.getText().toString());
                            FormBody formBody = formBuilder.build();
                            //创建Request请求对象
                            Request request = new Request.Builder()
                                    .url("http://"+ip+":8080/demo001/user/changePassword.action")
                                    .post(formBody)
                                    .build();
                            //3. 创建用于提交请求的Call对象
                            Call call = okHttpClient.newCall(request);
                            //4. 提交请求并获取服务器返回的响应信息（Response对象封装）
                            try {
                                Response response=call.execute();
                                Message message=handler.obtainMessage();
                                String str = response.body().string();
                                if(str.equals("未修改成功，请稍后再试~")){
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
                }else{
                    Toast.makeText(getApplicationContext(),"密码必须六位或六位以上",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
