package com.example.lenovo.viewpagerdemo.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.viewpagerdemo.CommentList;
import com.example.lenovo.viewpagerdemo.adapters.CustomComAdapter;
import com.example.lenovo.viewpagerdemo.R;
import com.example.lenovo.viewpagerdemo.entity.Num;
import com.example.lenovo.viewpagerdemo.entity.ShopDemo;
import com.example.lenovo.viewpagerdemo.entity.User;
import com.example.lenovo.viewpagerdemo.entity.UserApplication;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.lenovo.viewpagerdemo.fragment.Home_Fragment.ip;

public class ShopDetailsActivity extends AppCompatActivity {
    private ImageView btn_call;
    private ListView listView;
    private TextView shopTitle;
    private ImageView shopCover;
    private TextView shopAddress;
    private Button btn_getNum1;
    private Button btn_getNum2;
    private Button btn_getNum3;
    private TextView little_num;
    private TextView middle_num;
    private TextView large_num;
    private Thread thread;
    private OkHttpClient ok;
    private int shopId ;
    private Button btn_cancelNum1;
    private Button btn_cancelNum2;
    private Button btn_cancelNum3;
     //针对即使获取了拨打电话的权限依然报错问题的解决方案
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle b = msg.getData();
                    Num nm = (Num)b.getSerializable("num");
                    int qNum = b.getInt("qName");
                    String qq = String.valueOf(qNum);
                    Log.i("ceshi",qq+"判断序号");
                    little_num.setText(String.valueOf(nm.getLittleNum()));
                    middle_num.setText(String.valueOf(nm.getMiddleNum()));
                    large_num.setText(String.valueOf(nm.getLargeNum()));
                    if (qNum == 1){
                        btn_cancelNum1.setVisibility(View.VISIBLE);
                        btn_getNum2.setVisibility(View.GONE);
                        btn_getNum3.setVisibility(View.GONE);
                    }else if (qNum == 2){
                        btn_cancelNum2.setVisibility(View.VISIBLE);
                        btn_getNum1.setVisibility(View.GONE);
                        btn_getNum3.setVisibility(View.GONE);
                    }else if(qNum == 3){
                        btn_cancelNum3.setVisibility(View.VISIBLE);
                        btn_getNum2.setVisibility(View.GONE);
                        btn_getNum1.setVisibility(View.GONE);
                    }else{

                    }

                    btn_getNum1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            thread = new getLittle();
                            thread.start();
                            btn_cancelNum1.setVisibility(View.VISIBLE);
                            btn_getNum2.setVisibility(View.GONE);
                            btn_getNum3.setVisibility(View.GONE);
                            btn_getNum1.setVisibility(View.GONE);
                        }
                    });
                    btn_getNum2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            thread = new getMiddle();
                            thread.start();
                            btn_cancelNum2.setVisibility(View.VISIBLE);
                            btn_getNum1.setVisibility(View.GONE);
                            btn_getNum3.setVisibility(View.GONE);
                            btn_getNum2.setVisibility(View.GONE);
                        }
                    });
                    btn_getNum3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            thread = new getLarge();
                            thread.start();
                            btn_cancelNum3.setVisibility(View.VISIBLE);
                            btn_getNum1.setVisibility(View.GONE);
                            btn_getNum2.setVisibility(View.GONE);
                            btn_getNum3.setVisibility(View.GONE);
                        }
                    });
                    //取消排号
                    btn_cancelNum1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            thread = new cancelNum();
                            thread.start();
                            btn_cancelNum1.setVisibility(View.GONE);
                            btn_getNum1.setVisibility(View.VISIBLE);
                            btn_getNum2.setVisibility(View.VISIBLE);
                            btn_getNum3.setVisibility(View.VISIBLE);
                        }
                    });
                    btn_cancelNum2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            thread = new cancelNum();
                            thread.start();
                            btn_cancelNum2.setVisibility(View.GONE);
                            btn_getNum1.setVisibility(View.VISIBLE);
                            btn_getNum2.setVisibility(View.VISIBLE);
                            btn_getNum3.setVisibility(View.VISIBLE);
                        }
                    });
                    btn_cancelNum3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            thread = new cancelNum();
                            thread.start();
                            btn_cancelNum3.setVisibility(View.GONE);
                            btn_getNum1.setVisibility(View.VISIBLE);
                            btn_getNum2.setVisibility(View.VISIBLE);
                            btn_getNum3.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                case 2:
                    thread = new MyThread();
                    thread.start();
                   break;
                case 3:
                    thread = new MyThread();
                    thread.start();
                    break;
                case 4:
                    thread = new MyThread();
                    thread.start();
                    break;
                case 5:
                    thread = new MyThread();
                    thread.start();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        //获取控件
        getViews();
        //注册事件监听器
        ButtonClickListener listener = new ButtonClickListener();
        btn_call.setOnClickListener(listener);
        btn_getNum1.setOnClickListener(listener);
        btn_getNum2.setOnClickListener(listener);
        btn_getNum3.setOnClickListener(listener);
        shopCover.setOnClickListener(listener);
        //底部评价绑定adapter
        final CustomComAdapter adapter = new CustomComAdapter(this, R.layout.commen_item, prepaerDate());
        listView.setAdapter(adapter);
        //获取listView传来的店铺数据
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ShopDemo shopDemo = (ShopDemo) bundle.getSerializable("shopdemo");
        shopId = shopDemo.getShopdId();
        //设置店铺信息
        shopTitle.setText(shopDemo.getShopdName());
        shopAddress.setText(shopDemo.getAddress());
        Bitmap bm = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath()+shopDemo.getShopimg());
        shopCover.setImageBitmap(bm);
        //服务器传输
        ok = new OkHttpClient();
        //调用线程
        thread = new MyThread();
        thread.start();
    }

    private List<CommentList> prepaerDate() {
        List<CommentList> comList = new ArrayList<>();

        CommentList meituan = new CommentList();
        meituan.setItem_title("查看“美团”评论");
        meituan.setImg_id(R.drawable.arrow);
        comList.add(meituan);

        CommentList dazhong = new CommentList();
        dazhong.setItem_title("查看“大众点评”评论");
        dazhong.setImg_id(R.drawable.arrow);
        comList.add(dazhong);

        return comList;
    }

    //获取布局文件中的控件
    private void getViews() {
        listView = findViewById(R.id.comment_list);
        btn_call = findViewById(R.id.img_phone);
        shopTitle = findViewById(R.id.shop_title);
        shopCover = findViewById(R.id.shop_img);
        shopAddress = findViewById(R.id.txt_address);
        btn_getNum1 = findViewById(R.id.getnum);
        btn_getNum2 = findViewById(R.id.getnum2);
        btn_getNum3 = findViewById(R.id.getnum3);
        little_num = findViewById(R.id.numbe);
        middle_num = findViewById(R.id.numbe2);
        large_num = findViewById(R.id.numbe3);
        btn_cancelNum1 = findViewById(R.id.cancelNum);
        btn_cancelNum2 = findViewById(R.id.cancelNum2);
        btn_cancelNum3 = findViewById(R.id.cancelNum3);
    }

    //按钮点击事件监听器
    class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_phone:
                    if (ContextCompat.checkSelfPermission(ShopDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // 没有获得授权，申请授权
                        if (ActivityCompat.shouldShowRequestPermissionRationale(ShopDetailsActivity.this, Manifest.permission.CALL_PHONE)) {
                        // 返回值：
                        //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                        //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                        //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                        // 弹窗需要解释为何需要该权限，再次请求授权
                            Toast.makeText(ShopDetailsActivity.this, "请授权！", Toast.LENGTH_LONG).show();
                        // 帮跳转到该应用的设置界面，让用户手动授权
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        } else {
                         // 不需要解释为何需要该权限，直接请求授权
                            ActivityCompat.requestPermissions(ShopDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        }
                   } else {
                      // 已经获得授权，可以打电话
                            CallPhone();
                  }
                  break;
                case R.id.shop_img:
                    Intent intent = new Intent();
                    intent.putExtra("shopId",shopId);
                    intent.setClass(ShopDetailsActivity.this,grid_activity.class);
                    startActivity(intent);

            }
        }
    }
    //拨打电话
        private void CallPhone() {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            //url:统一资源定位符
            intent.setData(Uri.parse("tel:" + "15703286237"));
            //开启系统拨号器
            startActivity(intent);
        }
        //获取服务器端排号桌数
        class MyThread extends Thread {
            @Override
            public void run() {
                UserApplication userApplication = (UserApplication) getApplicationContext();
                User user = userApplication.getUser();
                FormBody.Builder formBuilder = new FormBody.Builder();
                //添加键值对数据
                String shopid = String.valueOf(shopId);
                String userid = String.valueOf(user.getUserId());
                formBuilder.add("sId", shopid);
                formBuilder.add("userId",userid);
                FormBody formBody1 = formBuilder.build();
                FormBody formBody2 = formBuilder.build();
                //创建Request请求对象
                Request request1 = new Request.Builder()
                        .url("http://"+ip+":8080/demo001/numberr/getNum.action")
                        .post(formBody1)
                        .build();
                Request request2 = new Request.Builder()
                        .url("http://"+ip+":8080/demo001/numberr/judgeNum.action")
                        .post(formBody2)
                        .build();
                //3. 创建用于提交请求的Call对象
                Call call = ok.newCall(request1);
                Call call2 = ok.newCall(request2);
                //4. 提交异步请求，并获取响应数据
                try {
                    Response response1 = call.execute();
                    Response response2 = call2.execute();
                    Gson gson = new Gson();
                    String nnn = response1.body().string();
                    Num num = gson.fromJson(nnn,Num.class);
                    String qName = response2.body().string();
                    int j = 0;
                    if (qName.equals("小桌")){
                        j = 1 ;
                    }else  if (qName.equals("中桌")){
                        j = 2 ;
                    }else if(qName.equals("大桌")){
                        j = 3 ;
                    }else{
                        j = 4 ;
                    }
                    Message msg = Message.obtain();
                    Bundle b = new Bundle();
                    b.putSerializable("num",num);
                    b.putInt("qName",j);
                    String jj = String.valueOf(j);
                    Log.i("ceshi",jj+"判断桌型");
                    Log.i("ceshi",qName);
                    msg.setData(b);
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    //取号 --- 小桌
    class getLittle extends Thread{
        @Override
        public void run() {
            UserApplication userApplication = (UserApplication) getApplicationContext();
            User user = userApplication.getUser();
            FormBody.Builder formBuilder = new FormBody.Builder();
            //添加键值对数据
            String shopid = String.valueOf(shopId);
            String userid = String.valueOf(user.getUserId());
            String userphone = user.getPhone();
            formBuilder.add("sId", shopid);
            formBuilder.add("userId",userid);
            formBuilder.add("phone",userphone);
            FormBody formBody = formBuilder.build();
            //创建Request请求对象
            Request request = new Request.Builder()
                    .url("http://"+ip+":8080/demo001/numberr/getLittle.action")
                    .post(formBody)
                    .build();
            //3. 创建用于提交请求的Call对象
            Call call = ok.newCall(request);
            //4. 提交异步请求，并获取响应数据
            try {
                Response response = call.execute();
                    Message message = Message.obtain();
                    message.what = 2;
                    handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //取号 --- 中桌
    class getMiddle extends Thread{
        @Override
        public void run() {
            UserApplication userApplication = (UserApplication) getApplicationContext();
            User user = userApplication.getUser();
            FormBody.Builder formBuilder = new FormBody.Builder();
            //添加键值对数据
            String shopid = String.valueOf(shopId);
            String userid = String.valueOf(user.getUserId());
            String userphone = user.getPhone();
            Log.i("ceshi",userid);
            Log.i("ceshi",userphone);
            formBuilder.add("sId", shopid);
            formBuilder.add("userId",userid);
            formBuilder.add("phone",userphone);
            FormBody formBody = formBuilder.build();
            //创建Request请求对象
            Request request = new Request.Builder()
                    .url("http://"+ip+":8080/demo001/numberr/getMiddle.action")
                    .post(formBody)
                    .build();
            //3. 创建用于提交请求的Call对象
            Call call = ok.newCall(request);
            //4. 提交异步请求，并获取响应数据
            try {
                Response response = call.execute();
                    Message message = Message.obtain();
                    message.what = 3;
                    handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //取号 --- 大桌
    class getLarge extends Thread{
        @Override
        public void run() {
            UserApplication userApplication = (UserApplication) getApplicationContext();
            User user = userApplication.getUser();
            FormBody.Builder formBuilder = new FormBody.Builder();
            //添加键值对数据
            String shopid = String.valueOf(shopId);
            String userid = String.valueOf(user.getUserId());
            String userphone = user.getPhone();
            formBuilder.add("sId", shopid);
            formBuilder.add("userId", userid);
            formBuilder.add("phone", userphone);
            FormBody formBody = formBuilder.build();
            //创建Request请求对象
            Request request = new Request.Builder()
                    .url("http://"+ip+":8080/demo001/numberr/getLarge.action")
                    .post(formBody)
                    .build();
            //3. 创建用于提交请求的Call对象
            Call call = ok.newCall(request);
            //4. 提交异步请求，并获取响应数据
            try {
                Response response = call.execute();
                Message message = Message.obtain();
                message.what = 4;
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //取消排号  (当天范围内，一个用户在一个店铺存在的"未叫"状态排号 只会存在唯一一个)
    class cancelNum extends Thread{
        @Override
        public void run() {
            UserApplication userApplication = (UserApplication) getApplicationContext();
            User user = userApplication.getUser();
            FormBody.Builder formBuilder = new FormBody.Builder();
            //添加键值对数据
            String shopid = String.valueOf(shopId);
            String userid = String.valueOf(user.getUserId());
            formBuilder.add("sId", shopid);
            formBuilder.add("userId",userid);
            FormBody formBody = formBuilder.build();
            //创建Request请求对象
            Request request = new Request.Builder()
                    .url("http://"+ip+":8080/demo001/numberr/cancelNum.action")
                    .post(formBody)
                    .build();
            //3. 创建用于提交请求的Call对象
            Call call = ok.newCall(request);
            //4. 提交异步请求，并获取响应数据
            try {
                Response response = call.execute();
                Message message = Message.obtain();
                message.what = 5;
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
