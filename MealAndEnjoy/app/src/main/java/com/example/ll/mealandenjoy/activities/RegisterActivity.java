package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.ll.mealandenjoy.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;


public class RegisterActivity extends AppCompatActivity {
    private EditText edt_username;
    private EditText edt_password;
    private EditText edt_repassword;
    private EditText edt_phone;
    private Button btn_register;
    private Thread thread;
    //定义OkHttpClient对象
    private OkHttpClient okHttpClient;
    //定义handler
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(getApplicationContext(),
                            "用户名/密码/手机号不能为空",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(),
                            "您输入的密码长度必须大于6个字符",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(),
                            "两次密码输入不一致，请重新输入",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(),
                            "亲，您所输入的手机号码格式有误哦~",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
                case 5:
                    Toast.makeText(getApplicationContext(),
                            "该用户名已被注册",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
                case 6:
                    //页面跳转
                    //1. 创建Intent对象
                    Intent intent = new Intent();
                    //2. 指定跳转路线
                    intent.setClass(RegisterActivity.this,
                            LoginActivity.class);
                    //3. 进行跳转
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化OkHttpClient对象
        okHttpClient = new OkHttpClient();

        //获取布局文件中的控件对象
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        edt_repassword = findViewById(R.id.edt_repassword);
        btn_register = findViewById(R.id.btn_register);
        edt_phone = findViewById(R.id.edt_phone);

        //为按钮注册事件监听器
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动线程
                thread = new Register();
                thread.start();
            }
        });
    }
    //创建一个子线程实现向服务器网络传输数据
    class Register extends Thread {
        @Override
        public void run() {
            //构建Message对象
            final Message message = Message.obtain();

            //获取布局控件内输入的文本信息
            // replaceAll() 方法使用给定的参数 replacement 替换字符串所有匹配给定的正则表达式的子字符串。 在此去掉字符串中所有空格。
            final String username = edt_username.getText().toString().replaceAll(" ", "");
            final String password = edt_password.getText().toString().replaceAll(" ", "");
            final String repassword = edt_repassword.getText().toString().replaceAll(" ", "");
            final String phone = edt_phone.getText().toString().replaceAll(" ", "");

            //向服务器传输数据前判断用户名、密码、手机号码是否为空
            if (TextUtils.isEmpty(repassword) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username) || TextUtils.isEmpty(phone)) {
                //包装Message对象,本质就是向Message对象中添加数据
                //Message对象有3个属性（int）：arg1，arg2，what
                //Message的方法：setData(Bundlle对象), getData()
                message.what = 1;
                handler.sendMessage(message);
            } else {
                //定义密码长度必须大于六个字符
                if (password.length() < 6) {
                    message.what = 2;
                    handler.sendMessage(message);
                } else {
                   /*  判断所输入手机号码格式是否正确
                   移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
                   联通：130、131、132、152、155、156、185、186
                   电信：133、153、180、189、（1349卫通）
                   总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9*/
                    String telRegex = "^[1][3,5,8][0-9]{9}$";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"[0-9]{9}"代表后面是可以是0～9的数字，有9位。
                    if(phone.matches(telRegex) && phone.length() == 11){
                        //判断两次密码输入是否一致
                        if(password.equals(repassword)){
                            //POST请求，带有封装请求参数的请求方式
                            // 构造向服务器提交的键值对数据信息
                            FormBody.Builder formBuilder = new FormBody.Builder();
                            //添加键值对数据
                            formBuilder.add("username", username);
                            formBuilder.add("password", password);
                            formBuilder.add("phone", phone);
                            FormBody formBody = formBuilder.build();
                            //创建Request请求对象
                            Request request = new Request.Builder()
                                    .url("http://"+ip+":8080/demo001/user/register.action")
                                    .post(formBody)
                                    .build();
                            //3. 创建用于提交请求的Call对象
                            Call call = okHttpClient.newCall(request);
                            //4. 提交异步请求，并获取响应数据
                            call.enqueue(new Callback() {
                                //当请求失败时被回调
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.i("sjy", "请求失败");
                                    Log.i("sjy", e.toString());
                                }
                                //当请求成功时被回调
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    //获取请求返回的响应信息
                                    if (response.body().string().equals("0")){
                                        message.what = 5;
                                        handler.sendMessage(message);
                                    }else{
                                        message.what = 6;
                                        handler.sendMessage(message);
                                        Log.i("sjy","注册成功");
                                    }
                                }
                            });
                        }else{
                            message.what = 3;
                            handler.sendMessage(message);
                        }
                    }else{
                        message.what = 4;
                        handler.sendMessage(message);
                    }
                }
            }
            handler.removeCallbacks(thread);
        }
    }
}

