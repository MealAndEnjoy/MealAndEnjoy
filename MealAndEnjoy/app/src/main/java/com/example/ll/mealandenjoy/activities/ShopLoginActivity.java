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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ll.mealandenjoy.R;
import com.google.gson.Gson;

import java.io.IOException;

import entity.Shop;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;


public class ShopLoginActivity extends AppCompatActivity {
    private ImageButton imgbtnRtn;
    private EditText edtShopUsername;
    private EditText edtShoppassword;
    private Button btnShopLogin;
    //定义OkHttpClient对象
    private OkHttpClient okHttpClient;
    //自定义一个Handler类
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //处理消息，做相应的操作
            switch (msg.what) {
                //获取消息内容  msg.arg1   msg.arg2   msg.getData()
                case 1:
                    //如果用户名或密码不正确，提示
                   /* tvInfo.setText("用户名或密码");*/
                    Toast.makeText(getApplicationContext(),"用户名或密码不正确", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //如果用户名或密码正确并是商店用户，跳转到另一个页面
                    Toast.makeText(getApplicationContext(),"登录成功", Toast.LENGTH_SHORT).show();
                    Bundle b=msg.getData();
                    Intent intent=new Intent();
                    intent.setClass(getApplicationContext(),ShopMainActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;

            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_login);
        //获取控件
        imgbtnRtn=findViewById(R.id.imgbtn_rtn);
        edtShopUsername=findViewById(R.id.edt_shop_username);
        edtShoppassword=findViewById(R.id.edt_shop_password);
        btnShopLogin=findViewById(R.id.btn_shop_login);
        //初始化OKHTTPClient对象
        okHttpClient = new OkHttpClient();
        //为登录按钮注册点击事件监听器
        btnShopLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String shopUsername=edtShopUsername.getText().toString();
                final String shopPassword=edtShoppassword.getText().toString();
                if (shopUsername == null || shopUsername.equals("")){
                    Toast.makeText(getApplicationContext(),"用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if (shopPassword == null || shopPassword.equals("")){
                    Toast.makeText(getApplicationContext(),"密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    //2. 创建Request请求对象
                    // 构造向服务器提交的键值对数据信息
                    new Thread() {
                        @Override
                        public void run() {
                            FormBody.Builder formBuilder = new FormBody.Builder();
                            //添加键值对数据
                            formBuilder.add("shopUsername", shopUsername);
                            formBuilder.add("shopPassword",shopPassword);
                            FormBody formBody = formBuilder.build();
                            //创建Request请求对象
                            Request request = new Request.Builder()
                                    .url("http://"+ip+":8080/demo001/user/shoplogin.action")
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
                                if(str.equals("用户名或密码不正确")){
                                    message.what=1;
                                    Log.i("hh","第一种情况");
                                }else{
                                    Gson gson=new Gson();
                                    Shop shop=gson.fromJson(str, Shop.class);
                                    Bundle b=new Bundle();
                                    b.putSerializable("shop",shop);
                                    message.setData(b);
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
        //为返回按钮注册点击事件监听器
        imgbtnRtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
    }
}
