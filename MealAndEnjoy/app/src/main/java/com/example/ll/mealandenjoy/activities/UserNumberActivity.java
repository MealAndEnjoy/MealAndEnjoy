package com.example.ll.mealandenjoy.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ll.mealandenjoy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import adapters.UserNumAdapter;
import entity.NumDemo;
import entity.User;
import entity.UserApplication;
import fragment.Mine_Fragment;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;

public class UserNumberActivity extends AppCompatActivity {
    private ListView lvUserNum;
    private ImageButton imgbtnRetn;
    //定义OKHTTPClient对象
    private OkHttpClient okHttpClient;
    //自定义一个Handler类
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //处理消息，做相应的操作
            switch (msg.what) {
                //获取消息内容  msg.arg1   msg.arg2   msg.getData()
                case 1:
                    Toast.makeText(getApplicationContext(),"未删除成功,请稍候再试~",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //将userNumList传到号码里面

                    Bundle b1 = msg.getData();
                    List<NumDemo> userNumList = (List<NumDemo>)b1.getSerializable("userNumList");
                    Gson gson = new Gson();
                    String demo = gson.toJson(userNumList);
                    Log.i("ceshi",demo);
                    UserNumAdapter userNumAdapter=new UserNumAdapter(getApplicationContext(),R.layout.user_num_item,userNumList);
                    lvUserNum.setAdapter(userNumAdapter);
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(),"您当前无取号",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_number);
        lvUserNum=findViewById(R.id.lv_user_num);
        imgbtnRetn=findViewById(R.id.imgbtn_retn);
        //初始化OKHTTPClient对象
        okHttpClient = new OkHttpClient();
        //获取传过来的numList对象
        final Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        final List<NumDemo> userNumList=(List<NumDemo>) bundle.getSerializable("userNumList");

        //为退出当前页面按钮设置点击事件监听器
        imgbtnRetn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*UserApplication userApplication= (UserApplication)getApplicationContext();
                final User user=userApplication.getUser();
                Intent intent1 = new Intent();
                intent1.putExtra("个人界面",2);
                intent1.setClass(UserNumberActivity.this,MainActivity.class);*/
                //获取fragment的实例
               /* Fragment fragment=new Fragment();
                //获取Fragment的管理器
                FragmentManager fragmentManager=getFragmentManager();
                //开启fragment的事物,在这个对象里进行fragment的增删替换等操作。
                FragmentTransaction ft=fragmentManager.beginTransaction();
                //跳转到fragment，第一个参数为所要替换的位置id，第二个参数是替换后的fragment
                ft.replace(R.layout.activity_main,Mine_Fragment.class);
                //提交事物
                ft.commit();*/
               finish();
                /*startActivity(intent1);*/
            }
        });
        UserNumAdapter userNumAdapter=new UserNumAdapter(this,R.layout.user_num_item,userNumList);
        lvUserNum.setAdapter(userNumAdapter);
        lvUserNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserApplication userApplication= (UserApplication)getApplicationContext();
                final User user=userApplication.getUser();
                NumDemo numDemo=userNumList.get(position);
                final int num=numDemo.getMyNum();
                AlertDialog.Builder dialog = new AlertDialog.Builder(UserNumberActivity.this);
                dialog.setTitle("确认对话框");
                dialog.setMessage("是否要删除/取消");
                dialog.setPositiveButton("确认",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        new Thread() {
                            @Override
                            public void run() {
                                FormBody.Builder formBuilder = new FormBody.Builder();
                                //添加键值对数据
                                formBuilder.add("userId", user.getUserId() + "");
                                formBuilder.add("numId", num + "");
                                FormBody formBody = formBuilder.build();
                                //创建Request请求对象
                                Request request = new Request.Builder()
                                        .url("http://"+ip+":8080/MealAndEnjoyServer/user/deleteusernum.action")
                                        .post(formBody)
                                        .build();
                                //3. 创建用于提交请求的Call对象
                                Call call = okHttpClient.newCall(request);
                                //4. 提交请求并获取服务器返回的响应信息（Response对象封装）
                                try {
                                    Response response = call.execute();
                                    String str = response.body().string();
                                    Log.i("ceshi", str);
                                    Message message = handler.obtainMessage();
                                    if (str.equals("未删除成功,请稍候再试~")){
                                        message.what=1;
                                    }else if(str.equals("您当前无取号")){
                                        message.what=3;
                                    }
                                    else{
                                        Gson gson = new Gson();
                                        Type UserNumList = new TypeToken<List<NumDemo>>() {
                                        }.getType();
                                        List<NumDemo> userNumList = gson.fromJson(str, UserNumList);
                                        Bundle b = new Bundle();
                                        b.putSerializable("userNumList", (Serializable) userNumList);
                                        message.setData(b);
                                        message.what = 2;
                                    }

                                    //利用Handler对象发送消息
                                    handler.sendMessage(message);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                        }.start();
                    }
                });
                dialog.setNegativeButton("取消", null);
                dialog.create();
                dialog.show();
            }
        });

    }
}
