package fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ll.mealandenjoy.R;
import com.example.ll.mealandenjoy.activities.LoginActivity;
import com.example.ll.mealandenjoy.activities.PersonalInfoActivity;
import com.example.ll.mealandenjoy.activities.RegisterActivity;
import com.example.ll.mealandenjoy.activities.ShopLoginActivity;
import com.example.ll.mealandenjoy.activities.UserNumberActivity;
import com.example.ll.mealandenjoy.activities.UserShopCollectionActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import entity.NumDemo;
import entity.Shop;
import entity.ShopDemo;
import entity.User;
import entity.UserApplication;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;


/**
 * Created by lenovo on 2018/5/23.
 */

public class Mine_Fragment extends Fragment {
    private ImageButton imgbtnHeadSculpture;
    private Button btnRegister;
    private Button btnLogin;
    private TextView tvInfo;
    private TextView tvUserInfo;
    private Button btnCall;
    private Button btnExit;
    private User user;
    private Button btn_shop;
    private ImageButton imgbtnCollection;
    private ImageButton imgbtnNumber;
    //定义OKHTTPClient对象
    private OkHttpClient okHttpClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //加载选项卡对应的选项页面
        View view = inflater.inflate(R.layout.activity_mine, container, false);
        final UserApplication userApplication= (UserApplication)getActivity().getApplicationContext();
        user=userApplication.getUser();
        //获取布局文件中控件对象
        btnRegister = view.findViewById(R.id.bt_2);
        btnLogin = view.findViewById(R.id.bt_1);
        tvInfo = view.findViewById(R.id.tv_info);
        imgbtnHeadSculpture = view.findViewById(R.id.tox);
        tvUserInfo = view.findViewById(R.id.tv_user_info);
        tvUserInfo.setVisibility(View.GONE);
        btnCall=view.findViewById(R.id.bt_7);
        btn_shop = view.findViewById(R.id.bt_8);
        imgbtnCollection = view.findViewById(R.id.bt_3);
        btnExit=view.findViewById(R.id.bt_9);
        imgbtnNumber=view.findViewById(R.id.bt_4);
        //初始化OKHTTPClient对象
        okHttpClient = new OkHttpClient();
        //自定义一个Handler类
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //处理消息，做相应的操作
                switch (msg.what) {
                    //获取消息内容  msg.arg1   msg.arg2   msg.getData()
                    case 1:
                        //将shopSet传到我的收藏页面
                        Bundle b = msg.getData();
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), UserShopCollectionActivity.class);
                        intent.putExtras(b);
                        startActivity(intent);
                        break;
                    case 2:
                        //将userNumList传到号码里面
                        Bundle b1 = msg.getData();
                        Intent intent1 = new Intent();
                        intent1.setClass(getActivity(), UserNumberActivity.class);
                        intent1.putExtras(b1);
                        startActivity(intent1);
                        break;
                }
                super.handleMessage(msg);
            }
        };
        //为注册按钮添加点击事件监听器
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), RegisterActivity.class);
                //进行跳转
                startActivity(intent);
            }
        });
        //为登录按钮添加点击事件监听器
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                //进行跳转
                startActivity(intent);
            }
        });
        //为商店按钮添加事件监听器
        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ShopLoginActivity.class);
                //进行跳转,跳转到商店登录界面
                startActivity(intent);
            }
        });
        if (user != null){
            //为注销按钮设置点击事件监听器
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userApplication.setUser(null);
                    Toast.makeText(getActivity(),"您已注销~",Toast.LENGTH_SHORT).show();
                    btnExit.setVisibility(View.GONE);
                    getActivity().recreate();
                }
            });
        }else{
            btnExit.setVisibility(View.GONE);
        }
        //为拨打电话按钮添加事件监听器
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    // 没有获得授权，申请授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE)) {
                        // 返回值：
                        //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                        //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                        //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                        // 弹窗需要解释为何需要该权限，再次请求授权
                        Toast.makeText(getActivity(), "请授权！", Toast.LENGTH_LONG).show();
                        // 帮跳转到该应用的设置界面，让用户手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", "1111111", null);
                        intent.setData(uri);
                        startActivity(intent);
                    } else {
                        // 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                } else {
                    // 已经获得授权，可以打电话
                    CallPhone();
                }
            }
        });

        if (user != null){
            btnRegister.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
            tvInfo.setVisibility(View.GONE);

            tvUserInfo.setText(user.getUsername());
            tvUserInfo.setVisibility(View.VISIBLE);
            //为头像按钮添加点击事件监听器，跳转到个人信息界面
            imgbtnHeadSculpture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), PersonalInfoActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            imgbtnHeadSculpture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "您还没有登录,请先登录~", Toast.LENGTH_SHORT).show();
                }
            });
        }
            /*btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"您已登录",Toast.LENGTH_SHORT).show();
                }
            });*/


        //为收藏按钮注册点击事件监听器
        if (user == null) {
            imgbtnCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "您还没有登录,请先登录~", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            //请求服务器得到shopList
            //2. 创建Request请求对象
            // 构造向服务器提交的键值对数据信息
            imgbtnCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread() {
                        @Override
                        public void run() {
                            FormBody.Builder formBuilder = new FormBody.Builder();
                            //添加键值对数据
                            formBuilder.add("userId", user.getUserId() + "");
                            FormBody formBody = formBuilder.build();
                            //创建Request请求对象
                            Request request = new Request.Builder()
                                    .url("http://"+ip+":8080/MealAndEnjoyServer/user/getcollection.action")
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
                                Gson gson = new Gson();
                                Type ShopCollectionList = new TypeToken<List<ShopDemo>>() {
                                }.getType();
                                List<ShopDemo> shopCollectionList = gson.fromJson(str, ShopCollectionList);
                                Bundle b = new Bundle();
                                b.putSerializable("shopCollectionList", (Serializable) shopCollectionList);
                                message.setData(b);
                                message.what = 1;

                                //利用Handler对象发送消息
                                handler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }.start();

                }
            });


        }
        //为查看号码按钮注册点击事件监听器
        if (user == null){
            imgbtnNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "您还没有登录,请先登录~", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            imgbtnNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread() {
                        @Override
                        public void run() {
                            FormBody.Builder formBuilder = new FormBody.Builder();
                            //添加键值对数据
                            formBuilder.add("userId", user.getUserId() + "");
                            FormBody formBody = formBuilder.build();
                            //创建Request请求对象
                            Request request = new Request.Builder()
                                    .url("http://"+ip+":8080/MealAndEnjoyServer/user/getusernum.action")
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
                                Gson gson = new Gson();
                                Type UserNumList = new TypeToken<List<NumDemo>>() {
                                }.getType();
                                List<NumDemo> userNumList = gson.fromJson(str, UserNumList);
                                Bundle b = new Bundle();
                                b.putSerializable("userNumList", (Serializable) userNumList);
                                message.setData(b);
                                message.what = 2;

                                //利用Handler对象发送消息
                                handler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }.start();
                }
            });
        }

        //返回选项卡对应的选项页面
        return view;

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

}
