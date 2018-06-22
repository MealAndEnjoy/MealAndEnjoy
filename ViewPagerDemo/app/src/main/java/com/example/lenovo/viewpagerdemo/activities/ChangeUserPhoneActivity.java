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

public class ChangeUserPhoneActivity extends AppCompatActivity {
    private EditText edtUserPhone;
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
                    //如果，提示
                    Toast.makeText(getApplicationContext(),"未修改成功，请稍后再试~",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //如果未修改成功手机号码修改成功，
                    Bundle b=msg.getData();
                    Intent intent=new Intent();
                    UserApplication userApplication=(UserApplication) getApplicationContext();
                    userApplication.setUser((User)b.getSerializable("user"));
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
        setContentView(R.layout.activity_change_user_phone);
        //获取控件
        edtUserPhone=findViewById(R.id.edt_phone);
        btnSubmit=findViewById(R.id.btn_submit);
        //初始化OKHTTPClient对象
        okHttpClient = new OkHttpClient();
        //获取传输的user对象
        UserApplication userApplication= (UserApplication)getApplicationContext();
        final User user=userApplication.getUser();
        //为控件赋值（phone）
        if(user != null) {
            edtUserPhone.setText(user.getPhone());
        }
        //为确定按钮设置点击事件监听器
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先获取输入的手机号码，判断手机号码是否合法
                /*  判断所输入手机号码格式是否正确
                   移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
                   联通：130、131、132、152、155、156、185、186
                   电信：133、153、180、189、（1349卫通）
                   总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9*/
                String phone=edtUserPhone.getText().toString();
                String telRegex = "^[1][3,5,8][0-9]{9}$";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"[0-9]{9}"代表后面是可以是0～9的数字，有9位。
                if(phone.matches(telRegex) && phone.length() == 11){
                    //2. 创建Request请求对象
                    // 构造向服务器提交的键值对数据信息
                    new Thread() {
                        @Override
                        public void run() {
                            FormBody.Builder formBuilder = new FormBody.Builder();
                            //添加键值对数据
                            formBuilder.add("username", user.getUsername());
                            formBuilder.add("changePhone", edtUserPhone.getText().toString());
                            FormBody formBody = formBuilder.build();
                            //创建Request请求对象
                            Request request = new Request.Builder()
                                    .url("http://"+ip+":8080/demo001/user/changePhone.action")
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
                    Toast.makeText(getApplicationContext(),"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
